package com.example.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.ResponseDTO;

@RestController
@RequestMapping("todo")
public class TodoController {

	@GetMapping
	public ResponseEntity<?> testTodo(){
		List<String> list = new ArrayList<>();
		list.add("준비중입니다...");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.badRequest().body(response);
	}
}
