package com.example.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
		try{
			// 1. TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// 2. id를 null로 초기화
			entity.setId(null);
			
			// 3. 사용자 ID 설정
			entity.setUserId(userId);
			
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
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
		// 1. 서비스의 retrieve() 메소드를 사용해 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(userId);
		
		// 2. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 3. 변환된 TodoDTO 리스트를 이요해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// 4. ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
		// 1. dto를 entity로 변환한다.
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		// 2. id를 userId로 초기화한다.
		entity.setUserId(userId);
		
		// 3. 서비스를 이용해 entity를 업데이트한다.
		List<TodoEntity> entities = service.update(entity);
		
		// 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// 6. ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
		try {
			// 1. dto를 entity로 변환한다.
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// 2. id를 userId로 초기화한다.
			entity.setUserId(userId);
			
			// 3. 서비스를 이용해 entity를 삭제한다.
			List<TodoEntity> entities = service.delete(entity);
			
			// 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// 6. ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			// 7. 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
