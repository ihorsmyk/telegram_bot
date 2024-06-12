package ua.javarush;

import io.github.cdimascio.dotenv.Dotenv;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

import static ua.javarush.TelegramBotContent.*;
import static ua.javarush.TelegramBotUtils.*;


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

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            addGlories(chatId, 0);
            SendMessage message = createMessage(chatId, STEP_1_TEXT, Map.of(
                    "Злам холодильника", "step_1_btn"
            ));
            sendApiMethodAsync(message);
        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("step_1_btn") && getGlories(chatId) == 0) {
                addGlories(chatId, 20);
                SendMessage message = createMessage(chatId, STEP_2_TEXT, Map.of(
                        "Взяти сосиску! +20 слави", "step_2_btn",
                        "Взяти рибку! +20 слави", "step_2_btn",
                        "Скинути банку з огірками! +20 слави", "step_2_btn"));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_2_btn") && getGlories(chatId) == 20) {
                addGlories(chatId, 20);
                SendMessage message = createMessage(chatId, STEP_3_TEXT, Map.of(
                        "Злам робота пилососа", "step_3_btn"
                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_3_btn") && getGlories(chatId) == 40) {
                addGlories(chatId, 30);
                SendMessage message = createMessage(chatId, STEP_4_TEXT, Map.of(
                        "Відправити робопилосос за їжею! +30 слави", "step_4_btn",
                        "Проїхатися на робопилососі! +30 слави", "step_4_btn",
                        "Тікати від робопилососа! +30 слави", "step_4_btn"

                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_4_btn") && getGlories(chatId) == 70) {
                addGlories(chatId, 30);
                SendMessage message = createMessage(chatId, STEP_5_TEXT, Map.of(
                        "Одягнути та включити GoPro!", "step_5_btn"
                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_5_btn") && getGlories(chatId) == 100) {
                addGlories(chatId, 40);
                SendMessage message = createMessage(chatId, STEP_6_TEXT, Map.of(
                        "Бігати дахами, знімати на GoPro! +40 слави", "step_6_btn",
                        "З GoPro нападати на інших котів із засідки! +40 слави", "step_6_btn",
                        "З GoPro нападати на собак із засідки! +40 слави", "step_6_btn"

                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_6_btn") && getGlories(chatId) == 140) {
                addGlories(chatId, 90);
                SendMessage message = createMessage(chatId, STEP_7_TEXT, Map.of(
                        "Злам пароля", "step_7_btn"
                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_7_btn") && getGlories(chatId) == 230) {
                addGlories(chatId, 170);
                SendMessage message = createMessage(chatId, STEP_8_TEXT, Map.of(
                        "Вийти на подвір'я", "step_8_btn"
                ));
                sendApiMethodAsync(message);
            } else if (update.getCallbackQuery().getData().equals("step_8_btn") && getGlories(chatId) == 400) {
                SendMessage message = createMessage(chatId, FINAL_TEXT);
                sendApiMethodAsync(message);
            }
        } else {
            SendMessage message = createMessage(chatId, "_Meow_=^^=");
            sendApiMethodAsync(message);
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}
