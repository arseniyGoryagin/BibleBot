package com.biblebot;

import com.biblebot.request.domain.Request;
import com.biblebot.request.RequestParser;
import com.biblebot.domain.Book;
import com.biblebot.domain.BookRepository;
import com.biblebot.domain.Verse;
import com.biblebot.domain.VerseRepository;
import com.biblebot.tgbot.TgBotWrapper;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;


@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {


    private final VerseRepository verseRepository;
    private final BookRepository bookRepository;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        System.out.println("Started Bot...");

        TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

        TgBotWrapper tgBotWrapper = new TgBotWrapper(bot);

        bot.setUpdatesListener(updates ->{

            for(Update update : updates){

                try {

                    Request request = RequestParser.parseRequest(update.message().text());

                    Book book = bookRepository.findByBookNameOrAltOrAbbr(request.getBookName()).orElseThrow(() ->{
                        return new NoSuchElementException("Нету книги с таким названием(");
                    });

                    Verse verse = verseRepository.findByBookIdAndChapterAndVerseNumber(book.getId(), request.getChapter(), request.getVerse()).orElseThrow(() -> {
                        return new NoSuchElementException("Нету такой главы или параграфа");
                    });

                    tgBotWrapper.sendMessage(verse.getVerseText(), update.message().chat().id());
                }
                catch (NoSuchElementException e){
                    log.info(e.getLocalizedMessage());
                    tgBotWrapper.sendMessage("Ничего не найденно(, вводите данные в формате Бытие 1:1 ", update.message().chat().id());
                }
                catch (Exception e){
                    log.info(e.getLocalizedMessage());
                    tgBotWrapper.sendMessage("Произошла ошибка", update.message().chat().id());
                }
            }


            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}