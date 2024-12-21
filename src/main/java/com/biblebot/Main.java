package com.biblebot;

import com.biblebot.callbackdata.CallBackData;
import com.biblebot.constants.Replies;
import com.biblebot.domain.*;
import com.biblebot.page.PageService;
import com.biblebot.request.domain.Request;
import com.biblebot.request.RequestParser;
import com.biblebot.page.Page;
import com.biblebot.tgbot.TgBotWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.EditMessageText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;


@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {


    private final VerseRepository verseRepository;
    private final BookRepository bookRepository;
    private final PageService pageService;

    public static final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));


    public static final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
            new KeyboardButton[]{
                    new KeyboardButton("Список всех книг")
            }
    ).resizeKeyboard(true);


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        log.info("Started Bot...");

        bot.setUpdatesListener(updates ->{

            for(Update update : updates){


                try{

                if(update.message() != null){
                    handleMessage(update);
                }else if(update.callbackQuery() != null){
                    handleCallBackQuery(update);
                }


                }
                catch (Exception e){
                    log.error(e.getLocalizedMessage());
                    e.printStackTrace();
                    if (update.message() != null) {
                        TgBotWrapper.sendMessage(e.getLocalizedMessage(), update.message().chat().id(), null, replyKeyboardMarkup);
                    } else if (update.callbackQuery() != null) {
                        bot.execute(new AnswerCallbackQuery(update.callbackQuery().id()).text(e.getLocalizedMessage()));
                    };
                }
            }


            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }






    private void handleCallBackQuery(Update update) throws JsonProcessingException {

        CallbackQuery query = update.callbackQuery();
        CallBackData callBackData = CallBackData.fromJson(query.data());

        Page page = switch (callBackData.getPage()){
            case BOOK -> {
                yield pageService.renderBookPage();
            }
            case CHAPTER -> {
                yield  pageService.renderChaptersPage(callBackData.getData());
            }
            case VERSE -> {
                yield  pageService.renderVersePage(callBackData.getData());
            }
            case TEXT-> {
                yield pageService.renderTextPage(callBackData.getData());
            }
        };

        bot.execute(new EditMessageText(query.message().chat().id(), query.message().messageId() , page.getText())
                .replyMarkup(page.getInlineKeyboardMarkup())
        );


    }


    private void  handleMessage(Update update) throws JsonProcessingException {

            if (Objects.equals(update.message().text(), "/start") || Objects.equals(update.message().text(), "/all") || Objects.equals(update.message().text(), "Список всех книг")) {
                Page bookPage = pageService.renderBookPage();
                TgBotWrapper.sendMessage(bookPage.getText(), update.message().chat().id(), bookPage.getInlineKeyboardMarkup(), null);
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

                TgBotWrapper.sendMessage(finalChapter.toString(), update.message().chat().id(), null, replyKeyboardMarkup);

            }else {

                Verse verse = verseRepository.findByBookIdAndChapterAndVerseNumber(book.getId(), request.getChapter(), request.getVerse()).orElseThrow(() -> {
                    return new NoSuchElementException(Replies.NO_SUCH_CHAPTER_OR_VERSE);
                });

                TgBotWrapper.sendMessage(verse.getVerseText(), update.message().chat().id(), null, replyKeyboardMarkup);
            }
    }

}