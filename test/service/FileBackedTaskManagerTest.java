package service;

import exceptions.ManagerSaveException;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;



class FileBackedTaskManagerTest {


    @DisplayName("Save and load in file")
    @Test
    void loadFromFile() {
        try {
            File fileTest = File.createTempFile("backup", "csv");
            FileBackedTaskManager manager = Managers.getFileBackedTaskManager(fileTest);
            addAllTask(manager);
            addHistoryAllTasks(manager);

            FileBackedTaskManager loadFromFileManager = FileBackedTaskManager.loadFromFile(fileTest);

            Assertions.assertEquals(manager.taskCounts, loadFromFileManager.taskCounts,
                    "Error task count (generate id)");
            Assertions.assertEquals(manager.getAllEpics(), loadFromFileManager.getAllEpics(),
                    "Epics not equals");
            Assertions.assertEquals(manager.getAllTasks(), loadFromFileManager.getAllTasks(),
                    "Tasks not equals");
            Assertions.assertEquals(manager.getAllSubtasks(), loadFromFileManager.getAllSubtasks(),
                    "Subtask not equals");
            Assertions.assertEquals(manager.historyManager.getHistory(), loadFromFileManager.historyManager.getHistory(),
                    "History not equals");
        } catch (IOException e) {
            throw new ManagerSaveException("Error IO");
        }
        System.out.println("END");
    }

    private void addAllTask(FileBackedTaskManager manager) {
        Task buySock = new Task("Купить носки", "Закончились носки");
        manager.createTask(buySock);

        Task makeDinner = new Task("Сделать ужин", "Хочется кушать");
        manager.createTask(makeDinner);

        Epic goToShop = new Epic("Сходить в магазин", "Купить продукты");
        manager.createEpic(goToShop);

        Subtask buyMilk = new Subtask("Купить молоко", "Молоко кончается", 3);
        Subtask buyMeat = new Subtask("Купить мясо", "Кончается мясо", 3);
        manager.createSubtask(buyMilk);
        manager.createSubtask(buyMeat);
    }

    private void addHistoryAllTasks(FileBackedTaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksByEpic(epic)) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }
}