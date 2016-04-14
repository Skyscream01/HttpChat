import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class LoginPage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try {
            String name;
            Boolean success = false;
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");


            String s = t.getRequestURI().getRawQuery();
            if (s == null) {
                String response = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Login Page</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<form action=\"http://192.168.13.202:7779/login\" method=\"get\">\n" +
                        "    Login: <input type=\"text\" name=\"login\" value=\"\"><br>\n" +
                        "    Password: <input type=\"text\" name=\"password\" value=\"\"><br>\n" +
                        "    <input type=\"submit\" value=\"Log In\">\n" +
                        "</form>\n" +
                        "\n" +
                        "<a href = http://192.168.13.202:7779/chat?name=guest> Log in as Guest</a>"+
                        "</body>\n" +
                        "</html>";
                t.sendResponseHeaders(200, response.length());

                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } else {
                Map<String, String> map = ParseGET.queryToMap(s);

                String login = map.get("login");
                String password = map.get("password");
                success = Database.credentialCheck(login, password);

                if (success)
                {
                    name = login;
                    h.set("Location", "http://192.168.13.202:7779/chat?name=" + name);
                    t.sendResponseHeaders(302, 3);

                    System.out.println("success");
                    }
                else
                {
                    System.out.println("Invalid credentials. Try again!");
                }
            }
        }
        catch (SQLException e)
        {

        }
        catch (ClassNotFoundException e)
        {

        }
    }
}