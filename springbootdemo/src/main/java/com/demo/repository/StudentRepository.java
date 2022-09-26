package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

	default String newMethod(){  
        return "default method Called successfully";  
    } 

}
