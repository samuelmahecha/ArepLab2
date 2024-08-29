package org.example;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;

public class SimpleWebServer {
    private static final Map<String, RouteHandler> routes = new HashMap<>();
    private static final int PORT = 8080;
    public static String WEB_ROOT = "src/main/resources/webroot";

    public static void get(String path, RouteHandler handler) {
        routes.put(path, handler);
    }

    public static void staticfiles(String folder) {
        WEB_ROOT = folder;
    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);

        // Ejemplo de uso del nuevo framework
        staticfiles("src/main/resources");
        get("/App/hello", (req, res) -> "Hello " + req.getValue("name"));
        get("/App/pi", (req, res) -> String.valueOf(Math.PI));

        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket, routes));
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Map<String, RouteHandler> routes;

    public ClientHandler(Socket socket, Map<String, RouteHandler> routes) {
        this.clientSocket = socket;
        this.routes = routes;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String fileRequested = tokens[1];
            String queryString = null;

            if (fileRequested.contains("?")) {
                String[] parts = fileRequested.split("\\?");
                fileRequested = parts[0];
                queryString = parts.length > 1 ? parts[1] : null;
            }

            if (method.equals("GET") && routes.containsKey(fileRequested)) {
                RouteHandler handler = routes.get(fileRequested);
                String response = handler.handle(new Request(queryString), new Response());

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println();
                out.println(response);
            } else if (method.equals("GET")) {
                handleGetRequest(fileRequested, out, dataOut);
            } else if (method.equals("POST")) {
                handlePostRequest(fileRequested, in, out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos auxiliares para manejar GET y POST
    public void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(SimpleWebServer.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);

        if (file.exists()) {
            byte[] fileData = readFileData(file, fileLength);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: " + content);
            out.println("Content-length: " + fileLength);
            out.println();
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-type: text/html");
            out.println();
            out.flush();
            out.println("<html><body><h1>File Not Found</h1></body></html>");
            out.flush();
        }
    }

    private void handlePostRequest(String fileRequested, BufferedReader in, PrintWriter out) throws IOException {
        // Manejo de POST según sea necesario
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) fileIn.close();
        }
        return fileData;
    }
}

@FunctionalInterface
interface RouteHandler {
    String handle(Request req, Response res);
}

class Request {
    private final String queryString;

    public Request(String queryString) {
        this.queryString = queryString;
    }

    public String getValue(String name) {
        if (queryString == null) return null;
        for (String param : queryString.split("&")) {
            String[] pair = param.split("=");
            if (pair[0].equals(name)) {
                return pair.length > 1 ? pair[1] : null;
            }
        }
        return null;
    }
}

class Response {
    // Métodos para manejar la respuesta
}
