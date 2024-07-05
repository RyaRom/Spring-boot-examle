package com.example.TodoDemo.controller;

import com.example.TodoDemo.model.Task;
import com.example.TodoDemo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTodoById(@PathVariable Long id){
        return todoService.getTaskById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<Task> updateSubTasks(@PathVariable Long id, @RequestBody List<Task> tasks){
//        Optional<Task> existingTask = todoService.getTaskById(id);
//        if (existingTask.isEmpty()) return ResponseEntity.notFound().build();
//
//    }

    @PostMapping
    public ResponseEntity<Task> createTodo(@RequestBody Task task){
        return new ResponseEntity<>(todoService.createOrUpdateTask(task), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Task> updateTodo(@PathVariable Long id, @RequestBody Task task){
        Optional<Task> existing = todoService.getTaskById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        task.setId(id);
        return ResponseEntity.ok(todoService.createOrUpdateTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id){
        todoService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
