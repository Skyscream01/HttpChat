import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.lang.String;

/**
 * Created by Ihar.Kastsenich on 4/13/2016.
 */
public class SendMessage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try {
            String name = t.getRequestURI().getRawQuery();
            name = name.replace("name=", "");
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            h.set("Location", "http://192.168.13.202:7779/inputframe?name="+ name);



            InputStreamReader isr = new InputStreamReader(t.getRequestBody());
            BufferedReader reader = new BufferedReader(isr);
            String query = reader.readLine();

           String decodedvalue = URLDecoder.decode(query, "UTF-8");

            String[] s = decodedvalue.split("=", 2);
            decodedvalue = s[1];

            if(name != null) {
               // decodedvalue = decodedvalue.replaceAll("[^a-zA-Z0-9]"," ");
                Database.insertMessage(name, decodedvalue);
            }
            else
            {
                Database.insertMessage("guest", decodedvalue);
            }

            t.sendResponseHeaders(302, 0);
            OutputStream os = t.getResponseBody();
            os.write(0);
            os.close();

        }
        catch (SQLException e) {}
        catch (ClassNotFoundException e){}

    }
}