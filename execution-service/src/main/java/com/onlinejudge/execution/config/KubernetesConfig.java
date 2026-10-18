package com.onlinejudge.execution.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class KubernetesConfig {

    @Bean
    public ApiClient apiClient() throws IOException {
        ApiClient client;
        try {
            // Try in-cluster config first (when running inside AKS)
            client = Config.fromCluster();
        } catch (Exception e) {
            // Fall back to kubeconfig (local development)
            client = Config.defaultClient();
        }
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);
        return client;
    }
}