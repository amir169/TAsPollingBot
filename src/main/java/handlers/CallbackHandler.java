package handlers;

import database.PollingData;
import database.QuestionGenerator;
import database.UserData;
import config.ConfigReader;
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

/**
 * Created by Amir Shams on 9/24/2016.
 */
public class CallbackHandler extends AbstractUpdateHandler {

    @Override
    public ArrayList<BotApiMethod<Message>> handle() {

        ArrayList<BotApiMethod<Message>> result = new ArrayList<>();

        result.add(deleteKeyboard(update));

        if(isNotAllowedToRespondThis(update)) {
            result.add(wrongAction(update));
            return result;
        }

        int pollingResult = PollingData.getInstance().insertVote(update.getCallbackQuery().getFrom().getId(),
                Integer.valueOf(ConfigReader.getButtonValue(Integer.valueOf(update.getCallbackQuery().getData()))),
                new Date());

        if(pollingResult == -1) {
            result.add(wrongAction(update));
            return result;
        }

        String chatID = String.valueOf(update.getCallbackQuery().getFrom().getId());
        String questionText = QuestionGenerator.getInstance().getNextQuestion(new Long(chatID));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);


        if (questionText != null){

            sendMessage.setText(questionText);
            sendMessage.setReplyMarkup(createInlineKeyboardMarkup());
            sendMessage.enableMarkdown(true);
            result.add(editMessage(update));
            result.add(sendMessage);
        }
        else {

            sendMessage.setText(ConfigReader.THANK_YOU);
            result.add(sendMessage);
        }

        return result;
    }

    private Update update;

    public CallbackHandler(Update update)
    {
        this.update = update;
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

    private EditMessageText wrongAction(Update update)
    {
        return new EditMessageText()
                .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                .setText(ConfigReader.EXPIRED);
    }

    private EditMessageText editMessage(Update update)
    {
        return new EditMessageText()
                .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                .setText(update.getCallbackQuery().getMessage().getText() + "\n" + ConfigReader.YOUR_ANSWER_IS + ConfigReader.getVotingOption(Integer.valueOf(update.getCallbackQuery().getData())));
    }
}
