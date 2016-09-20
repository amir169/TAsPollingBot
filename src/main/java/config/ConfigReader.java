package config;

import bot.Question;

/**
 * Created by Amir Shams on 9/19/2016.
 */
public class ConfigReader {

    private ConfigReader(){};

    public static String DB_URL = ConfigUtils.get("url","ISO-8859-1");
    public static String DB_USERNAME = ConfigUtils.get("username","ISO-8859-1");
    public static String DB_PASSWORD = ConfigUtils.get("password","ISO-8859-1");
    public static String DB_DRIVER = ConfigUtils.get("jdbc_driver","ISO-8859-1");

    public static String THANK_YOU = ConfigUtils.get("thank_you","UTF-8");
    public static String SIGN_IN_ERROR = ConfigUtils.get("sign_in_first","UTF-8");

    public static String QUESTION = ConfigUtils.get("question#2","UTF-8");
}
