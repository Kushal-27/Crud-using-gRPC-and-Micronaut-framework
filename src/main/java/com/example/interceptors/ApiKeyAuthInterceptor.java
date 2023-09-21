package com.example.interceptors;

import com.example.serverservice.StudentServiceImpl;
import io.grpc.*;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.logging.Logger;

@Slf4j
@Singleton

public class ApiKeyAuthInterceptor implements ServerInterceptor {
    private static final Logger logger = Logger.getLogger(ApiKeyAuthInterceptor.class.getName());

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {

        Metadata.Key<String> apiKeyMetadata = Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);
        String apiKey = metadata.get(apiKeyMetadata);
        logger.info(apiKey);

        if (Objects.nonNull(apiKey) && apiKey.equals("kushal")) {

            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            Status status = Status.UNAUTHENTICATED.withDescription("Invalid api-key");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<>() {
        };
    }

}
