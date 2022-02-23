package com.example.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 생성자와 비슷하게 Builder 패턴으로 객체 생성 가능
@Builder
// 매개변수 없는 생성자
@NoArgsConstructor
// 모든 멤버 변수를 매개변수로 받는 생성자
@AllArgsConstructor
// getter/setter 구현
@Data
@Entity
@Table(name="Todo")
public class TodoEntity {
	@Id	// 기본키 명시
	@GeneratedValue(generator="system-uuid")	// 키 자동 생성
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String id;
	private String userId;
	private String title;
	private boolean done;
}
