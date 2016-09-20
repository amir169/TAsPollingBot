package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by Amir Shams on 9/19/2016.
 */

public class ConfigUtils {

    public static String getPropertyFromConfigFile(String propName)
            throws UnsupportedEncodingException, IOException {
        String prop;
        FileInputStream input = new FileInputStream("src/main/java/config/config.properties");
        try {
            Properties properties = new Properties();
            properties.load(input);
            prop = properties.getProperty(propName);
        }finally {
            input.close();
        }

        return prop;
    }

    public static String get(String key,String encoding){
        try{

            return new String(getPropertyFromConfigFile(key).getBytes("ISO-8859-1"),encoding);

        }catch(IOException ex){
            return null;
        }
    }

}

