package david.daczo.assignment.controller;

import david.daczo.assignment.model.Job;
import david.daczo.assignment.model.Task;
import david.daczo.assignment.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    TaskService taskService;

    @PostMapping("/job")
    public Job postJob(@RequestBody Job job) {
        Task[] topoOrderedTasks = taskService.topologicalSort(job.getTasks());
        Task[] reversedTasks = taskService.reverseTaskArray(topoOrderedTasks);
        return new Job(List.of(reversedTasks));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "Error: " + ex.getMessage();
    }
}
