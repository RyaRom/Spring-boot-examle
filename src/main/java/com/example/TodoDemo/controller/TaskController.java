package com.example.TodoDemo.controller;

import com.example.TodoDemo.model.Step;
import com.example.TodoDemo.model.Task;
import com.example.TodoDemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@Controller
@RequestMapping(method = RequestMethod.POST)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/")
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
    public String getSteps(@PathVariable Long taskId, Model model){
        model.addAttribute("steps", taskService.getSteps(taskId));
        return "redirect:/";
    }

    @PostMapping("/tasks")
    public String createTask(@RequestParam String title, @RequestParam String description){
        taskService.createOrUpdateTask(new Task(title, description));
        return "redirect:/";
    }

    @PostMapping("/tasks/{taskId}/steps")
    public String addStep(@PathVariable Long taskId, @RequestParam String step_title){
        taskService.createOrUpdateStep(taskId, new Step(step_title));
        return "redirect:/";
    }

    @PostMapping("/tasks/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId){
        taskService.completeTask(taskId);
        return "redirect:/";
    }

    @PostMapping("/tasks/steps/{stepId}/complete")
    public String completeStep(@PathVariable Long stepId){
        taskService.completeStep(stepId);
        return "redirect:/";
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Optional<Task> existing = taskService.getTaskById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        task.setId(id);
        return ResponseEntity.ok(taskService.createOrUpdateTask(task));
    }*/

    @DeleteMapping("/{taskId}/steps/{stepId}")
    public String deleteStep(@PathVariable Long taskId, @PathVariable Long stepId){
        taskService.deleteStep(taskId, stepId);
        return "redirect:/";
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return "redirect:/";
    }
}
