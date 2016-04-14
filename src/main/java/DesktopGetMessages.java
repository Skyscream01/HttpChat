import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ihar.Kastsenich on 4/14/2016.
 */
public class DesktopGetMessages implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try
        {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");

            String name = t.getRequestURI().getRawQuery();

            ArrayList<String> list = new ArrayList<String>();
            list = Database.getChatMessages();

           /* for(int i = 0; i<list.size(); i++)
            {
                String s = URLEncoder.encode(list.get(i), "UTF-8");
                list.get(i).replace(list.get(i), s);
            } */

            String response = list.toString();
            URLEncoder.encode(response, "UTF-8");
            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        catch (SQLException e)
        {

        }
        catch (ClassNotFoundException e)
        {

        }
    }

}
