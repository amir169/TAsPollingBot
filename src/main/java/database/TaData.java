package database;

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
}
