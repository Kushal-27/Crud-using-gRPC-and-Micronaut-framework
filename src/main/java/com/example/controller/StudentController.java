package com.example.controller;

import com.example.StudentReply;
import com.example.clientservice.ClientService;
import com.example.entity.Student;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller

public class StudentController {
    @Inject
    private ClientService clientService;

    @Get("/{id}")
    public Student getStudent(@PathVariable int id) {
        Student student = new Student();
        StudentReply studentReply = clientService.getStudent(id);
        student.setLevel(studentReply.getLevel());
        student.setMarks(studentReply.getMarks());
        student.setName(studentReply.getName());
        student.setGrade(studentReply.getGrade());
        return student;
    }
    @Post("/create")
    public Student createStudent(@Body Student student){
        StudentReply studentReply = clientService.addStudent(student);
        Student student1 = new Student();

        student1.setLevel(studentReply.getLevel());
        student1.setMarks(studentReply.getMarks());
        student1.setName(studentReply.getName());
        student1.setGrade(studentReply.getGrade());
        return student1;
    }
    @Post("/update")
    public Student updateStudent(@Body Student student){
        StudentReply studentReply = clientService.updateStudent(student);
        Student student1 = new Student();

        student1.setLevel(studentReply.getLevel());
        student1.setMarks(studentReply.getMarks());
        student1.setName(studentReply.getName());
        student1.setGrade(studentReply.getGrade());
        return student1;
    }
    @Delete("delete/{id}")
    public Student deleteStudent(@PathVariable int id){
        StudentReply studentReply = clientService.deleteStudent(id);
        Student student1 = new Student();

        student1.setLevel(studentReply.getLevel());
        student1.setMarks(studentReply.getMarks());
        student1.setName(studentReply.getName());
        student1.setGrade(studentReply.getGrade());
        return student1;

    }
}
