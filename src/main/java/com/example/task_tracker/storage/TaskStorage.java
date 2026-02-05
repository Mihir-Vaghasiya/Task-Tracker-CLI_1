package com.example.task_tracker.storage;


import com.example.task_tracker.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.json";
    private final ObjectMapper objectMapper;
    private final File file;

    public TaskStorage() {
        this.file = new File(FILE_NAME);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        initFile();
    }

    // if file not exist so create
    private void initFile() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                writeTasks(new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to create tasks.json file", e);
        }
    }

    // JSON → List<Task>
    public List<Task> readTasks() {
        try {
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading tasks.json", e);
        }
    }

    // List<Task> → JSON
    public void writeTasks(List<Task> tasks) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, tasks);
        } catch (IOException e) {
            throw new RuntimeException("Error writing tasks.json", e);
        }
    }

    // Next ID generate
    public int getNextId(List<Task> tasks) {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }
}
