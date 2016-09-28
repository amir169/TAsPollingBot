package database;

import config.ConfigReader;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
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
        params.add(ConfigReader.CURRENT_TERM);
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
        params.add(ConfigReader.CURRENT_TERM);

        ArrayList<Map<String,Object>> queryResult = DBConnection.executeQuery(sql, params);
        Map<String,Integer> result = new HashMap<>();
        result.put("questionNumber",(int)queryResult.get(0).get("q_number"));
        result.put("taCourseNumber",(int)queryResult.get(0).get("ta_co_number"));

        return result;
    }

    public void setLastMessage(long chatid,long messageID)
    {
        String sql = "SELECT * FROM last_message WHERE chatid = ?";
        ArrayList<Object> params = new ArrayList<>();

        params.add(chatid);

        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql, params);
        if(result.isEmpty())
            insertLastMessage(chatid,messageID);
        else
            updateLastMessage(chatid,messageID);

    }

    private void updateLastMessage(long chatid, long messageID) {

        String sql = "UPDATE last_message SET messageid = ? WHERE chatid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(messageID);
        params.add(chatid);

        DBConnection.executeUpdate(sql,params);
    }

    private void insertLastMessage(long chatid, long messageID) {

        String sql = "INSERT INTO last_message VALUES (?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(chatid);
        params.add(messageID);

        DBConnection.executeUpdate(sql, params);
    }

    public long getLastMessage(long chatID)
    {

        String sql = "SELECT messageid FROM last_message WHERE chatid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(chatID);

        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql, params);

        return new Long((String)result.get(0).get("messageid"));
    }

    public boolean hasComment(int studentID) {
        String sql = "SELECT * FROM comment WHERE stid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(studentID);
        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql, params);


        return !result.isEmpty();

    }

    public void setComment(int studentID,String comment, Date date)
    {
        String sql = "INSERT INTO comment VALUES (?,?,?,?)";
        ArrayList<Object> params = new ArrayList<>();

        params.add(studentID);

        try {
            byte[] bytes = comment.getBytes();
            comment = new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        params.add(comment);
        params.add(ConfigReader.CURRENT_TERM);
        params.add(new java.sql.Date(date.getTime()));

        DBConnection.executeUpdate(sql,params);
    }

    public void addTaCourse(int stid,int taid,int coid)
    {
        String sql = "INSERT INTO st_ta VALUES (?,?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(stid);
        params.add(taid);
        params.add(coid);
        params.add(ConfigReader.CURRENT_TERM);

        DBConnection.executeUpdate(sql,params);
    }
}
