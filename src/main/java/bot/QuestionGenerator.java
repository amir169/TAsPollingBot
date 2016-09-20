package bot;

import config.ConfigReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amir Shams on 9/19/2016.
 */
public class QuestionGenerator {

    private static ArrayList<Question> questions = new ArrayList<>();

    private static QuestionGenerator instance = new QuestionGenerator();

    private QuestionGenerator(){

    }

    public static QuestionGenerator getInstance(){
        return instance;
    }

    public void addUser(/* arguments */){

    }

    public String getNextQuestion(String user)
    {
        return ConfigReader.QUESTION;
    }


}
