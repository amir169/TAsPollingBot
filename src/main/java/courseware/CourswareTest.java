package courseware;

/**
 * Created by ABM on 9/28/2016.
 */
public class CourswareTest {
    public static void main(String[] args){
        CoursewareResponse coursewareResponse = new CoursewareResponse();
        User user = coursewareResponse.coursewareResponseFunc();
        for(Course c : user.courses){
            System.out.println("course name = "+c.name);
            System.out.println("course id ="+ c.id);

            for (TA t : c.TAs){
                System.out.println("ta name= "+ t.TAname);
                System.out.println("ta id = "+ t.TAId);
                System.out.println("+++++++++++++");
            }
            System.out.println("----------------------------------------------------");
        }
    }


}
