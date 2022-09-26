package com.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.exception.IdNotFoundException;
import com.demo.model.Student;
import com.demo.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	StudentService studentService;

	// test
	@RequestMapping("/test")
	public String hello() {
		return "Hello world";
	}

	// get by student id
	@GetMapping("/student/{id}")
	private Student getStudent(@PathVariable("id") int id) {
		if (id <= 0) {
			throw new IdNotFoundException("Invalid ItemId");
		}
		return studentService.getStudentById(id);
	}

	// get all student
	@GetMapping("/student")
	private List<Student> getAllStudent() {
		return studentService.getAllStudent();
	}

	// post the student info
	@PostMapping("/student")
	private int saveStudent(@Validated @RequestBody Student student) {
		studentService.saveOrUpdate(student);
		return student.getId();
	}

	// delete student by studentId
	@DeleteMapping("/student/{id}")
	private void deleteStudent(@PathVariable("id") int id) {
		studentService.delete(id);
	}

	// compare two dates
	@RequestMapping("/dates/{dateTimeOne}/{dateTimeTwo}")
	private String deleteStudent(@PathVariable("dateTimeOne") String dateTimeOne,
			@PathVariable("dateTimeTwo") String dateTimeTwo) {
		LocalDateTime dateTimeOne1 = LocalDateTime.parse(dateTimeOne);

		LocalDateTime dateTimeTwo2 = LocalDateTime.parse(dateTimeTwo);

		int compareValue = dateTimeOne1.compareTo(dateTimeTwo2);

		System.out.println("Compare value = " + compareValue);

		if (compareValue > 0) {
			return "dateTimeOne is later than dateTimeTwo";
		} else if (compareValue < 0) {
			return "dateTimeOne is earlier than dateTimeTwo";
		} else {
			return "both dates are equal";
		}
	}

	// filter and map
	@RequestMapping("/testfiltermap")
	private Map<Integer, String> filterMapDemo() {
		Map<Integer, String> hmap = new HashMap<Integer, String>();
		hmap.put(1, "ABC");
		hmap.put(2, "XCB");
		hmap.put(3, "ABB");
		hmap.put(4, "ZIO");

		Map<Integer, String> result = hmap.entrySet().stream().filter(p -> p.getKey().intValue() <= 2) // filter by key
				.filter(map -> map.getValue().startsWith("A")) // filter by value
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		return result;
	}

	// default method
	@RequestMapping("/testdefaultmethod")
	private String defaultMethod() {
		return studentService.defaultMethod();
	}
}
