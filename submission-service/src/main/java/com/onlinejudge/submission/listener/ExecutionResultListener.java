package com.onlinejudge.submission.listener;

import com.onlinejudge.submission.dto.ExecutionResult;
import com.onlinejudge.submission.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import static com.onlinejudge.common.constants.QueueConstants.EXECUTION_RESULT_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecutionResultListener implements MessageListener {

    private final SubmissionService submissionService;
    private final Jackson2JsonRedisSerializer<ExecutionResult> serializer =
            new Jackson2JsonRedisSerializer<>(ExecutionResult.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ExecutionResult result = serializer.deserialize(message.getBody());
        if (result != null) {
            log.info("Received execution result for submission {}: {}", result.getSubmissionId(), result.getStatus());
            submissionService.updateResult(
                    result.getSubmissionId(), result.getStatus(),
                    result.getOutput(), result.getRuntime(), result.getMemory()
            );
        }
    }
}