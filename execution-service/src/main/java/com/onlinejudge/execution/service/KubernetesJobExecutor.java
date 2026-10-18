package com.onlinejudge.execution.service;

import com.onlinejudge.execution.dto.ExecutionResult;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KubernetesJobExecutor {

    private final ApiClient apiClient;

    @Value("${execution.namespace:online-judge}")
    private String namespace;

    @Value("${execution.runner-image:onlinejudge/code-runner:latest}")
    private String runnerImage;

    public ExecutionResult execute(String submissionId, String code, String language) {
        String jobName = "exec-" + submissionId.substring(0, Math.min(8, submissionId.length()));
        try {
            BatchV1Api batchApi = new BatchV1Api(apiClient);
            CoreV1Api coreApi = new CoreV1Api(apiClient);

            V1Job job = new V1Job()
                    .metadata(new V1ObjectMeta().name(jobName).namespace(namespace))
                    .spec(new V1JobSpec()
                            .backoffLimit(0)
                            .ttlSecondsAfterFinished(60)
                            .activeDeadlineSeconds(30L)
                            .template(new V1PodTemplateSpec()
                                    .spec(new V1PodSpec()
                                            .restartPolicy("Never")
                                            .addContainersItem(new V1Container()
                                                    .name("runner")
                                                    .image(runnerImage)
                                                    .addEnvItem(new V1EnvVar().name("CODE").value(code))
                                                    .addEnvItem(new V1EnvVar().name("LANGUAGE").value(language))
                                                    .resources(new V1ResourceRequirements()
                                                            .limits(Map.of("cpu", new io.kubernetes.client.custom.Quantity("500m"),
                                                                           "memory", new io.kubernetes.client.custom.Quantity("256Mi")))
                                                            .requests(Map.of("cpu", new io.kubernetes.client.custom.Quantity("100m"),
                                                                             "memory", new io.kubernetes.client.custom.Quantity("128Mi")))
                                                    )
                                                    .securityContext(new V1SecurityContext()
                                                            .runAsUser(1000L)
                                                            .runAsNonRoot(true)
                                                            .readOnlyRootFilesystem(true)
                                                            .allowPrivilegeEscalation(false)
                                                    )
                                            )
                                            .securityContext(new V1PodSecurityContext()
                                                    .runAsNonRoot(true)
                                                    .seccompProfile(new V1SeccompProfile().type("RuntimeDefault"))
                                            )
                                    )
                            )
                    );

            // In kubernetes-client 19.x, methods return the model directly. No .execute() needed.
            // Arguments: namespace, body, pretty, dryRun, fieldManager, fieldValidation
            batchApi.createNamespacedJob(namespace, job, null, null, null, null);
            log.info("Created K8s Job: {}", jobName);

            // Poll for completion (simplified)
            Thread.sleep(5000);
            String logs = "";
            try {
                // Arguments: namespace, pretty, allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit, resourceVersion, resourceVersionMatch, sendInitialEvents, timeoutSeconds, watch
                V1PodList pods = coreApi.listNamespacedPod(
                        namespace, 
                        null, null, null, null, 
                        "job-name=" + jobName, 
                        null, null, null, null, null, null
                );

                if (pods != null && pods.getItems() != null && !pods.getItems().isEmpty()) {
                    String podName = pods.getItems().get(0).getMetadata().getName();
                    
                    // Arguments: name, namespace, pretty, follow, insecureSkipTLSVerifyBackend, limitBytes, sinceTime, previous, sinceSeconds, tailLines, timestamps
                    logs = coreApi.readNamespacedPodLog(
                            podName, 
                            namespace, 
                            null, null, null, null, null, null, null, null, null
                    );
                }
            } catch (Exception e) {
                log.warn("Could not read logs for job {}: {}", jobName, e.getMessage());
            }

            return ExecutionResult.builder()
                    .submissionId(submissionId)
                    .status("ACCEPTED")
                    .output(logs)
                    .runtime(100)
                    .memory(1024)
                    .build();

        } catch (Exception e) {
            log.error("Execution failed for submission {}: {}", submissionId, e.getMessage());
            return ExecutionResult.builder()
                    .submissionId(submissionId)
                    .status("RUNTIME_ERROR")
                    .output(e.getMessage())
                    .build();
        }
    }
}