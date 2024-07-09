package com.example.TodoDemo.controller;

import com.example.TodoDemo.model.Step;
import com.example.TodoDemo.model.Task;
import com.example.TodoDemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model){
        model.addAttribute("tasks", taskService.getAllTasks());
        return "MainPage";
    }

    @GetMapping("/{id}")
    public String getTaskById(Model model, @PathVariable Long id){
        model.addAttribute(taskService.getTaskById(id));
        return "redirect:/";
    }

    @GetMapping("/{taskId}/steps")
    public ResponseEntity<List<Step>> getSteps(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getSteps(taskId));
    }

    @PostMapping("/tasks")
    public String createTask(@RequestParam String title, @RequestParam String description){
        taskService.createOrUpdateTask(new Task(title, description));
        return "redirect:/";
    }

    @PostMapping("/{taskId}/steps")
    public ResponseEntity<Step> addStep(@PathVariable Long taskId, @RequestBody Step step){
        return new ResponseEntity<>(taskService.createOrUpdateStep(taskId, step), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Optional<Task> existing = taskService.getTaskById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        task.setId(id);
        return ResponseEntity.ok(taskService.createOrUpdateTask(task));
    }

    @DeleteMapping("/{taskId}/steps/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long taskId, @PathVariable Long stepId){
        taskService.deleteStep(taskId, stepId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
