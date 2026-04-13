package com.example33.iit_lab8;

import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private static final FluentLogger LOG = FluentLogger.getLogger("app", "127.0.0.1", 24224);
    @Override
    public String getBotUsername() {
        return "football_match_org_bot";
    }
    @Override
    public String getBotToken() {
        return "8775265017:AAEPjgG3d6uMw7SL2aFcH2FsNwEhVFZyVIQ";
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("Прийшло: " + update.getMessage().getText());
            // дані користувача
            int userId = update.getMessage().getFrom().getId().intValue();
            String username = update.getMessage().getFrom().getUserName();
            String text = update.getMessage().getText();
            // Відправляємо лог у Fluentd
            Map<String, Object> data = new HashMap<>();
            data.put("UserId", userId);
            data.put("UserName", username);
            data.put("Message", text);
            LOG.log("UserData", data);
            // Відповідаємо користувачу
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("Лог записано! User: " + username);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}