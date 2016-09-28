package database;

import courseware.Course;

import java.io.UnsupportedEncodingException;
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

    public boolean searchCourse(int courseID)
    {
        String sql = "SELECT * FROM course WHERE coid = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(courseID);

        ArrayList<Map<String,Object>> result = DBConnection.executeQuery(sql,params);

        return !result.isEmpty();
    }
    public void addCourse(Course course)
    {
        String sql = "INSERT INTO course VALUES(?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(course.id);

        String name = course.name;
        try {
            byte[] bytes = name.getBytes();
            name = new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.add(name);
        System.out.println(name);

        if(!searchCourse(course.id))
            DBConnection.executeUpdate(sql,params);
    }
}
