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
                        "<html >\n" +
                        "  <head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Login Form</title>  \n" +
                        "<style>    \n" +
                        "    @import url(http://fonts.googleapis.com/css?family=Open+Sans:400,700);\n" +
                        "\n" +
                        "body {\n" +
                        "  background: url(http://www.zastavki.com/pictures/originals/2015/Computers_Digital_computer_tunnel_096050_.jpg) no-repeat;\n" +
                        "  font-family: 'Open Sans', sans-serif;\n" +
                        "}\n" +
                        "\n" +
                        ".login {\n" +
                        "  width: 400px;\n" +
                        "  margin: 16px auto;\n" +
                        "  font-size: 16px;\n" +
                        "}\n" +
                        "\n" +
                        "/\n" +
                        ".login-header,\n" +
                        ".login p {\n" +
                        "  margin-top: 0;\n" +
                        "  margin-bottom: 0;\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        ".login-triangle {\n" +
                        "  width: 0;\n" +
                        "  margin-right: auto;\n" +
                        "  margin-left: auto;\n" +
                        "  border: 12px solid transparent;\n" +
                        "  border-bottom-color: #28d;\n" +
                        "}\n" +
                        "\n" +
                        ".login-header {\n" +
                        "  background: #28d;\n" +
                        "  padding: 20px;\n" +
                        "  font-size: 1.4em;\n" +
                        "  font-weight: normal;\n" +
                        "  text-align: center;\n" +
                        "  text-transform: uppercase;\n" +
                        "  color: #fff;\n" +
                        "}\n" +
                        "\n" +
                        ".login-container {\n" +
                        "  background: #ebebeb;\n" +
                        "  padding: 12px;\n" +
                        "}\n" +
                        "\n" +
                        "/* Every row inside .login-container is defined with p tags */\n" +
                        ".login p {\n" +
                        "  padding: 12px;\n" +
                        "}\n" +
                        "\n" +
                        ".login input {\n" +
                        "  box-sizing: border-box;\n" +
                        "  display: block;\n" +
                        "  width: 100%;\n" +
                        "  border-width: 1px;\n" +
                        "  border-style: solid;\n" +
                        "  padding: 16px;\n" +
                        "  outline: 0;\n" +
                        "  font-family: inherit;\n" +
                        "  font-size: 0.95em;\n" +
                        "}\n" +
                        "\n" +
                        ".login input[type=\"username\"],\n" +
                        ".login input[type=\"password\"] {\n" +
                        "  background: #fff;\n" +
                        "  border-color: #bbb;\n" +
                        "  color: #555;\n" +
                        "}\n" +
                        "\n" +
                        "/* Text fields' focus effect */\n" +
                        ".login input[type=\"username\"]:focus,\n" +
                        ".login input[type=\"password\"]:focus {\n" +
                        "  border-color: #888;\n" +
                        "}\n" +
                        "\n" +
                        ".login input[type=\"submit\"] {\n" +
                        "  background: #28d;\n" +
                        "  border-color: transparent;\n" +
                        "  color: #fff;\n" +
                        "  cursor: pointer;\n" +
                        "}\n" +
                        "\n" +
                        ".login input[type=\"submit\"]:hover {\n" +
                        "  background: #17c;\n" +
                        "}\n" +
                        "\n" +
                        "/* Buttons' focus effect */\n" +
                        ".login input[type=\"submit\"]:focus {\n" +
                        "  border-color: #05a;\n" +
                        "}\n" +
                        "    \n" +
                        "        \n" +
                        "</style>\n" +
                        "    \n" +
                        "    \n" +
                        "    \n" +
                        "  </head>\n" +
                        "\n" +
                        "  <body>\n" +
                        "\n" +
                        "    <div class=\"login\">\n" +
                        "  <div class=\"login-triangle\"></div>\n" +
                        "  \n" +
                        "  <h2 class=\"login-header\">Register</h2>\n" +
                        "\n" +
                        "  <form class=\"login-container\" action=\"http://192.168.13.202:7779/register\" method=\"get\">\n" +
                        "    <p><input type=\"username\" placeholder=\"Username\" name=\"login\"></p>\n" +
                        "    <p><input type=\"password\" placeholder=\"Password\" name=\"password\"></p>\n" +
                        "    <p><input type=\"submit\" value=\"Register new user\"></p>\n" +
                        "  </form>\n" +
                        "</div>\n" +
                        "    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>    \n" +
                        "  </body>\n" +
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
