package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import exceptions.NotFoundException;
import exceptions.NullTaskException;
import model.Subtask;
import server.adapters.*;
import service.InMemoryTaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


class SubtasksHandler implements HttpHandler {
    private final InMemoryTaskManager manager;
    private final Gson gson;

    public SubtasksHandler(InMemoryTaskManager manager) {
        this.manager = manager;
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) {

        String method = exchange.getRequestMethod();
        String[] path = exchange.getRequestURI().getPath().split("/");

        switch (method) {
            case "GET":
                handlerGetSubtasks(exchange, path);
                break;
            case "POST":
                handlerPostSubtasks(exchange, path);
                break;
            case "DELETE":
                handlerDeleteSubtasks(exchange);

        }
    }

    private void handlerGetSubtasks(HttpExchange exchange, String[] path) {
        switch (path.length) {
            case 2:
                List<Subtask> subtasks = manager.getAllSubtasks();
                String jsonSubtasks = gson.toJson(subtasks);
                writeResponse(jsonSubtasks, exchange, 200);
                break;
            case 3:
                try {
                    Subtask subtask = manager.getSubtask(Integer.parseInt(path[2]));
                    String jsonSubtask = gson.toJson(subtask);
                    writeResponse(jsonSubtask, exchange, 200);
                } catch (NumberFormatException e) {
                    writeResponse("Subtasks id need be integer", exchange, 400);
                } catch (NotFoundException e) {
                    writeResponse("Subtask not founded", exchange, 404);
                }
                break;
            default:
                writeResponse("Incorrect request", exchange, 400);
        }
    }

    private void handlerPostSubtasks(HttpExchange exchange, String[] path) {
        Subtask subtask;

        try (InputStream inputStream = exchange.getRequestBody()) {
            String jsonSubtask = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            subtask = gson.fromJson(jsonSubtask, Subtask.class);

        } catch (IOException e) {
            writeResponse("Incorrect request", exchange, 400);
            return;
        } catch (NullTaskException e) {
            writeResponse("Internal Server Error", exchange, 500);
            return;
        }

        switch (path.length) {
            // create
            case 2:
                try {
                    manager.getSubtask(subtask.getId());
                    writeResponse("Internal Server Error", exchange, 500);
                } catch (NotFoundException e) {
                    manager.createSubtask(subtask);
                    writeResponse("Create subtask success", exchange, 201);
                }
                break;
            // update
            case 3:
                try {
                    manager.updateSubtask(subtask);
                    writeResponse("Update subtask success", exchange, 201);
                } catch (NullTaskException e) {
                    writeResponse("Internal Server Error", exchange, 500);
                }
                break;
            default:
                writeResponse("Incorrect request", exchange, 400);
        }
    }

    private void handlerDeleteSubtasks(HttpExchange exchange) {
        Subtask subtask;

        try (InputStream inputStream = exchange.getRequestBody()) {
            String jsonSubtask = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            subtask = gson.fromJson(jsonSubtask, Subtask.class);

        } catch (IOException e) {
            writeResponse("Incorrect request", exchange, 400);
            return;
        } catch (NullTaskException e) {
            writeResponse("Internal Server Error", exchange, 500);
            return;
        }

        try {
            manager.removeSubtask(subtask.getId());
        } catch (NotFoundException ignored) {
        }
        writeResponse("Delete task success", exchange, 201);
    }

    private void writeResponse(String body, HttpExchange exchange, int code) {
        Headers headers = exchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(code, body.getBytes().length);
            os.write(body.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }
    }
}