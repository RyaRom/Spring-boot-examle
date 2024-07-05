package com.example.TodoDemo.repository;

import com.example.TodoDemo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findTasksByCompleted(boolean isCompleted);
}
