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
public class DesktopRegistration implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        try
        {
            Logz.log.info("Desktop registration");
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html");

            String s = t.getRequestURI().getRawQuery();
            Map<String, String> map = ParseGET.queryToMap(s);

            String login = map.get("login");
            if (Database.checkUser(login))
            {
                String password = map.get("password");
                Database.insertClient(login, password);
                t.sendResponseHeaders(200, 0);
                OutputStream os = t.getResponseBody();
                os.write(0);
                os.close();
                Logz.log.info("Desktop registration successfull");
            }
            else
            {
                t.sendResponseHeaders(500, 0);
                OutputStream os = t.getResponseBody();
                os.write(0);
                os.close();
                Logz.log.info("Desktop registration unsuccessfull");
            }


        }
        catch (SQLException e)
        {
            Logz.log.error("Error during desktop registration", e);
        }
        catch (ClassNotFoundException e)
        {
            Logz.log.error("Error during desktop registration", e);
        }
    }

}
