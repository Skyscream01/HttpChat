import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Logz.Init();
        HttpServer server = HttpServer.create(new InetSocketAddress(7779), 0);
        server.createContext("/", new HomePage());
        server.createContext("/register", new Register());
        server.createContext("/login", new LoginPage());
        server.createContext("/chat", new ChatWindow());
        server.createContext("/sendmessage", new SendMessage());
        server.createContext("/messageframe", new MessageFrame());
        server.createContext("/inputframe", new InputFrame());

        //Desktop block
        server.createContext("/desktopregistration", new DesktopRegistration());
        server.createContext("/desktoplogin", new DesktopLogin());
        server.createContext("/desktopgetmessages", new DesktopGetMessages());
        server.createContext("/desktopwritemessage", new DesktopWriteMessage());

        server.setExecutor(null);
        server.start();


    }

}

