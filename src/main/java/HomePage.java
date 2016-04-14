import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


    class HomePage implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type","text/html");

            String response = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Home</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<a href=\"http://192.168.13.202:7779/register\">Register</a>\n" +
                    "<a href=\"http://192.168.13.202:7779/login\">Log In</a>\n" +
                    "</body>\n" +
                    "</html>";



            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
}
