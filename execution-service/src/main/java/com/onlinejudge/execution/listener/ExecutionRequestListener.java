package com.onlinejudge.execution.listener;

import com.onlinejudge.execution.dto.ExecutionRequest;
import com.onlinejudge.execution.dto.ExecutionResult;
import com.onlinejudge.execution.service.KubernetesJobExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import static com.onlinejudge.common.constants.QueueConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecutionRequestListener implements MessageListener {

    private final KubernetesJobExecutor executor;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Jackson2JsonRedisSerializer<ExecutionRequest> requestSerializer =
            new Jackson2JsonRedisSerializer<>(ExecutionRequest.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ExecutionRequest request = requestSerializer.deserialize(message.getBody());
        if (request != null) {
            log.info("Processing execution request for submission {}", request.getSubmissionId());
            ExecutionResult result = executor.execute(
                    request.getSubmissionId(), request.getCode(), request.getLanguage()
            );
            redisTemplate.convertAndSend(EXECUTION_RESULT_QUEUE, result);
            log.info("Published result for submission {}", request.getSubmissionId());
        }
    }
}