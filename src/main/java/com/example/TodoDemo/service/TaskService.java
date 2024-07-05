package com.example.TodoDemo.service;


import com.example.TodoDemo.model.Step;
import com.example.TodoDemo.model.Task;
import com.example.TodoDemo.repository.StepRepository;
import com.example.TodoDemo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StepRepository stepRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> getCompletedTasks(){
        return taskRepository.findTasksByCompleted(true);
    }
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    public Task createOrUpdateTask(Task task) {
        return taskRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
    public Step addStep(Long taskId, Step step){
        Optional<Task> task = getTaskById(taskId);
        if (task.isEmpty())throw new RuntimeException("no task with this id");
        step.setTask(task.get());
        return stepRepository.save(step);
    }
    public List<Step> getSteps(Long taskId){
        return stepRepository.findByTaskId(taskId);
    }
    public void deleteStep(Long taskId, Long stepId){
        Optional<Task> task = getTaskById(taskId);
        Optional<Step> step = stepRepository.findById(stepId);
        if (task.isEmpty() || step.isEmpty()) return;
        stepRepository.deleteById(stepId);
    }
}
