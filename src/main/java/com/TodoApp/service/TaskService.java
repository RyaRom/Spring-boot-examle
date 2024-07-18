package com.TodoApp.service;


import com.TodoApp.model.Task;
import com.TodoApp.repository.TaskRepository;
import com.TodoApp.model.Step;
import com.TodoApp.repository.StepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final StepRepository stepRepository;

    public TaskService(TaskRepository taskRepository, StepRepository stepRepository) {
        this.taskRepository = taskRepository;
        this.stepRepository = stepRepository;
    }

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
        task.orElseThrow();
        step.setTask(task.get());
        return stepRepository.save(step);
    }
    public Optional<Step> getStepById(Long stepId){
        return stepRepository.findById(stepId);
    }
    public List<Step> getSteps(Long taskId){
        return stepRepository.findByTaskId(taskId);
    }
    public void deleteStep( Long stepId){
        stepRepository.deleteById(stepId);
    }
}
