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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;


import static com.biblebot.Main.bot;

@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class TgBotWrapper {


    private final BookRepository bookRepository;


    public static void sendMessage(String message, Object chatId, InlineKeyboardMarkup inlineKeyBoard, ReplyKeyboardMarkup replyKeyboardMarkup){

        SendMessage sendMessage = new SendMessage(chatId, message);

        if(inlineKeyBoard != null){
            sendMessage.replyMarkup(inlineKeyBoard);
        }else if(replyKeyboardMarkup != null){
            sendMessage.replyMarkup(replyKeyboardMarkup);
        }

        log.info("Sending message: {}", message);
        log.info("Inline keyboard: {}", inlineKeyBoard);
        log.info("Chat ID: {}", chatId);

        bot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage sendMessage, SendResponse sendResponse) {
                log.info("all good");
            }

            @Override
            public void onFailure(SendMessage sendMessage, IOException e) {
                log.error(e.getLocalizedMessage());
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
