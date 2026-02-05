package com.example.task_tracker;

import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.TaskStatus;
import com.example.task_tracker.storage.TaskStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TaskTrackerApplication implements CommandLineRunner {

	private final TaskStorage storage = new TaskStorage();

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		if (args.length == 0) {
			printHelp();
			return;
		}

		String command = args[0];

		switch (command) {
			case "add":
				handleAdd(args);
				break;
			case "list":
				handleList(args);
				break;
			case "update":
				handleUpdate(args);
				break;
			case "delete":
				handleDelete(args);
				break;
			case "progress":
				handleProgress(args);
				break;
			case "done":
				handleDone(args);
				break;
			default:
				System.out.println("❌ Unknown command");
				printHelp();
		}
	}

	private void handleAdd(String[] args) {

		if (args.length < 2) {
			System.out.println("❌ Please provide task description");
			return;
		}

		String description = args[1];

		List<Task> tasks = storage.readTasks();
		int newId = storage.getNextId(tasks);

		Task task = new Task(newId, description);
		tasks.add(task);

		storage.writeTasks(tasks);

		System.out.println("✅ Task added successfully (ID: " + newId + ")");
	}


	private void handleList(String[] args) {

		List<Task> tasks = storage.readTasks();

		if (tasks.isEmpty()) {
			System.out.println("📭 No tasks found");
			return;
		}

		// list (all)
		if (args.length == 1) {
			printTasks(tasks);
			return;
		}

		String filter = args[1];

		switch (filter) {
			case "done":
				printTasks(tasks.stream()
						.filter(t -> t.getStatus() == TaskStatus.DONE)
						.collect(java.util.stream.Collectors.toList()));
				break;

			case "todo":
				printTasks(tasks.stream()
						.filter(t -> t.getStatus() == TaskStatus.TODO)
						.collect(java.util.stream.Collectors.toList()));
				break;

			case "progress":
				printTasks(tasks.stream()
						.filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS)
						.collect(java.util.stream.Collectors.toList()));
				break;

			default:
				System.out.println("❌ Invalid list filter");
		}
	}


	private void printTasks(List<Task> tasks) {

		if (tasks.isEmpty()) {
			System.out.println("📭 No matching tasks");
			return;
		}

		System.out.println("ID | STATUS       | DESCRIPTION");
		System.out.println("--------------------------------");

		for (Task task : tasks) {
			System.out.printf(
					"%-2d | %-11s | %s%n",
					task.getId(),
					task.getStatus(),
					task.getDescription()
			);
		}
	}



	private void handleUpdate(String[] args) {

		if (args.length < 3) {
			System.out.println("❌ Usage: update <id> \"new description\"");
			return;
		}

		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("❌ Invalid task id");
			return;
		}

		String newDescription = args[2];

		List<Task> tasks = storage.readTasks();

		Task task = tasks.stream()
				.filter(t -> t.getId() == id)
				.findFirst()
				.orElse(null);

		if (task == null) {
			System.out.println("❌ Task with id " + id + " not found");
			return;
		}

		task.setDescription(newDescription);
		storage.writeTasks(tasks);

		System.out.println("✏️ Task updated successfully");
	}

	private void handleDelete(String[] args) {

		if (args.length < 2) {
			System.out.println("❌ Usage: delete <id>");
			return;
		}

		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("❌ Invalid task id");
			return;
		}

		List<Task> tasks = storage.readTasks();

		boolean removed = tasks.removeIf(t -> t.getId() == id);

		if (!removed) {
			System.out.println("❌ Task with id " + id + " not found");
			return;
		}

		storage.writeTasks(tasks);
		System.out.println("🗑️ Task deleted successfully");
	}



	private void handleProgress(String[] args) {

		if (args.length < 2) {
			System.out.println("❌ Usage: progress <id>");
			return;
		}

		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("❌ Invalid task id");
			return;
		}

		List<Task> tasks = storage.readTasks();

		Task task = tasks.stream()
				.filter(t -> t.getId() == id)
				.findFirst()
				.orElse(null);

		if (task == null) {
			System.out.println("❌ Task with id " + id + " not found");
			return;
		}

		task.setStatus(TaskStatus.IN_PROGRESS);
		storage.writeTasks(tasks);

		System.out.println("🚧 Task marked as IN_PROGRESS");
	}

	private void handleDone(String[] args) {

		if (args.length < 2) {
			System.out.println("❌ Usage: done <id>");
			return;
		}

		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("❌ Invalid task id");
			return;
		}

		List<Task> tasks = storage.readTasks();

		Task task = tasks.stream()
				.filter(t -> t.getId() == id)
				.findFirst()
				.orElse(null);

		if (task == null) {
			System.out.println("❌ Task with id " + id + " not found");
			return;
		}

		task.setStatus(TaskStatus.DONE);
		storage.writeTasks(tasks);

		System.out.println("✅ Task marked as DONE");
	}

	private void printHelp() {
		System.out.println(
				"Task Tracker CLI\n" +
						"=================\n" +
						"Commands:\n" +
						"  add \"task description\"   -> Add new task\n" +
						"  list                     -> List all tasks\n" +
						"  list done                -> List completed tasks\n" +
						"  list todo                -> List pending tasks\n" +
						"  list progress            -> List in-progress tasks\n" +
						"  update <id> \"text\"       -> Update task\n" +
						"  delete <id>              -> Delete task\n" +
						"  progress <id>            -> Mark task in progress\n" +
						"  done <id>                -> Mark task done\n"
		);
	}
}
