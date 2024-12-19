package com.biblebot.tgbot;


import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

import static com.biblebot.Main.bot;

@Data

public class TgBotWrapper {


    public static final Keyboard keyboard = new ReplyKeyboardMarkup(
            new KeyboardButton[]{
                    new KeyboardButton("Список всех книг")
            }
    ).resizeKeyboard(true);


    public static void sendMessage(String message, Object chatId){

        SendMessage sendMessage = new SendMessage(chatId, message).replyMarkup(keyboard);

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
