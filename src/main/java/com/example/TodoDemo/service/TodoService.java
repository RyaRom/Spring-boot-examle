package com.example.TodoDemo.service;


import com.example.TodoDemo.model.Task;
import com.example.TodoDemo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Task> getAllTasks() {
        return todoRepository.findAll();
    }
    public Optional<Task> getTaskById(Long id) {
        return todoRepository.findById(id);
    }
    public Task createOrUpdateTask(Task task) {
        return todoRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        todoRepository.deleteById(id);
    }
}
