package pl.javastart.tasksmanager.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.javastart.tasksmanager.task.model.Category;
import pl.javastart.tasksmanager.task.model.Task;
import pl.javastart.tasksmanager.task.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    private TaskRepository taskRepository;


    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        boolean completed = false;
        List<Task> toDoTasksList = taskRepository.findAllByCompletedOrderByDeadlineDate(completed);
        model.addAttribute("toDoTasksList", toDoTasksList);
        model.addAttribute("localDateNow", LocalDate.now());
        return "home";
    }

    @GetMapping("/newTask")
    public String addNewTask(Model model) {
        model.addAttribute("newTask", new Task());
        return "newTaskForm";
    }

    @PostMapping("/addNewTask")
    public String addNewTask(Task newTask) {
        taskRepository.save(newTask);
        return "redirect:/";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Task> allTasksList = taskRepository.findByOrderByDeadlineDate();
        model.addAttribute("allTasksList", allTasksList);
        model.addAttribute("localDateNow", LocalDate.now());
        return "all";
    }

    @GetMapping("/completed")
    public String getCompleted(Model model) {
        boolean completed = true;
        List<Task> completedTasksList = taskRepository.findAllByCompletedOrderByDeadlineDate(completed);
        model.addAttribute("completedTasksList", completedTasksList);
        return "completed";
    }

    @GetMapping("/task/{category}")
    public String getTaskByCategory(@PathVariable Category category, Model model) {
        List<Task> tasksListByCategory = taskRepository.findByCategoryOrderByDeadlineDate(category);
        model.addAttribute("tasksListByCategory", tasksListByCategory);
        model.addAttribute("localDateNow", LocalDate.now());
        return "category";
    }

    @GetMapping("/modify/task/{id}")
    public String modifyTask(@PathVariable Long id, Model model) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isPresent()) {
            Task taskToModify = taskById.get();
            model.addAttribute("taskToModify", taskToModify);
            return "modifyTaskForm";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/task/{id}/update")
    public String updateTaskById(@PathVariable Long id, Task task, Model model) {
        Optional<Task> taskById = taskRepository.findById(id);
        model.addAttribute("id", id);
        if (taskById.isPresent()) {
            Task taskToUpdate = taskById.get();
            taskToUpdate.setName(task.getName());
            taskToUpdate.setCategory(task.getCategory());
            taskToUpdate.setCompleted(task.isCompleted());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setDeadlineDate(task.getDeadlineDate());
            taskRepository.save(taskToUpdate);
            return "redirect:/";

        } else {
            return "error";
        }
    }

    @GetMapping("/task/{id}/checkCompleted")
    public String checkCompleted(@PathVariable Long id, Model model) {
        Optional<Task> taskById = taskRepository.findById(id);
        model.addAttribute("id", id);
        if (taskById.isPresent()) {
            Task checkCompleted = taskById.get();
            checkCompleted.setCompleted(true);
            taskRepository.save(checkCompleted);
            return "redirect:/";

        } else {
            return "error";
        }
    }

}
