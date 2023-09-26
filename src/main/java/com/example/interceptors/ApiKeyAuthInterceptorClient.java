//package com.example.interceptors;
//
//import io.grpc.*;
//import jakarta.inject.Singleton;
//import lombok.extern.slf4j.Slf4j;
//
//@Singleton
//@Slf4j
//public class ApiKeyAuthInterceptorClient implements ClientInterceptor {
//    @Override
//    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
//        return new
//                ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(methodDescriptor, callOptions)) {
//                    @Override
//                    public void start(Listener<RespT> responseListener, Metadata metadata) {
//                        metadata.put(Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER),"kushal");
//                        super.start(responseListener, metadata);
//                    }
//                };
//    }
//}
