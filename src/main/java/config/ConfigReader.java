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

    public static String WELCOME = ConfigUtils.get("welcome");
    public static String THANK_YOU = ConfigUtils.get("thank_you");
    public static String SIGN_IN_ERROR = ConfigUtils.get("sign_in_first");

    public static int QUESTION_COUNT = Integer.valueOf(ConfigUtils.get("number_of_questions"));
    public static String CONSTANT_QUESTION_STATEMENT = ConfigUtils.get("constant_statement");
    public static String CURRENT_TERM = ConfigUtils.get("current_term");
    public static String DUPLICATE_ANSWER_ERROR = ConfigUtils.get("duplicate_answer_error");
    public static String INVALID_INPUT = ConfigUtils.get("invalid_input");
    public static String YOUR_ANSWER_IS = ConfigUtils.get("your_answer_is");
    public static String GAME_OVER = ConfigUtils.get("game_over");
    public static String EXPIRED = ConfigUtils.get("expired");
    public static int NUMBER_OF_BUTTONS = Integer.valueOf(ConfigUtils.get("number_of_possible_answers"));
    public static int MAX_BUTTONS_IN_ROW = Integer.valueOf(ConfigUtils.get("max_buttons_in_row"));
    public static String ANSWER_YES = ConfigUtils.get("yes");
    public static String ANSWER_NO = ConfigUtils.get("no");
    public static int GET_MAX_COMMENT_SIZE = Integer.valueOf(ConfigUtils.get("max_comment_size"));
    public static String MESSAGE_SKIP = ConfigUtils.get("skip");
    public static final String COMMENT_ADDED = ConfigUtils.get("comment_added");
    public static final String TOO_LARGE_INPUT = ConfigUtils.get("too_large_input");
    public static final String SURVEY = ConfigUtils.get("survey");

    public static String getVotingOption(int index){return ConfigUtils.get("option#" + String.valueOf(index));}

    public static String getQuestion(int index)
    {
        return ConfigUtils.get("question#" + String.valueOf(index));
    }

    public static String getButtonValue(int index) {
        return ConfigUtils.get("value#" + String.valueOf(index));
    }




}
