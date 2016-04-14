import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Writer implements Runnable
{
    String _name;
    String _url;
    public Writer(String name, String url)
    {_name = name;
    _url=url;}

    public  void run()
    {
            try {
                String line = null;
                while (true)
                {
                    String url = _url+"/desktopwritemessage";
                    HttpClient client2 = HttpClientBuilder.create().build();
                    final BufferedReader _keyboard = new BufferedReader(new InputStreamReader(System.in));
                    line = _keyboard.readLine();
                    HttpPost request = new HttpPost(url + "?" + "name=" + _name);
                    List<NameValuePair> mess = new ArrayList<NameValuePair>();
                    mess.add(new BasicNameValuePair("message",line));
                    request.setEntity(new UrlEncodedFormEntity(mess));
                    client2.execute(request);
                }
            } catch (IOException x) {
                System.err.println(x.getMessage() + x.getStackTrace());
            }
    }
}