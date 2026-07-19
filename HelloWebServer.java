import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.io.OutputStream;

public class HelloWebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpContext context = server.createContext("/");

        context.setHandler(exchange -> {
            System.out.println("Server Received HTTP Request");

            String query = exchange.getRequestURI().getQuery();

            String course_value = "";
            String name_value = "";

            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue[0].equals("course")) {
                    course_value = keyValue[1];
                } else if (keyValue[0].equals("name")) {
                    name_value = keyValue[1].replace("%20", " ");
                }
            }

            String response = "Hello " + name_value + "! <br/> I hope you are having a great "
                    + java.time.LocalDate.now();

            exchange.getResponseHeaders().add("Content-type", "text/html");
            exchange.sendResponseHeaders(200, response.length());

            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.close();
        });

        server.start();
        System.out.println("Hello Web Server Running...");
    }
}
