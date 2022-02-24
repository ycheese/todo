package com.example.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;

	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try{
			String temporaryUserId = "temporary-user";	// temporary user id.
			
			// 1. TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// 2. id를 null로 초기화
			entity.setId(null);
			
			// 3. 임시 사용자 ID 설정
			entity.setUserId(temporaryUserId);
			
			// 4. Todo 엔티티 생성
			List<TodoEntity> entities = service.create(entity);
			
			// 5. 자바 스트림을 이용해 리턴된 리스트를 TodoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// 7. ResponseDTO 리턴
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			// 8. 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴 
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
}
