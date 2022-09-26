package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Student;
import com.demo.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	public Student getStudentById(int id) {
		return studentRepository.findById(id).get();
	}

	// getting all student records
	public List<Student> getAllStudent() {
		List<Student> students = new ArrayList<Student>();
		studentRepository.findAll().forEach(student -> students.add(student));
		return students;
	}

	public void saveOrUpdate(Student student) {
		studentRepository.save(student);
	}

	public void delete(int id) {
		studentRepository.deleteById(id);
	}

	public String defaultMethod() {
		return studentRepository.newMethod();
	}

}
