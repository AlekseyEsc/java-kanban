import model.*;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = new TaskManager();

        Task buySock = new Task("Купить носки", "Закончились носки");
        taskManager.createTask(buySock);

        Task makeDinner = new Task("Сделать ужин", "Хочется кушать");
        taskManager.createTask(makeDinner);

        Epic goToShop = new Epic("Сходить в магазин", "Купить продукты");
        taskManager.createEpic(goToShop);

        Subtask buyMilk = new Subtask("Купить молоко", "Молоко кончается", goToShop);
        Subtask buyMeat = new Subtask("Купить мясо", "Кончается мясо", goToShop);
        taskManager.createSubtask(buyMilk);
        taskManager.createSubtask(buyMeat);

        taskManager.updateEpic(goToShop);

        System.out.println("------------");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getTask(2));
        System.out.println("------------");
        System.out.println("CHANGE STATUS");
        System.out.println("------------");

        taskManager.inProgress(makeDinner);
        taskManager.inProgress(buySock);
        taskManager.inDone(buyMilk);
        taskManager.inDone(buyMeat);

        taskManager.updateSubtask(buyMeat);
        taskManager.updateSubtask(buyMilk);
        taskManager.updateTask(buySock);
        taskManager.updateEpic(goToShop);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getEpic(3));
        System.out.println(taskManager.getTask(2));
        System.out.println("------------");
        System.out.println(taskManager.getTask(1));
        taskManager.removeAllTasks();
        System.out.println(taskManager.getAllTasks());
    }
}
