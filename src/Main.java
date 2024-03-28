import model.*;
import service.FileBackedTaskManager;
import service.Managers;
import service.TaskManager;

import java.io.File;

public class Main {
    private static FileBackedTaskManager manager;

    public static void main(String[] args) {
        System.out.println("Поехали!");

        manager = Managers.getFileBackedTaskManager();
        addAllTask();
        printAllTasks();
        File fileBackup = new File("resources/backup.csv");

        TaskManager loadFromFileManager = FileBackedTaskManager.loadFromFile(fileBackup);
        System.out.println("END");
    }

    private static void addAllTask() {
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

    private static void printAllTasks() {
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

        System.out.println("История:");
        for (Task task : manager.historyManager.getHistory()) {
            System.out.println(task);
        }
    }
}
