package bot;

import DB.PollingData;
import DB.QuestionGenerator;
import DB.UserData;
import config.ConfigReader;
import config.ConfigUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amir Shams on 9/19/2016.
 */
public class UpdateHandler {

    Update update;

    public UpdateHandler(Update update) {
        this.update = update;
    }

    public SendMessage handleResponse() {

        if(isNotAllowedToRespondThis(update))
            return null;

        int pollingResult = PollingData.getInstance().insertVote(update.getCallbackQuery().getFrom().getId(),
                Integer.valueOf(update.getCallbackQuery().getData()),
                new Date());

        if(pollingResult == -1)
            return null;

        String chatID = String.valueOf(update.getCallbackQuery().getFrom().getId());
        String questionText = QuestionGenerator.getInstance().getNextQuestion(new Long(chatID));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);


        if (questionText != null){

            sendMessage.setText(questionText);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> firstRow = createRow(ConfigReader.NUMBER_OF_BUTTONS);

            List<List<InlineKeyboardButton>> list = new ArrayList<>();

            list.add(firstRow);
            inlineKeyboardMarkup.setKeyboard(list);

            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            sendMessage.enableMarkdown(true);
        }
        else {

            sendMessage.setText(ConfigReader.THANK_YOU);
        }


        return sendMessage;

    }

    private boolean isNotAllowedToRespondThis(Update update) {

        long chatid = update.getCallbackQuery().getFrom().getId();
        long messageID = update.getCallbackQuery().getMessage().getMessageId();
        long lastMessage = UserData.getInstance().getLastMessage(chatid);

        if(messageID == lastMessage)
            return false;

        return true;
    }

    public SendMessage handleMessage() {

        if(UserData.getInstance().searchUser(update.getMessage().getChatId())){

            String chatID = String.valueOf(update.getMessage().getChatId());
            String questionText = QuestionGenerator.getInstance().getNextQuestion(new Long(chatID));

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);

            if(questionText != null) {
                sendMessage.setText(questionText);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<InlineKeyboardButton> firstRow = createRow(ConfigReader.NUMBER_OF_BUTTONS);

                List<List<InlineKeyboardButton>> list = new ArrayList<>();

                list.add(firstRow);
                inlineKeyboardMarkup.setKeyboard(list);

                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                sendMessage.enableMarkdown(true);
            }
            else
                sendMessage.setText(ConfigReader.GAME_OVER);

            return sendMessage;
        }
        else
        {
            String chatID = String.valueOf(update.getMessage().getChatId());
            String text = ConfigReader.WELCOME;

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);
            sendMessage.setText(text);

            return sendMessage;
        }

    }

    private List<InlineKeyboardButton> createRow(int numberOfButtons) {

//        emojies.add(String.valueOf(startingNumber    ) + "  \uD83D\uDE21");
//        emojies.add(String.valueOf(startingNumber + 1) + "  \uD83D\uDE31");
//        emojies.add(String.valueOf(startingNumber + 2) + "  \uD83D\uDE10");
//        emojies.add(String.valueOf(startingNumber + 3) + "  \uD83D\uDE0A");
//        emojies.add(String.valueOf(startingNumber + 4) + "  \uD83D\uDE0D");

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        for(int i = 0;i<numberOfButtons;i++)
            buttons.add(new InlineKeyboardButton().setText(ConfigReader.getVotingOption(i + 1)).setCallbackData(String.valueOf(i + 1)));

        return buttons;
    }

}
