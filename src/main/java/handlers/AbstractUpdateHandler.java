package handlers;

import config.ConfigReader;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Shams on 9/24/2016.
 */
public abstract class AbstractUpdateHandler {

    public abstract ArrayList<BotApiMethod<Message>> handle();

    protected InlineKeyboardMarkup createInlineKeyboardMarkup()
    {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> firstRow = createRow(ConfigReader.NUMBER_OF_BUTTONS);

        List<List<InlineKeyboardButton>> list = new ArrayList<>();

        list.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(list);

        return inlineKeyboardMarkup;
    }

    protected List<InlineKeyboardButton> createRow(int numberOfButtons) {

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        for(int i = 0;i<numberOfButtons;i++)
            buttons.add(new InlineKeyboardButton().setText(ConfigReader.getVotingOption(i + 1)).setCallbackData(String.valueOf(i + 1)));

        return buttons;
    }
}
