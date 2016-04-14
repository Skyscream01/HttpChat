import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Ihar.Kastsenich on 4/14/2016.
 */
public class DesktopLogin implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try
        {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html");

            String s = t.getRequestURI().getRawQuery();
            Map<String, String> map = ParseGET.queryToMap(s);

            String login = map.get("login");
            String password = map.get("password");

            boolean result;
            result = Database.credentialCheck(login, password);


            if (result)
            {
                t.sendResponseHeaders(200, 0);
                OutputStream os = t.getResponseBody();
                os.write(0);
                os.close();
            }
            else
            {
                t.sendResponseHeaders(500, 0);
                OutputStream os = t.getResponseBody();
                os.write(0);
                os.close();
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
