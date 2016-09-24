package handlers;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by Amir Shams on 9/24/2016.
 */
public class HandlerFactory {

    public static AbstractUpdateHandler create(Update update)
    {
        if(update.hasCallbackQuery())
            return new CallbackHandler(update);

        if(update.hasMessage())
            return new MessageHandler(update);

        return null;
    }
}
