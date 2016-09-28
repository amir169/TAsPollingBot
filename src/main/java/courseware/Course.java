package courseware;

import java.util.ArrayList;

/**
 * Created by ABM on 8/25/2016.
 */
public class Course {
    public String name;
    public Integer id;
    public ArrayList<TA> TAs;

    public Course(String name, Integer id) {
        this.name = name;
        this.id = id;
        TAs = new ArrayList<>();
    }
}
