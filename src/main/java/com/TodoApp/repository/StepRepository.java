package com.TodoApp.repository;

import com.TodoApp.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByTaskId(Long taskId);
}
