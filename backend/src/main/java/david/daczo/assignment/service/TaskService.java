package david.daczo.assignment.service;

import david.daczo.assignment.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    public Task[] topologicalSort(List<Task> tasks) throws IllegalArgumentException {
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Task> taskMap = new HashMap<>();
        Set<String> recursionStack = new HashSet<>();

        for (Task task : tasks) {
            visited.put(task.getName(), false);
            taskMap.put(task.getName(), task);
        }

        Task[] ordering = new Task[tasks.size()];
        int i = tasks.size() - 1;

        for (Task task : tasks) {
            if (!visited.get(task.getName())) {
                i = dfs(i, task, visited, ordering, taskMap, recursionStack);
            }
        }

        return ordering;
    }

    private int dfs(int i, Task task, Map<String, Boolean> visited, Task[] ordering, Map<String, Task> taskMap, Set<String> recursionStack) throws IllegalArgumentException {
        visited.put(task.getName(), true);
        recursionStack.add(task.getName());


        if (task.getRequires() != null) {
            for (String required : task.getRequires()) {
                if (recursionStack.contains(required)) {
                    throw new IllegalArgumentException("Cycle detected in the graph");
                }
                if (!visited.get(required)) {
                    i = dfs(i, taskMap.get(required), visited, ordering, taskMap, recursionStack);
                }
            }
        }

        recursionStack.remove(task.getName());
        ordering[i] = new Task(task.getName(), task.getCommand(), null);
        return i - 1;
    }

    public Task[] reverseTaskArray(Task[] array) {
        List<Task> list = Arrays.asList(array);
        Collections.reverse(list);
        return list.toArray(new Task[0]);
    }
}
