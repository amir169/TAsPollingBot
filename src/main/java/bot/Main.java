package bot;

import courseware.Course;
import courseware.CoursewareResponse;
import courseware.TA;
import courseware.User;
import database.CourseData;
import database.TaData;
import database.UserData;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

public class Main {

    public static void main(String[] args) throws TelegramApiException {

        runTheTestDataBaseFiller();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        telegramBotsApi.registerBot(new PollBot());
    }

    private static void runTheTestDataBaseFiller() {
        CoursewareResponse coursewareResponse = new CoursewareResponse();
        User user = coursewareResponse.coursewareResponseFunc();

        for(Course c : user.courses){
            CourseData.getInstance().addCourse(c);
//            System.out.println("course name = "+c.name);
//            System.out.println("course id ="+ c.id);

            for (TA t : c.TAs){
                TaData.getInstance().addTA(t);
                UserData.getInstance().addTaCourse(123,t.TAId,c.id);
//   System.out.println("ta name= "+ t.TAname);
//                System.out.println("ta id = "+ t.TAId);
//                System.out.println("+++++++++++++");
            }
//            System.out.println("----------------------------------------------------");
        }

    }
}
