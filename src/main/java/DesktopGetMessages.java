import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


public class DesktopGetMessages implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try
        {
            Logz.log.info("Getting messages for desktop app");
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");

            String name = t.getRequestURI().getRawQuery();

            ArrayList<String> list = new ArrayList<String>();
            list = Database.getChatMessages();

            String formatted="";
            for(int i=0;i<list.size();i++)
            {
                formatted=formatted+list.get(i);
            }


            URLDecoder.decode(formatted, "UTF-8");
            t.sendResponseHeaders(200, 0);

            OutputStream os = t.getResponseBody();
            os.write(formatted.getBytes());
            os.close();
            Logz.log.info("Messages are sent");
        }
        catch (SQLException e)
        {
Logz.log.error("Error during getting messages for desktop client", e);
        }
        catch (ClassNotFoundException e)
        {
            Logz.log.error("Error during getting messages for desktop client", e);
        }
    }

}
