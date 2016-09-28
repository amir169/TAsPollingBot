package handlers;

import config.ConfigReader;
import database.DBConnection;
import database.UserData;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Amir Shams on 9/28/2016.
 */
public class CommentHandler extends AbstractUpdateHandler {


    ArrayList<BotApiMethod<Message>> result;
    @Override
    public ArrayList<BotApiMethod<Message>> handle() {

        result = new ArrayList<>();
        if(update.hasMessage())
        {
            long chatid = update.getMessage().getChatId();
            int studentID = UserData.getInstance().getStudentIdByChatid(chatid);
            String comment = update.getMessage().getText();
            if(comment.length() > ConfigReader.GET_MAX_COMMENT_SIZE)
            {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatid));
                sendMessage.setText(ConfigReader.TOO_LARGE_INPUT);
                result.add(sendMessage);
            }
            else
            {
                UserData.getInstance().setComment(studentID,comment,new Date());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatid));
                sendMessage.setText(ConfigReader.COMMENT_ADDED);
                result.add(sendMessage);

                sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatid));
                sendMessage.setText(ConfigReader.THANK_YOU);
                result.add(sendMessage);
            }
        }
        else if(update.hasCallbackQuery())
        {
            if(isNotAllowedToRespondThis(update)) {
                result.add(wrongAction(update));
                return result;
            }

            result.add(deleteKeyboard(update));

            int studentID = UserData.getInstance().getStudentIdByChatid
                    (update.getCallbackQuery().getFrom().getId());

            UserData.getInstance().setComment(studentID,"",new Date());

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()));
            sendMessage.setText(ConfigReader.THANK_YOU);
            result.add(sendMessage);
        }

        return result;
    }

    private Update update;
    public CommentHandler(Update update)
    {
        this.update = update;
    }

    private EditMessageText wrongAction(Update update)
    {
        return new EditMessageText()
                .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                .setText(ConfigReader.EXPIRED);
    }

    private boolean isNotAllowedToRespondThis(Update update) {

        long chatid = update.getCallbackQuery().getFrom().getId();
        long messageID = update.getCallbackQuery().getMessage().getMessageId();
        long lastMessage = UserData.getInstance().getLastMessage(chatid);

        return messageID != lastMessage;

    }
    private EditMessageReplyMarkup deleteKeyboard(Update update)
    {
        return new EditMessageReplyMarkup()
                .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>()));
    }

}
