package com.example.clientservice;

import com.example.CreateOrUpdateStudentRequest;
import com.example.StudentReply;
import com.example.StudentRequest;
import com.example.StudentServiceGrpc;
import com.example.entity.Student;
import com.example.interceptors.ApiKeyAuthInterceptorClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.inject.Singleton;

@Singleton
public class ClientService {


        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8081)
                .usePlaintext() // disable TLS which is enabled by default and requires certificates
                .intercept(new ApiKeyAuthInterceptorClient())
                .build();
        StudentServiceGrpc.StudentServiceBlockingStub client = StudentServiceGrpc.newBlockingStub(channel);

    public StudentReply getStudent(int id){

        StudentRequest studentRequest = StudentRequest.newBuilder().setId(id).build();
        return client.getStudentDetails(studentRequest);

    }
    public StudentReply addStudent(Student student){
        CreateOrUpdateStudentRequest createOrUpdateStudentRequest = CreateOrUpdateStudentRequest.newBuilder()
                .setGrade(student.getGrade())
                .setLevel(student.getLevel())
                .setName(student.getName())
                .setMarks(student.getMarks())
                .build();
        return client.addStudentDetails(createOrUpdateStudentRequest);
    }
    public StudentReply updateStudent(Student student){

        CreateOrUpdateStudentRequest createOrUpdateStudentRequest = CreateOrUpdateStudentRequest.newBuilder()
                .setGrade(student.getGrade())
                .setLevel(student.getLevel())
                .setName(student.getName())
                .setMarks(student.getMarks())
                .setId((student.getId().intValue()))
                .build();
        return client.editStudentDetails(createOrUpdateStudentRequest);
    }

    public StudentReply deleteStudent(int id){
        StudentRequest studentRequest = StudentRequest.newBuilder().setId(id).build();
        return client.deleteStudentDetails(studentRequest);

    }
}
