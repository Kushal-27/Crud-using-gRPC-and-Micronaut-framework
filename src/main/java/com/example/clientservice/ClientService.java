package com.example.clientservice;

import com.example.CreateOrUpdateStudentRequest;
import com.example.StudentReply;
import com.example.StudentRequest;
import com.example.StudentServiceGrpc;
import com.example.entity.Student;
//import com.example.interceptors.ApiKeyAuthInterceptorClient;
import com.example.security.Constants;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Singleton;

@Singleton
public class ClientService {


        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8081)
                .usePlaintext() // disable TLS which is enabled by default and requires certificates
               // .intercept(new ApiKeyAuthInterceptorClient())
                .build();
        BearerToken token = new BearerToken(getJwt());

        StudentServiceGrpc.StudentServiceBlockingStub client = StudentServiceGrpc.newBlockingStub(channel).withCallCredentials(token);



    private static String getJwt() {
        return Jwts.builder()
                .setSubject("random")
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SIGNING_KEY)
                .compact();
    }

    public StudentReply getStudent(int id){

        StudentRequest studentRequest = StudentRequest.newBuilder().setId(id).build();
        System.out.println(getJwt());
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
