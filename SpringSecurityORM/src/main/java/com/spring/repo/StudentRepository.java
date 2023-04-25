package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
