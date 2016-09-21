package config;

import bot.Question;

/**
 * Created by Amir Shams on 9/19/2016.
 */
public class ConfigReader {

    private ConfigReader(){};

    public static String DB_URL = ConfigUtils.get("url");
    public static String DB_USERNAME = ConfigUtils.get("username");
    public static String DB_PASSWORD = ConfigUtils.get("password");
    public static String DB_DRIVER = ConfigUtils.get("jdbc_driver");

    public static String WELCOME = ConfigUtils.get("welcome");
    public static String THANK_YOU = ConfigUtils.get("thank_you");
    public static String SIGN_IN_ERROR = ConfigUtils.get("sign_in_first");

    public static int QUESTION_COUNT = Integer.valueOf(ConfigUtils.get("number_of_questions"));
    public static String CONSTANT_QUESTION_STATEMENT = ConfigUtils.get("constant_statement");

    public static String getQuestion(int index)
    {
        return ConfigUtils.get("question#" + String.valueOf(index));
    }
}
