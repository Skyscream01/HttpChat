import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by yauhen.bialiayeu on 15.04.2016.
 */
public class Logz
{
    final static public Logger log = Logger.getRootLogger();
    public static void Init()
    {
        String path = System.getProperty("user.dir")+"\\log4j.properties";
        PropertyConfigurator.configure(path);
    }
}
