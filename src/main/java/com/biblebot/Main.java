package com.biblebot;

import com.biblebot.domain.Verse;
import com.biblebot.domain.VerseRepository;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {


    private final VerseRepository verseRepository;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        System.out.println("Started Bot...");

        TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

        bot.setUpdatesListener(updates ->{

            for(Update update : updates){

                String []message = update.message().text().split(" ");
                String bookName = message[0];
                String chapter = message[1].split(":")[0];
                String verseNumber = message[1].split(":")[1];

                Verse verse = verseRepository.findByBookNameAndChapterAndVerseNumber(bookName, Integer.parseInt(chapter), Integer.parseInt(verseNumber));

                SendMessage sendMessage = new SendMessage(update.message().chat().id(), verse.getVerseText());

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