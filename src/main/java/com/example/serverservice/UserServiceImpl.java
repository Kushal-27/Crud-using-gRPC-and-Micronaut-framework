package com.example.serverservice;

import com.example.*;
import com.example.entity.Student;
import com.example.entity.Users;
import com.example.repository.UsersRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    @Inject
    private UsersRepository usersRepository;
    @Override
    public void addUser(CreateOrUpdateUserRequest request, StreamObserver<UserReply> responseObserver) {
        try {
            UserReply userReply = UserReply.newBuilder()
                    .setName(request.getName())
                    .setPassword(request.getPassword())
                    .setRole(request.getRole())
                    .build();

            Users user = new Users();
            user.setPassword(request.getPassword());
            user.setRole(request.getRole());
            user.setName(request.getName());


            // Attempt to save the student entity
            usersRepository.save(user);



            responseObserver.onNext(userReply);
            responseObserver.onCompleted();
        } catch (Exception e) {

            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while adding a user.")
                    .asRuntimeException());
        }
    }


    @Override
    public void getUser(UserRequest request, StreamObserver<UserReply> responseObserver) {
        long id = request.getId();

        try {
            Optional<Users> userOptional = usersRepository.findById(id);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                UserReply userReply = UserReply.newBuilder()
                        .setRole(user.getRole())
                        .setPassword(user.getPassword())
                        .setName(user.getName())

                        .build();

                responseObserver.onNext(userReply);
                responseObserver.onCompleted();
            } else {

                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("User with ID " + id + " not found.")
                        .asRuntimeException());
            }
        } catch (Exception e) {

            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while fetching user details.")
                    .asRuntimeException());
        }
    }

    @Override
    public void editUser(CreateOrUpdateUserRequest request, StreamObserver<UserReply> responseObserver) {
        long id = request.getId();

        try {
            Optional<Users> userOptional = usersRepository.findById(id);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                // Update the student entity
                user.setName(request.getName());
                user.setPassword(request.getPassword());
                user.setName(request.getPassword());


                // Attempt to update the student entity
                usersRepository.update(user);

                UserReply userReply = UserReply.newBuilder()
                        .setName(request.getName())
                        .setPassword(request.getPassword())
                        .setRole(request.getRole())

                        .build();

                responseObserver.onNext(userReply);
                responseObserver.onCompleted();
            } else {

                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("User with ID " + id + " not found.")
                        .asRuntimeException());
            }
        } catch (Exception e) {

            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while updating a user.")
                    .asRuntimeException());
        }
    }

    @Override
    public void deleteUser(UserRequest request, StreamObserver<UserReply> responseObserver) {
        long id = request.getId();

        try {
            Optional<Users> userOptional = usersRepository.findById(id);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                usersRepository.delete(user);
                UserReply userReply = UserReply.newBuilder()
                        .setName(user.getName())
                        .setPassword(user.getPassword())
                        .setRole(user.getRole())

                        .build();

                responseObserver.onNext(userReply);
                responseObserver.onCompleted();
            } else {

                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("User with ID " + id + " not found.")
                        .asRuntimeException());
            }
        } catch (Exception e) {

            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while deleting a user.")
                    .asRuntimeException());
        }
    }
}
