package database;

import config.ConfigReader;


import java.util.Map;

/**
 * Created by Amir Shams on 9/19/2016.
 */
public class QuestionGenerator {


    private static QuestionGenerator instance = new QuestionGenerator();

    private QuestionGenerator(){

    }

    public static QuestionGenerator getInstance(){
        return instance;
    }

    public String getNextQuestion(long chatid)
    {
        int studentID = UserData.getInstance().getStudentIdByChatid(chatid);
        Map<String,Integer> state = UserData.getInstance().getState(studentID);

        if(state.get("taCourseNumber") >= UserData.getInstance().getTaCourseCount(studentID))
            return null;

        Map<String,Integer> taCourseData =  UserData.getInstance().getNthTaCourse(studentID, state.get("taCourseNumber"));

        String taName = TaData.getInstance().getTaName(taCourseData.get("taid"));
        String courseName = CourseData.getInstance().getCourseName(taCourseData.get("coid"));

        return createQuestionText(taName,courseName,state.get("questionNumber"));
    }

    private String createQuestionText(String taName, String courseName, Integer questionNumber) {
       
        return "در مورد" + "  " +
                taName + "  در درس  " + courseName +" :"+ "\n\n" +ConfigReader.getQuestion(questionNumber + 1);

    }

}
