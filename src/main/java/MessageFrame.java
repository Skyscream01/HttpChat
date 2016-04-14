import com.sun.deploy.net.HttpResponse;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;


public class MessageFrame implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=cp1251");

            String name = t.getRequestURI().getRawQuery();

            ArrayList<String> list = new ArrayList<String>();
            list = Database.getChatMessages();
            String formatted="";
            for(int i=0;i<list.size();i++)
            {
                formatted=formatted+list.get(i);
            }




            String response = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"content-type\" content=\"text/html; charset=cp1251\">" +
                    "<META HTTP-EQUIV=\"refresh\" CONTENT=\"4\">"+
                    "<title>Test</title>"+
                    "    <style>\n" +
                    "\n" +
                    "#section {\n" +
                    "    width:800px;\n" +
                    "    float:left;\n" +
                    "    padding:10px;\n" +
                    "font-color: white;\t \t \n" +
                    "}\n" +
                    "#footer {\n" +
                    "    background-color:black;\n" +
                    "    color:white;\n" +
                    "    clear:both;\n" +
                    "    text-align:center;\n" +
                    "   padding:35px;\t \t \n" +
                    "    width:800px;\n" +
                    "}\n" +
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
                    "input, textarea {\n" +
                    "  background-color : #2C3539;"+
                    "    color: white;\n" +
                    "}"+
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "\n" +
                    "<div id=\"section\">\n" + "<textarea rows=\"25\" cols=\"150\">\n" +
                    formatted + "</textarea>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";

            URLEncoder.encode(response, "UTF-8");
            t.sendResponseHeaders(200,0);


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
