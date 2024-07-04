package com.example.TodoDemo.service;


import com.example.TodoDemo.model.Todo;
import com.example.TodoDemo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    public Todo createOrUpdateTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }
}
