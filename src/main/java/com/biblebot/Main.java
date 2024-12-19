package com.biblebot;

import com.biblebot.constants.Replies;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {


    private final VerseRepository verseRepository;
    private final BookRepository bookRepository;

    public static final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        log.info("Started Bot...");

        bot.setUpdatesListener(updates ->{

            for(Update update : updates){

                if(update.message() != null){
                    handleMessage(update);
                }

            }


            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


    private void  handleMessage(Update update){
        try {

            if (Objects.equals(update.message().text(), "/start")) {
                TgBotWrapper.sendMessage(Replies.WELCOME_MESSAGE, update.message().chat().id() );
                return;
            }
            else if (Objects.equals(update.message().text(), "/all") || Objects.equals(update.message().text(), "Список всех книг")) {
                TgBotWrapper.sendMessage(Replies.ALL_BOOKS, update.message().chat().id() );
                return;
            }


            Request request = RequestParser.parseRequest(update.message().text());

            Book book = bookRepository.findByBookNameOrAltOrAbbr(request.getBookName(), request.getBookName(), request.getBookName()).orElseThrow(() ->{
                return new NoSuchElementException(Replies.NO_BOOKS_WITH_THAT_NAME);
            });

            if(request.getVerse() == null){

                List<Verse> verses = verseRepository.findAllByChapterAndBookId(request.getChapter(), book.getId()).orElseThrow(() -> {
                    return new NoSuchElementException(Replies.NO_SUCH_CHAPTER);
                });

                StringBuilder finalChapter = new StringBuilder();

                for(Verse verse : verses){
                    finalChapter.append(verse.getVerseText());
                }

                TgBotWrapper.sendMessage(finalChapter.toString(), update.message().chat().id());

            }else {

                Verse verse = verseRepository.findByBookIdAndChapterAndVerseNumber(book.getId(), request.getChapter(), request.getVerse()).orElseThrow(() -> {
                    return new NoSuchElementException(Replies.NO_SUCH_CHAPTER_OR_VERSE);
                });

                TgBotWrapper.sendMessage(verse.getVerseText(), update.message().chat().id());
            }
        }
        catch (IllegalArgumentException e){
            TgBotWrapper.sendMessage(Replies.INCORRECT_FORMAT,update.message().chat().id() );
        }
        catch (NoSuchElementException e){
            TgBotWrapper.sendMessage(e.getLocalizedMessage(), update.message().chat().id());
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
            TgBotWrapper.sendMessage(Replies.ERROR_OCCURED_TRY_AGAIN, update.message().chat().id());
        }
    }

}