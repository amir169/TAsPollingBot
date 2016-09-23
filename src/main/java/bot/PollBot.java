package bot;

import DB.UserData;
import config.ConfigReader;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PollBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage current = null;

        if(update.hasCallbackQuery()) {
            current = new UpdateHandler(update).handleResponse();

            if(current != null){

                try {

                    editMessageReplyMarkup(deleteKeyboard(update));
                    editMessageText(editMessage(update));
                    long messageID = sendMessage(current).getMessageId();
                    UserData.getInstance().setLastMessage(new Long(current.getChatId()),messageID);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {

                    editMessageReplyMarkup((deleteKeyboard(update)));
                    editMessageText(wrongAction(update));

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(update.hasMessage()){

            current = new UpdateHandler(update).handleMessage();

            try {

                long messageID = sendMessage(current).getMessageId();
                UserData.getInstance().setLastMessage(new Long(current.getChatId()),messageID);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        System.out.println(update);

    }

    private EditMessageReplyMarkup deleteKeyboard(Update update)
    {
        return new EditMessageReplyMarkup()
                    .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                            .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>()));
    }

    private EditMessageText editMessage(Update update)
    {
        return new EditMessageText()
                    .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                            .setText(update.getCallbackQuery().getMessage().getText() + "\n" + ConfigReader.YOUR_ANSWER_IS + update.getCallbackQuery().getData());
    }
    private EditMessageText wrongAction(Update update)
    {
        return new EditMessageText()
                    .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                            .setText(ConfigReader.EXPIRED);

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
