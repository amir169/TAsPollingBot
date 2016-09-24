package database;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Amir Shams on 9/21/2016.
 */
public class CourseData {

    private static CourseData instance;
    private CourseData(){}

    public static CourseData getInstance()
    {
        if(instance == null)
            instance = new CourseData();
        return instance;
    }

    public String getCourseName(int id)
    {
        String sql = "SELECT coName FROM course WHERE coid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);

        ArrayList<Map<String,Object>> queryResult = DBConnection.executeQuery(sql, params);

        return (String) queryResult.get(0).get("coName");
    }
}
