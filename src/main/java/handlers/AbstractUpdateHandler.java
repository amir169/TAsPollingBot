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

        ArrayList<String> buttons = new ArrayList<>();
        for(int i=0;i<ConfigReader.NUMBER_OF_BUTTONS;i++)
            buttons.add(ConfigReader.getButtonValue(i + 1));

        List<List<InlineKeyboardButton>> keyboard = createKeyboard(buttons);

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    protected InlineKeyboardMarkup createYesNoKeyboardMarkup()
    {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        ArrayList<String> buttons = new ArrayList<>();

        buttons.add(ConfigReader.ANSWER_YES);
        buttons.add(ConfigReader.ANSWER_NO);

        List<List<InlineKeyboardButton>> keyboard = createKeyboard(buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    protected List<List<InlineKeyboardButton>> createKeyboard(ArrayList<String> buttonNames) {

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> currentRow = new ArrayList<>();

        for(int i = 0;i<buttonNames.size();i++) {

            if(currentRow.size() < ConfigReader.MAX_BUTTONS_IN_ROW)
                currentRow.add(new InlineKeyboardButton().setText(buttonNames.get(i)).setCallbackData(String.valueOf(i + 1)));
            else
            {
                buttons.add(currentRow);
                currentRow = new ArrayList<>();
                currentRow.add(new InlineKeyboardButton().setText(buttonNames.get(i + 1)).setCallbackData(String.valueOf(i + 1)));
            }
        }
        buttons.add(currentRow);

        return buttons;
    }
}
