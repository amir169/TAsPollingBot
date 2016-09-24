package handlers;

import database.QuestionGenerator;
import database.UserData;
import config.ConfigReader;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;


import java.util.ArrayList;


/**
 * Created by Amir Shams on 9/24/2016.
 */
public class MessageHandler extends AbstractUpdateHandler {
    @Override
    public ArrayList<BotApiMethod<Message>> handle() {

        ArrayList<BotApiMethod<Message>> result = new ArrayList<>();

        if(UserData.getInstance().searchUser(update.getMessage().getChatId())){

            String chatID = String.valueOf(update.getMessage().getChatId());
            String questionText = QuestionGenerator.getInstance().getNextQuestion(new Long(chatID));

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);

            if(questionText != null) {
                sendMessage.setText(questionText);

                sendMessage.setReplyMarkup(createInlineKeyboardMarkup());
                sendMessage.enableMarkdown(true);
            }
            else
                sendMessage.setText(ConfigReader.GAME_OVER);

            result.add(sendMessage);

        }
        else
        {
            String chatID = String.valueOf(update.getMessage().getChatId());
            String text = ConfigReader.WELCOME;

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);
            sendMessage.setText(text);

            result.add(sendMessage);

        }

        return result;
    }

    private Update update;
    public MessageHandler(Update update)
    {
        this.update = update;
    }
}
