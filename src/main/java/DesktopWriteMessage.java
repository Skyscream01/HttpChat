import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


public class DesktopWriteMessage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try
        {
            Logz.log.info("Desktop recieving messages");
            String name = t.getRequestURI().getRawQuery();
            name = name.replace("name=", "");

            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");

            InputStreamReader isr = new InputStreamReader(t.getRequestBody());
            BufferedReader reader = new BufferedReader(isr);

            String query = reader.readLine();
            String decodedvalue = URLDecoder.decode(query, "UTF-8");

            String[] s = decodedvalue.split("=", 2);
            decodedvalue = s[1];

            if(name != null) {
               Database.insertMessage(name, decodedvalue);
            }
            else
            {
                Database.insertMessage("guest", decodedvalue);
            }

            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            os.write(0);
            os.close();

        }
        catch (SQLException e)
        {
            Logz.log.error("Error during desktop recieving messages", e);
        }
        catch (ClassNotFoundException e)
        {
            Logz.log.error("Error during desktop recieving messages", e);
        }
    }

}
