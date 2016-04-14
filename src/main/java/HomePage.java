import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


    class HomePage implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type","text/html");

            String response = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" +
                    "<html style=\"overflow: hidden;\">\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://fonts.googleapis.com/css?family=Monofett\" />\n" +
                    "\t<title>Homepage</title>\n" +
                    "\t<style>\n" +
                    "\tbody {\n" +
                    "\t\tposition: relative;\n" +
                    "\t\tbackground: url(http://www.zastavki.com/pictures/originals/2015/Computers_Digital_computer_tunnel_096050_.jpg) no-repeat;\n" +
                    "\t\tbackground-color: #c7b39b; \n" +
                    "\t\twidth: 100%;\n" +
                    "\t\theight: 100%;\n" +
                    "\t\tbackground-size: 100%;\n" +
                    "   }\n" +
                    "\t.center-div\n" +
                    "{\n" +
                    "\t  position: relative;\n" +
                    "\t  top: 45%;\n" +
                    "\t  text-align: center\n" +
                    "  \n" +
                    "}\n" +
                    ".nice { \n" +
                    "\tdisplay: block;\n" +
                    "\tfont-family: Monofett;\n" +
                    "\tcolor: black;\n" +
                    "\tfont-size: 48px;\n" +
                    "\ttext-decoration: none;\n" +
                    "}\n" +
                    "\n" +
                    "}\n" +
                    "  </style>\n" +
                    "\n" +
                    "</head>\n" +
                    "<body>\n" +
                    " <div class=\"center-div\">\n" +
                    "\t <a href=\"http://192.168.13.202:7779/login\" class=\"nice\">Log in</a>\n" +
                    "\t <a href=\"http://192.168.13.202:7779/register\" class=\"nice\">Register</a>\n" +
                    " </div>\n" +
                    "</body>\n" +
                    "</html>";



            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
}
