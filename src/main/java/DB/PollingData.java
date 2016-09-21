package DB;

import config.ConfigReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Amir Shams on 9/21/2016.
 */
public class PollingData {

    private static PollingData instance;
    private PollingData(){}

    public static PollingData getInstance()
    {
        if(instance == null)
            instance = new PollingData();
        return instance;
    }

    public int insertVote(long chatid, int score, Date date)
    {

        int studentID = UserData.getInstance().getStudentIdByChatid(chatid);
        Map<String,Integer> state = UserData.getInstance().getState(studentID);
        int taCourseNumber = state.get("taCourseNumber");
        int questionNumber = state.get("questionNumber");
        Map<String,Integer> taCourseData = UserData.getInstance().getNthTaCourse(studentID,taCourseNumber);

        String sql = "INSERT INTO poll VALUES (?,?,?,?,?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(studentID);
        params.add(taCourseData.get("taid"));
        params.add(taCourseData.get("coid"));
        params.add(ConfigReader.CURRENT_TERM);
        params.add(questionNumber);
        params.add(score);
        params.add(new java.sql.Date(date.getTime()));

        int result = DBConnection.executeUpdate(sql,params);
        System.out.println(result);
        if(result > 0)
            nextState(studentID,taCourseNumber,questionNumber);

        return result;
    }

    private void nextState(int studentID,int taCourseNumber,int questionNumber) {

        if(questionNumber == ConfigReader.QUESTION_COUNT - 1) {
            questionNumber = 0;
            taCourseNumber++;
        }
        else if(questionNumber < ConfigReader.QUESTION_COUNT) {
            questionNumber++;
        }

        System.out.println(questionNumber);
        String sql = "UPDATE st_state SET q_number = ? ,ta_co_number = ? WHERE stid = ? AND term = ?";
        ArrayList<Object> params = new ArrayList<>();

        params.add(questionNumber);
        params.add(taCourseNumber);
        params.add(studentID);
        params.add(ConfigReader.CURRENT_TERM);

        DBConnection.executeUpdate(sql,params);
    }
}
