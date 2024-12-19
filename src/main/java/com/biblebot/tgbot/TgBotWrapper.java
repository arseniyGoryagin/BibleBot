package com.biblebot.tgbot;


import com.biblebot.domain.BookRepository;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;


import static com.biblebot.Main.bot;

@Data
@Service
@RequiredArgsConstructor
public class TgBotWrapper {


    private final BookRepository bookRepository;


    public static void sendMessage(String message, Object chatId, InlineKeyboardMarkup inlineKeyBoard, ReplyKeyboardMarkup replyKeyboardMarkup){

        SendMessage sendMessage = new SendMessage(chatId, message);

        if(inlineKeyBoard != null){
            sendMessage.replyMarkup(inlineKeyBoard);
        }

        if(replyKeyboardMarkup != null){
            sendMessage.replyMarkup(replyKeyboardMarkup);
        }

        bot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage sendMessage, SendResponse sendResponse) {

            }

            @Override
            public void onFailure(SendMessage sendMessage, IOException e) {

            }
        });
    }



    public static void editMessage(String message, String inlineMessageId, InlineKeyboardMarkup inlineKeyBoard){

        EditMessageText editMessageText = new EditMessageText(inlineMessageId, message);

        if(inlineKeyBoard != null){
            editMessageText.replyMarkup(inlineKeyBoard);
        }

        bot.execute(editMessageText);
    }

}
