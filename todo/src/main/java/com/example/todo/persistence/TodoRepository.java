package com.example.todo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.todo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {	// 매핑될 엔티티 클래스, 기본키 타입

	@Query("select * from Todo t where t.userId = ?1")
	List<TodoEntity> findByUserId(String userId);
	
}
