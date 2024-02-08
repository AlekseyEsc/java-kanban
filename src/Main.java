import model.*;
import service.InMemoryTaskManager;
import service.Managers;

public class Main {
    private static InMemoryTaskManager manager;

    public static void main(String[] args) {
        System.out.println("Поехали!");

        manager = Managers.getDefault();
        addAllTask();
        printAllTasks();
    }

    private static void addAllTask() {
        Task buySock = new Task("Купить носки", "Закончились носки");
        manager.createTask(buySock);

        Task makeDinner = new Task("Сделать ужин", "Хочется кушать");
        manager.createTask(makeDinner);

        Epic goToShop = new Epic("Сходить в магазин", "Купить продукты");
        manager.createEpic(goToShop);

        Subtask buyMilk = new Subtask("Купить молоко", "Молоко кончается", goToShop);
        Subtask buyMeat = new Subtask("Купить мясо", "Кончается мясо", goToShop);
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
