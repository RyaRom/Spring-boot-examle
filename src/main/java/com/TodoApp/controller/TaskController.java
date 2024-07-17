package com.TodoApp.controller;

import com.TodoApp.model.Step;
import com.TodoApp.model.Task;
import com.TodoApp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        return taskService.getTaskById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/tasks/{taskId}/steps")
    public ResponseEntity<List<Step>> getAllSteps(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getSteps(taskId));
    }
    @GetMapping("/tasks/steps/{stepId}")
    public ResponseEntity<Step> getStep(@PathVariable Long stepId){
        return taskService.getStepById(stepId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @GetMapping("/tasks/completed")
    public ResponseEntity<List<Task>> getCompletedTasks(){
        return ResponseEntity.ok(taskService.getCompletedTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.createOrUpdateTask(task), HttpStatus.CREATED);
    }

    @PostMapping("/tasks/{taskId}/steps")
    public ResponseEntity<Step> addStep(@PathVariable Long taskId, @RequestBody Step step){
        return new ResponseEntity<>(taskService.addStep(taskId, step), HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Optional<Task> old = taskService.getTaskById(id);
        if (old.isEmpty()) return ResponseEntity.notFound().build();
        task.setId(id);
        return ResponseEntity.ok(taskService.createOrUpdateTask(task));
    }
    @PutMapping("/tasks/{taskId}/steps/{stepId}")
    public ResponseEntity<Step> updateStep(@PathVariable Long taskId, @PathVariable Long stepId, @RequestBody Step step){
        Optional<Step> old = taskService.getStepById(stepId);
        if (old.isEmpty()) return ResponseEntity.notFound().build();
        step.setId(stepId);
        return ResponseEntity.ok(taskService.addStep(taskId, step));
    }

    @DeleteMapping("/tasks/steps/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long stepId){
        taskService.deleteStep( stepId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
