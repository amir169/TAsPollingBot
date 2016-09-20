package bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
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

            try {
                editMessageReplyMarkup(deleteKeyboard(update));

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            if(current != null){
                try {
                    sendMessage(current);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        else {

            current = new UpdateHandler(update).handleMessage();

            try {
                sendMessage(current);
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

    @Override
    public String getBotUsername() {
        return "TAsPollingBot";
    }

    @Override
    public String getBotToken() {
        return "242316295:AAFrTMKmujoCyMfU2tX0jA3rqROWYz35RxM";
    }
}
