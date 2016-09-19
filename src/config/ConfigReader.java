package config;

/**
 * Created by Amir Shams on 9/19/2016.
 */
public class ConfigReader {

    private ConfigReader(){};

    public static String DB_URL = ConfigUtils.get("url");
    public static String DB_USERNAME = ConfigUtils.get("username");
    public static String DB_PASSWORD = ConfigUtils.get("password");
    public static String DB_DRIVER = ConfigUtils.get("jdbc_driver");
}
