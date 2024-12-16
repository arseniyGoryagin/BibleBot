package com.biblebot;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {


        System.out.println("Started Bot...");

        TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

        bot.setUpdatesListener(updates ->{

            for(Update update : updates){

                SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Bible Bot");

                bot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
                    @Override
                    public void onResponse(SendMessage sendMessage, SendResponse sendResponse) {

                    }

                    @Override
                    public void onFailure(SendMessage sendMessage, IOException e) {

                    }
                });
            }


            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

}