package bot;

import DAO.UserDAO;
import config.ConfigReader;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
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

        //insert vote record

        //update user state

        String chatID = String.valueOf(update.getCallbackQuery().getFrom().getId());
        String questionText = QuestionGenerator.getInstance().getNextQuestion(chatID);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);

        if (questionText != null)
            sendMessage.setText(questionText);
        else {

            System.out.println("شبتساهقثلذاب");
            System.out.println(ConfigReader.THANK_YOU);
            sendMessage.setText(ConfigReader.THANK_YOU);
        }
        return sendMessage;

    }

    public SendMessage handleMessage() {

        if(UserDAO.getInstance().searchUser(update.getMessage().getChatId())){

            String chatID = String.valueOf(update.getMessage().getChatId());
            String questionText = QuestionGenerator.getInstance().getNextQuestion(chatID);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);
            sendMessage.setText(questionText);

            InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup();
            List<InlineKeyboardButton> firstRow = createRow(1);

            List<List<InlineKeyboardButton>> list = new ArrayList<>();

            list.add(firstRow);
            inlineKeyboardMarkup.setKeyboard(list);

            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            sendMessage.enableMarkdown(true);

            return sendMessage;
        }
        else
        {
            String chatID = String.valueOf(update.getMessage().getChatId());
            String text = "get lost";

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);
            sendMessage.setText(text);

            return sendMessage;
        }

    }

    private List<InlineKeyboardButton> createRow(int startingNumber) {

        ArrayList<String> emojies = new ArrayList<>();

        emojies.add(String.valueOf(startingNumber    ) + "  \uD83D\uDE21");
        emojies.add(String.valueOf(startingNumber + 1) + "  \uD83D\uDE31");
        emojies.add(String.valueOf(startingNumber + 2) + "  \uD83D\uDE10");
        emojies.add(String.valueOf(startingNumber + 3) + "  \uD83D\uDE0A");
        emojies.add(String.valueOf(startingNumber + 4) + "  \uD83D\uDE0D");

        List<InlineKeyboardButton> scores = new ArrayList<>();

        for(int i = 0;i<5;i++)
            scores.add(new InlineKeyboardButton().setText(emojies.get(i)).setCallbackData(String.valueOf(i + startingNumber)));

        return scores;
    }

}
