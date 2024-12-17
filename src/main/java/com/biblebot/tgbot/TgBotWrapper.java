package com.biblebot.tgbot;


import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

@Data
@AllArgsConstructor
public class TgBotWrapper {

    private final TelegramBot bot;


    public void sendMessage(String message, Object chatId){
        SendMessage sendMessage = new SendMessage(chatId, message);

        bot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage sendMessage, SendResponse sendResponse) {

            }

            @Override
            public void onFailure(SendMessage sendMessage, IOException e) {

            }
        });
    }

}
