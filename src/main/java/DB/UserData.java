package DB;

import bot.Main;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amir Shams on 9/20/2016.
 */
public class UserData {

    private static UserData instance = new UserData();

    private UserData(){}

    public static UserData getInstance()
    {
        return instance;
    }

    public boolean searchUser(long chatID) {

        String sql = "SELECT * FROM chatid_stid WHERE chatid = ?";

        ArrayList<Object> params = new ArrayList<>();
        params.add(chatID);

        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql,params);

        return !result.isEmpty();
    }

    public long getTaCourseCount(int studentID)
    {
        String sql = "SELECT count(*) AS count FROM st_ta WHERE stid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(studentID);

        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql, params);

        long returnResult = 0;

        if(result.size() > 0 && result.get(0).containsKey("count"))
            returnResult = (long)result.get(0).get("count");

        return returnResult;
    }

    public Map<String,Integer>  getNthTaCourse(int stid,int n)
    {
        String sql = "SELECT taid, coid FROM st_ta WHERE term = ? AND stid = ? " +
                "ORDER BY taid, coid limit ? offset ?";

        ArrayList<Object> params = new ArrayList<>();
        params.add("1");      //must be replaced by term number
        params.add(stid);
        params.add(1);
        params.add(n);

        ArrayList<Map<String,Object>> queryResult = DBConnection.executeQuery(sql, params);

        Map<String,Integer> result = new HashMap<>();
        result.put("taid",(int)queryResult.get(0).get("taid"));
        result.put("coid",(int)queryResult.get(0).get("coid"));

        return result;
    }

    public int getStudentIdByChatid(long chatid)
    {
        String sql = "SELECT stid FROM chatid_stid WHERE chatid = ?";
        ArrayList<Object> params = new ArrayList<>();

        params.add(chatid);
        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql, params);

        int studentID = -1;

        if(result.size() > 0 && result.get(0).containsKey("stid"))
            studentID = (int)result.get(0).get("stid");

        return studentID;
    }

    public Map<String,Integer> getState(int stid)
    {
        String sql = "SELECT q_number, ta_co_number FROM st_state WHERE stid = ? AND term = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(stid);
        params.add("1");

        ArrayList<Map<String,Object>> queryResult = DBConnection.executeQuery(sql, params);
        Map<String,Integer> result = new HashMap<>();
        result.put("questionNumber",(int)queryResult.get(0).get("q_number"));
        result.put("taCourseNumber",(int)queryResult.get(0).get("ta_co_number"));

        return result;
    }
}
