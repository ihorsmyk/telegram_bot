package ua.javarush;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static ua.javarush.TelegramBotUtils.createMessage;
import static ua.javarush.TelegramBotUtils.getChatId;

public class MyFirstTelegramBot extends TelegramLongPollingBot {
    Dotenv dotenv = Dotenv.load();

    @Override
    public String getBotUsername() {
        String userName = dotenv.get("USERNAME");
        return userName;
    }

    @Override
    public String getBotToken() {
        String token = dotenv.get("TOKEN");
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = getChatId(update);
        SendMessage message = createMessage(chatId, "Hello World!");
        sendApiMethodAsync(message);
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}
