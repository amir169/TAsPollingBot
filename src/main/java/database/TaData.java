package database;

import courseware.TA;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Amir Shams on 9/21/2016.
 */
public class TaData {

    private static TaData instance;
    private TaData(){}

    public static TaData getInstance()
    {
        if(instance == null)
            instance = new TaData();
        return instance;
    }

    public String getTaName(int id)
    {
        String sql = "SELECT taName FROM ta WHERE taid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);

        ArrayList<Map<String,Object>> queryResult = DBConnection.executeQuery(sql, params);

        return (String) queryResult.get(0).get("taName");
    }

    public boolean searchTA(int taID)
    {
        String sql = "SELECT * FROM ta WHERE taid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(taID);

        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql,params);

        return !result.isEmpty();
    }
    public void addTA(TA ta)
    {
        String sql = "INSERT INTO ta VALUES(?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(ta.TAId);
        String name = ta.TAname;
        try {
            byte[] bytes = name.getBytes();
            name = new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.add(name);

        if(!searchTA(ta.TAId))
            DBConnection.executeUpdate(sql,params);
    }
}
