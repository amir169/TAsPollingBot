package handlers;

import database.UserData;
import org.telegram.telegrambots.api.objects.Update;
import java.util.Map;

/**
 * Created by Amir Shams on 9/24/2016.
 */
public class HandlerFactory {

    public static AbstractUpdateHandler create(Update update)
    {

        if(canComment(update))
        {
            return new CommentHandler(update);
        }

        if(update.hasCallbackQuery()) {

            return new CallbackHandler(update);
        }
        if(update.hasMessage()) {

            return new MessageHandler(update);
        }

        return null;
    }

    private static boolean canComment(Update update) {

        long chatid = 0;
        if(update.hasCallbackQuery())
            chatid = (long) update.getCallbackQuery().getFrom().getId();
        if(update.hasMessage())
            chatid = update.getMessage().getChatId();

        int studentID = UserData.getInstance()
                .getStudentIdByChatid(chatid);


        if(UserData.getInstance().searchUser(chatid)) {
            Map<String,Integer> state = UserData.getInstance().getState(studentID);
            if (state.get("taCourseNumber") >= UserData.getInstance().getTaCourseCount(studentID))
                if (!UserData.getInstance().hasComment(studentID))
                    return true;
        }
        return false;
    }
}
