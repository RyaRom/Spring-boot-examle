package com.example.TodoDemo.controller;

import com.example.TodoDemo.model.Todo;
import com.example.TodoDemo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        return todoService.getTodoById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.createOrUpdateTodo(todo), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo){
        Optional<Todo> existing = todoService.getTodoById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        todo.setId(id);
        return ResponseEntity.ok(todoService.createOrUpdateTodo(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id){
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }
}
