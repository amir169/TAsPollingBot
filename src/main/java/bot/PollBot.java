package bot;

import database.UserData;
import handlers.HandlerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;


public class PollBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        ArrayList<BotApiMethod<Message>> actions = new ArrayList<>();

        if(update.hasMessage() || update.hasCallbackQuery())
            actions = HandlerFactory.create(update).handle();

        try {
            doActions(actions);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        System.out.println(update);
    }

    private void doActions(ArrayList<BotApiMethod<Message>> actions) throws TelegramApiException {

        for(BotApiMethod<Message> action : actions)
        {
            if(action instanceof SendMessage) {
                int lastMessage = sendMessage((SendMessage) action).getMessageId();
                SendMessage sendMessage = (SendMessage) action;
                UserData.getInstance().setLastMessage(new Long(sendMessage.getChatId()),lastMessage);
            }
            if(action instanceof EditMessageText)
                editMessageText((EditMessageText) action);

            if(action instanceof EditMessageReplyMarkup)
                editMessageReplyMarkup((EditMessageReplyMarkup) action);

        }

    }


    @Override
    public String getBotUsername() {
        return "TAsPollingBot";
    }

    @Override
    public String getBotToken() {
        return "242316295:AAFrTMKmujoCyMfU2tX0jA3rqROWYz35RxM";
    }
}
