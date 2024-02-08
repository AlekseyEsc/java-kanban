import model.*;
import service.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task buySock = new Task("Купить носки", "Закончились носки");
        inMemoryTaskManager.createTask(buySock);

        Task makeDinner = new Task("Сделать ужин", "Хочется кушать");
        inMemoryTaskManager.createTask(makeDinner);

        Epic goToShop = new Epic("Сходить в магазин", "Купить продукты");
        inMemoryTaskManager.createEpic(goToShop);

        Subtask buyMilk = new Subtask("Купить молоко", "Молоко кончается", goToShop);
        Subtask buyMeat = new Subtask("Купить мясо", "Кончается мясо", goToShop);
        inMemoryTaskManager.createSubtask(buyMilk);
        inMemoryTaskManager.createSubtask(buyMeat);

        inMemoryTaskManager.updateEpic(goToShop);

        System.out.println("------------");
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println("------------");
        System.out.println("CHANGE STATUS");
        System.out.println("------------");

        inMemoryTaskManager.inProgress(makeDinner);
        inMemoryTaskManager.inProgress(buySock);
        inMemoryTaskManager.inDone(buyMilk);
        inMemoryTaskManager.inDone(buyMeat);

        inMemoryTaskManager.updateSubtask(buyMeat);
        inMemoryTaskManager.updateSubtask(buyMilk);
        inMemoryTaskManager.updateTask(buySock);
        inMemoryTaskManager.updateEpic(goToShop);

        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println("------------");
        System.out.println(inMemoryTaskManager.getTask(1));
        inMemoryTaskManager.removeAllTasks();
        System.out.println(inMemoryTaskManager.getAllTasks());
    }
}
