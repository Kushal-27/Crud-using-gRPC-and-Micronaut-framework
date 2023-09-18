package com.example.serverservice;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

import java.util.Optional;

@Controller
public class DataCheck {
    @Inject
    private StudentRepository studentRepository;
    @Get("/data/{id}")
    public Student getData(@PathVariable Long id){
        Optional<Student> student = studentRepository.findById(id);
        return student.get();


    }
}
