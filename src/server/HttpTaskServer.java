package server;

import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.Subtask;
import model.Task;
import service.InMemoryTaskManager;
import service.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {

    final static InMemoryTaskManager manager = Managers.getDefault();
    final static int port = 8080;
    static HttpServer server;

    public static void main(String[] args) throws IOException {
        init();
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/tasks", new TasksHandler(manager));
        server.createContext("/subtasks", new SubtasksHandler(manager));
        server.createContext("/epics", new EpicsHandler(manager));
        server.createContext("/history", new HistoryHandler(manager));
        server.createContext("/prioritized", new PrioritizedHandler(manager));

        server.start();
    }

    private static void init() {
        Task buySock = new Task("Купить носки", "Закончились носки");
        manager.createTask(buySock);

        Task makeDinner = new Task("Сделать ужин", "Хочется кушать",
                LocalDateTime.now().plus(Duration.ofDays(1)), Duration.ofHours(1));
        manager.createTask(makeDinner);

        Epic goToShop = new Epic("Сходить в магазин", "Купить продукты");
        manager.createEpic(goToShop);

        Subtask buyMilk = new Subtask("Купить молоко", "Молоко кончается", 3,
                LocalDateTime.now().plus(Duration.ofHours(5)), Duration.ofHours(5));
        Subtask buyMeat = new Subtask("Купить мясо", "Кончается мясо", 3,
                LocalDateTime.now(), Duration.ofHours(3));
        manager.createSubtask(buyMilk);
        manager.createSubtask(buyMeat);
    }
}
