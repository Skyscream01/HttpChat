import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;

public class Reader implements Runnable
{
    String _url=null;
    public Reader(String url) {
        _url=url;
    }

    public void run()
    {
        try {
            ArrayList<String> messages=new ArrayList<String>();
            while (true)
            {
                String urlRead = _url+"/desktopgetmessages";
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet requestRead=new HttpGet(urlRead);
                HttpResponse responseRead = client.execute(requestRead);
                BufferedReader rd = new BufferedReader(new InputStreamReader(responseRead.getEntity().getContent()));
                String line0 = "";
                while ((line0 = rd.readLine()) != null) {
                    boolean flag = false;
                    if (messages.size() == 0) {
                        messages.add(line0);
                        System.out.println(line0);
                    }
                 else {
                       for (String a : messages) {
                            if (a.equals(line0)) {
                                flag = true;
                           }
                        }
                    }
                   if (flag == false) {
                       messages.add(line0);
                       System.out.println(line0.trim());
                       }
                   if (messages.size() > 40)
                    {
                        messages.remove(0);
                   }
                }
                    try {
                        Thread.currentThread().sleep(2000);
                    }
                    catch (InterruptedException ex)
                    {
                        System.out.println(ex.getMessage()+ex.getStackTrace());
                    }
                }

        }
        catch (IOException x)
        {
            System.err.println(x.getMessage()+x.getStackTrace());
        }
    }
}
