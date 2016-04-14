import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ihar.Kastsenich on 4/13/2016.
 */
public class InputFrame implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {

            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");

            String name = t.getRequestURI().getRawQuery();
            name = name.replace("name=", "");

            String response = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" +
                    "<title>Test</title>"+
                    "    <style>\n" +
                    "\n" +

                    "#section {\n" +
                    "    width:800px;\n" +
                    "    float:left;\n" +
                    "    padding:10px;\t \t \n" +
                    "}\n" +
                    "#footer {\n" +
                    "    background-color:#565051;\n" +
                    "    color:white;\n" +
                    "    clear:both;\n" +
                    "    text-align:center;\n" +
                    "   padding:35px;\t \t \n" +
                    "    width:800px;\n" +
                    ""+
                    "}\n" +
                    "input, textarea {\n" +
                    "  background-color:#2C3539;\n" +
                    "  color:white;\n" +
                    "}"+
                    ".button {\n" +
                    "    background-color: #4CAF50;\n" +
                    "    border: none;\n" +
                    "    color: white;\n" +
                    "    padding: 15px 32px;\n" +
                    "    text-align: center;\n" +
                    "    text-decoration: none;\n" +
                    "    display: inline-block;\n" +
                    "    font-size: 16px;\n" +
                    "    margin: 4px 2px;\n" +
                    "    cursor: pointer;\n" +
                    "}"+
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "\n" +

                    "<div id=\"footer\">\n" +
                    "<form action=\"http://192.168.13.202:7779/sendmessage?name="+ name +"\" method=\"post\">\n" +
                    "<textarea rows=\"5\" cols=\"80\" name=\"message\" accept-charset=\"utf-8\"> </textarea>\n" +
                    "<input type=\"submit\" value=\"Submit\" class=\"button\" align=\"top\">\n" +
                    "    </form>"+

                    "\n" +
                    "</div>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();


    }
}

