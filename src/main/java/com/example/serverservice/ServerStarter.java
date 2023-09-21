package com.example.serverservice;

import com.example.interceptors.ApiKeyAuthInterceptor;
import io.grpc.ServerBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.IOException;
@Singleton
public class ServerStarter {
    @Inject
    private StudentServiceImpl studentService;
    public void startService() throws IOException {

        System.out.println("service started on port 8081");
        ServerBuilder.forPort(8081).addService(studentService)
                .intercept(new ApiKeyAuthInterceptor())
                .build().start();
    }
}
