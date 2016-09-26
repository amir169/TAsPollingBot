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
        List<List<InlineKeyboardButton>> keyboard = createKeyboard(ConfigReader.NUMBER_OF_BUTTONS);

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    protected List<List<InlineKeyboardButton>> createKeyboard(int numberOfButtons) {

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> currentRow = new ArrayList<>();

        for(int i = 0;i<numberOfButtons;i++) {

            if(currentRow.size() < ConfigReader.MAX_BUTTONS_IN_ROW)
                currentRow.add(new InlineKeyboardButton().setText(ConfigReader.getVotingOption(i + 1)).setCallbackData(String.valueOf(i + 1)));
            else
            {
                buttons.add(currentRow);
                currentRow = new ArrayList<>();
                currentRow.add(new InlineKeyboardButton().setText(ConfigReader.getVotingOption(i + 1)).setCallbackData(String.valueOf(i + 1)));
            }
        }
        buttons.add(currentRow);

        return buttons;
    }
}
