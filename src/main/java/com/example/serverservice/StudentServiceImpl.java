package com.example.serverservice;

import com.example.CreateOrUpdateStudentRequest;
import com.example.StudentReply;
import com.example.StudentRequest;
import com.example.StudentServiceGrpc;
import com.example.entity.Student;
import com.example.repository.StudentRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.logging.Logger;
@Singleton
//@GrpcService
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{
    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    @Inject
    private StudentRepository studentRepository;

    @Override
    public void addStudentDetails(CreateOrUpdateStudentRequest request, StreamObserver<StudentReply> responseObserver) {
        try {
            StudentReply studentReply = StudentReply.newBuilder()
                    .setName(request.getName())
                    .setGrade(request.getGrade())
                    .setLevel(request.getLevel())
                    .setMarks((request.getMarks()))
                    .build();

            Student student = new Student();
            student.setName(studentReply.getName());
            student.setGrade(studentReply.getGrade());
            student.setMarks(studentReply.getMarks());
            student.setLevel(studentReply.getLevel());

            // Attempt to save the student entity
            studentRepository.save(student);

            logger.info("Student created");

            responseObserver.onNext(studentReply);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Handle any exceptions related to saving the student entity
            logger.severe("An error occurred while adding a student: " + e.getMessage());
            // Send an error response
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while adding a student.")
                    .asRuntimeException());
        }
    }

    @Override
    public void getStudentDetails(StudentRequest request, StreamObserver<StudentReply> responseObserver) {
        long id = request.getId();
        logger.info("Fetching student details for ID: " + id);

        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student student1 = studentOptional.get();
                StudentReply studentReply = StudentReply.newBuilder()
                        .setName(student1.getName())
                        .setGrade(student1.getGrade())
                        .setLevel(student1.getLevel())
                        .setMarks(student1.getMarks())
                        .build();

                responseObserver.onNext(studentReply);
                responseObserver.onCompleted();
            } else {
                // Handle the case where the student with the given ID is not found
                // You can throw an exception or return an error response as needed.
                logger.warning("Student with ID " + id + " not found.");
                // Send an error response
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("Student with ID " + id + " not found.")
                        .asRuntimeException());
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            logger.severe("An error occurred while fetching student details: " + e.getMessage());
            // Send an error response
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while fetching student details.")
                    .asRuntimeException());
        }
    }


    @Override
    public void editStudentDetails(CreateOrUpdateStudentRequest request, StreamObserver<StudentReply> responseObserver) {
        long id = request.getId();

        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student student1 = studentOptional.get();

                // Update the student entity
                student1.setLevel(request.getLevel());
                student1.setMarks(request.getMarks());
                student1.setGrade(request.getGrade());
                student1.setName(request.getName());

                // Attempt to update the student entity
                studentRepository.update(student1);

                StudentReply studentReply = StudentReply.newBuilder()
                        .setName(request.getName())
                        .setGrade(request.getGrade())
                        .setLevel(request.getLevel())
                        .setMarks(request.getMarks())
                        .build();

                responseObserver.onNext(studentReply);
                responseObserver.onCompleted();
            } else {
                // Handle the case where the student with the given ID is not found
                logger.warning("Student with ID " + id + " not found.");
                // Send an error response
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("Student with ID " + id + " not found.")
                        .asRuntimeException());
            }
        } catch (Exception e) {
            // Handle any exceptions related to updating the student entity
            logger.severe("An error occurred while updating a student: " + e.getMessage());
            // Send an error response
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while updating a student.")
                    .asRuntimeException());
        }
    }

    @Override
    public void deleteStudentDetails(StudentRequest request, StreamObserver<StudentReply> responseObserver) {
        long id = request.getId();

        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student student1 = studentOptional.get();
                studentRepository.delete(student1);
                StudentReply studentReply = StudentReply.newBuilder()
                        .setName(student1.getName())
                        .setGrade(student1.getGrade())
                        .setLevel(student1.getLevel())
                        .setMarks(student1.getMarks())
                        .build();

                responseObserver.onNext(studentReply);
                responseObserver.onCompleted();
            } else {
                // Handle the case where the student with the given ID is not found
                logger.warning("Student with ID " + id + " not found.");
                // Send an error response
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("Student with ID " + id + " not found.")
                        .asRuntimeException());
            }
        } catch (Exception e) {
            // Handle any exceptions related to deleting the student entity
            logger.severe("An error occurred while deleting a student: " + e.getMessage());
            // Send an error response
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while deleting a student.")
                    .asRuntimeException());
        }
    }

}
