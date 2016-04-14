import com.sun.javafx.collections.MappingChange;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;


public class Register implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html");

            String s = t.getRequestURI().getRawQuery();
            if (s == null) {
                String response = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<body>\n" +
                        "\n" +
                        "<form action=\"http://192.168.13.202:7779/register\" method=\"get\"\">\n" +
                        "    Login: <input type=\"text\" name=\"login\" value=\"\"><br>\n" +
                        "    Password: <input type=\"password\" name=\"password\" value=\"\"><br>\n" +
                        "    <input type=\"submit\" value=\"Register\">\n" +
                        "</form>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";


                t.sendResponseHeaders(200, response.length());

                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                //получил логин и пароль. паршу. потом будет запись в бд
                Map<String, String> map = ParseGET.queryToMap(s);

                String login = map.get("login");
                String password = map.get("password");
                Database.insertClient(login, password);


                //страница успешной регистрации.
                t.sendResponseHeaders(200, 0);

                String response = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<body>\n" +
                        "\n" +
                        "<p>Registration performed successfully!</p>\n" +
                        "<a href=\"http://192.168.13.202:7779/login\">Go to the Login page</a>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

        }
        catch (SQLException x) {
        }
        catch (ClassNotFoundException x) {
        }

    }
}
