package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	
	
	private List<Student> studentlist;
	
	
	@PostConstruct
	public void loadData()
	{
		studentlist=new ArrayList<Student>();
		studentlist.add(new Student("Kamala","kala"));
		studentlist.add(new Student("sachine","Tendulkar"));
		studentlist.add(new Student("Mario","Rossi"));
		studentlist.add(new Student("Mary","Smith"));
	}
	
	@GetMapping("/student")
	public List<Student> getStudentList()
	{
		
		return studentlist;
		
	}
	
	@GetMapping("/student/{studentId}")
	public Student getStudent(@PathVariable int studentId)
	{
		if((studentId>=studentlist.size())||(studentId<0))
		{
			throw new StudentNotFoundException("student id not Fount -"+studentId);
		}
			
		
		return studentlist.get(studentId);
		
	}
	
	@ExceptionHandler 
	public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc)
	{
		
		StudentErrorResponse error=new StudentErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);

		
	}
	@ExceptionHandler 
	public ResponseEntity<StudentErrorResponse> handleException(Exception exc)
	{
		
		StudentErrorResponse error=new StudentErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

		
	}
}