import org.ini4j.Wini;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;


public class Database {
    public static String host;
    public static String port;
    public static String login;
    public static String password;
    public static String db;

    public static Connection Connect() throws ClassNotFoundException, SQLException, IOException {
        Wini ini = new Wini(new File("HttpTestv1/config.ini"));

        host = ini.get("host", "host");
        port = ini.get("host", "port");
        login = ini.get("host", "login");
        password = ini.get("host", "password");
        db = ini.get("host", "db");

        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db + "?user=" + login + "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8");
        return connection;
    }

    public static boolean insertClient(String name, String password) throws IOException, ClassNotFoundException, SQLException {
        Connection connect = Database.Connect();
        Statement statement = connect.createStatement();
        String id = null;
        Boolean flag = false;
        ResultSet rs = statement.executeQuery("SELECT \"id\" FROM \"user\" where name = '" + name + "'");
        while (rs.next()) {
            id = rs.getString("id");
        }

        if (id != null) {
            flag = false;
        } else {
            String insertClient = "INSERT INTO \"user\""
                    + "(name, password) " + "VALUES"
                    + "('" + name + "','" + password + "')";
            statement.executeUpdate(insertClient);
            flag = true;
        }


        Connect().close();
        return flag;
    }

    public static void insertMessage(String username, String message) throws IOException, ClassNotFoundException, SQLException {
        Connection connect = Database.Connect();
        Statement statement = connect.createStatement();
        Date dateTime = new Date();


        String insertMessage = "INSERT INTO \"messages\" (message, source_id, timestamp) VALUES ('" + message + "', '" + getUserId(username) + "','" + new Timestamp((dateTime).getTime()) + "');";

        statement.executeUpdate(insertMessage);
        Connect().close();
    }

    //напивать запрос на получение юзер айди нужного клиента
    public static String getUserId(String username) throws IOException, ClassNotFoundException, SQLException {
        Connection connect = Database.Connect();
        Statement stmt = connect.createStatement();
        String id = null;

        ResultSet rs = stmt.executeQuery("SELECT \"id\" FROM \"user\" where name = '" + username + "'");

        while (rs.next()) {
            id = rs.getString("id");
        }
        Connect().close();
        return id;


    }

    public static boolean credentialCheck(String login, String password) throws IOException, ClassNotFoundException, SQLException {
        Connection connect = Database.Connect();
        Statement stmt = connect.createStatement();
        String psswd;

        ResultSet rs = stmt.executeQuery("SELECT \"password\" FROM \"user\" where name = '" + login + "'");

        if (rs.next()) {
            psswd = rs.getString("password");
            if (psswd.equals(password)) {
                Connect().close();
                return true;
            } else {
                Connect().close();
                return false;
            }
        } else {
            Connect().close();
            return false;
        }

    }

    public static ArrayList<String> getChatMessages() throws IOException, ClassNotFoundException, SQLException {
        Connection connect = Database.Connect();
        Statement stmt = connect.createStatement();
        Statement stmt2 = connect.createStatement();
        ArrayList<String> list = new ArrayList<String>();


        ResultSet rs = stmt.executeQuery("SELECT timestamp, source_id, message FROM top20messages ORDER BY timestamp ASC;");
        ResultSet username;
        int i = 0;
        String user = null;
        while (rs.next()) {


            String timestamp = rs.getString("timestamp");


            //тут получаю имя юзра из другой таблицы по его айдишнику. знаю, что криво, но как не через while сделать не знаю. тут по-любому только одно значение возможно.
            username = stmt2.executeQuery("SELECT \"name\" FROM \"user\" WHERE id = '" + rs.getString("source_id") + "';");
            if (username.next()) {
                user = username.getString("name");
                }

            String message = rs.getString("message");

            list.add(timestamp + " " + user + " " + message + "\n");
            i++;
        }
        return list;
    }
    public static boolean checkUser(String login) throws IOException, ClassNotFoundException, SQLException {
        Connection connect = Database.Connect();
        Statement stmt = connect.createStatement();
        boolean success = false;

        ResultSet rs = stmt.executeQuery("SELECT \"name\" FROM \"user\" where name = '" + login + "'");

        if (rs.next())
        {
            return false;
        }
        else
        {
        return true;
        }
    }

}