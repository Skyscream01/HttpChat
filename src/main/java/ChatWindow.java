import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ihar.Kastsenich on 4/12/2016.
 */
public class ChatWindow implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");

            String name = t.getRequestURI().getRawQuery();

            name = name.replace("name=", "");


            ArrayList<String> list = new ArrayList<String>();
            list = Database.getChatMessages();

            String response = "<!DOCTYPE html>\n" +
                    "\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" +
                    "    <style>\n" +
                    "body{\n" +
                    "        background-image: url(argyle.png);\n" +
                    "        background-repeat:repeat;\n" +
                    "}"+

                    " .content\n" +
                    "        {\n" +
                    "        width: 1100px;\n" +
                    "        height: 430px;\n" +
                    "        float: center;\n" +
                    "        padding:10px;\n" +
                    "        background-color:black;\n" +
                    "        }\n" +
                    ".footer\n" +
                    "        {\n" +
                    "        background-color:black;\n" +
                    "        color:white;\n" +
                    "        clear:both;\n" +
                    "        text-align:center;\n" +
                    "        padding:10px;\n" +
                    "        width:1100px;\n" +
                    "        height: 250px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<iframe class = \"content\"   src=\"http://192.168.13.202:7779/messageframe?name="+name+"\" frameborder=\"0\">\n" +
                    "<p>Your browser does not support iframes.</p>\n" +
                    "</iframe>\n" +
                    "<iframe class = \"footer\" src=\"http://192.168.13.202:7779/inputframe?name="+name+"\" frameborder=\"0\">\n" +
                    "<p>Your browser does not support iframes.</p>\n" +
                    "</iframe>\n" +
                    "</body>\n" +
                    "</html>";

            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }
        catch (ClassNotFoundException e) {
        }
        catch (SQLException e) {
        }
    }
}