package courseware;

import java.util.ArrayList;

/**
 * Created by ABM on 8/25/2016.
 */
public class Course {
    String name;
    Integer id;
    ArrayList<TA> TAs;

    public Course(String name, Integer id) {
        this.name = name;
        this.id = id;
        TAs = new ArrayList<>();
    }
}
