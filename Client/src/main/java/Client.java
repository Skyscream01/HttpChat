import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by yauhen.bialiayeu on 12.04.2016.
 */
public class Client
{
    public static String Login = null;
    public static String Url="http://192.168.13.202:7779";
    public static void main(String[] args) throws IOException {

        Start();
        Runnable read = new Reader(Url);
        Runnable write = new Writer(Login, Url);
        Thread t1 = new Thread(read,"T1");
        Thread t2 = new Thread(write,"T2");
        t1.start();
        t2.start();

    }
    public static void Start() throws IOException
    {
        String line;
        boolean flag=false;
        final BufferedReader _keyboard = new BufferedReader(new InputStreamReader(System.in));
        while (!flag) {
            System.out.println("Please choose your option:");
            System.out.println("1 - Registration");
            System.out.println("2 - Log in");
            System.out.println("3 - Log in as guest");
            line = _keyboard.readLine();
            if (line.equals("1")||line.equals("2")||line.equals("3"))
            {
                if (line.equals("1"))
                    Registration(Url);
                if (line.equals("2"))
                {
                    Login(Url);
                }
                if (line.equals("3"))
                    Login="guest";
            }
            else
            {
                System.out.println("Incorrect input!");
            }
            if(Login!=null)
                flag=true;
        }
    }
    public static void Registration(String _url) throws IOException
    {
        final BufferedReader _keyboard = new BufferedReader(new InputStreamReader(System.in));
        String login=null;
        String password=null;
        boolean flag=false;
        while(!flag) {
            System.out.println("REGISTRATION FORM");
            System.out.println("To return in main menu submit '/quit':");
            System.out.println("Enter your login:");
            login = _keyboard.readLine();
            if (login.equals("/quit")) {
               break;
            }
            System.out.println("Enter your password:");
            password = _keyboard.readLine();
            if (password.equals("/quit")) {
                break;
            }
            // Здесь контекст на создание юзера
            String url = _url+"/desktopregistration";
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url + "?" + "login=" + login+"&"+"password="+password);
            HttpResponse response = client.execute(request);
            String status=null;
            status = response.getStatusLine().toString();
            System.out.println(status);
            // HTTP/1.1 200 OK
            if (status.equals("HTTP/1.1 200 OK"))
            {
                System.out.println("User successfully added");
                break;
            }
            else
                System.out.println("User already exists");
        }
    }
    public static void Login(String _url) throws IOException
    {
        final BufferedReader _keyboard = new BufferedReader(new InputStreamReader(System.in));
        String login;
        String password;
        boolean flag=false;
        while(!flag) {
            System.out.println("LOG IN FORM");
            System.out.println("To return in main menu submit '/quit':");
            System.out.println("Enter your login:");
            login = _keyboard.readLine();
            if (login.equals("/quit")) {
                break;
            }
            System.out.println("Enter your password:");
            password = _keyboard.readLine();
            if (password.equals("/quit")) {
                break;
            }
            String url = _url+"/desktoplogin";
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url + "?" + "login=" + login+"&"+"password="+password);
            HttpResponse response = client.execute(request);
            String status=null;
            status = response.getStatusLine().toString();
            System.out.println(status);
            // HTTP/1.1 200 OK
            if (status.equals("HTTP/1.1 200 OK"))
            {
                System.out.println("Successfull log in");
                Login=login;
                break;
            }
            else
                System.out.println("Incorrect credentials");
        }
    }
}
