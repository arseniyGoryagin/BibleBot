package com.biblebot;

import com.biblebot.constants.Replies;
import com.biblebot.request.domain.Request;
import com.biblebot.request.RequestParser;
import com.biblebot.domain.Book;
import com.biblebot.domain.BookRepository;
import com.biblebot.domain.Verse;
import com.biblebot.domain.VerseRepository;
import com.biblebot.tgbot.TgBotWrapper;
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


    private void handleCallBackQuery(Update update){


        CallbackQuery query = update.callbackQuery();


        String []data = query.data().split(":");

        switch (data.length){

            case 1:

                long totalChapters  = verseRepository.countDistinctChapterByBookId(Long.parseLong(data[0]));

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                InlineKeyboardButton[] chaptersButtonsRow = new InlineKeyboardButton[4];

                int rowNum =0;
                for (int chapterNum= 0; chapterNum < totalChapters; chapterNum++){

                    chaptersButtonsRow[rowNum] = new InlineKeyboardButton("" +chapterNum).callbackData(data[0] + ":" + chapterNum);

                    rowNum++;

                    if (rowNum == 4) {
                        inlineKeyboardMarkup.addRow(chaptersButtonsRow);
                        chaptersButtonsRow = new InlineKeyboardButton[4];
                        chapterNum = 0;
                    }


                }

                bot.execute(new EditMessageText(query.inlineMessageId(), Replies.SELECT_CHAPTER)
                        .replyMarkup(inlineKeyboardMarkup)
                );

            case 2:

                long verseCount = verseRepository.countByBookIdAndChapter(Long.parseLong(data[0]), Long.parseLong(data[1]));

                InlineKeyboardMarkup inlineVerseKeyboardMarkup = new InlineKeyboardMarkup();
                InlineKeyboardButton[] versesButtonsRow = new InlineKeyboardButton[4];


                int rowVerseNum = 0;
                for (int verseNum= 0; verseNum < verseCount; verseNum++){

                    versesButtonsRow[rowVerseNum] = new InlineKeyboardButton("" +verseNum).callbackData(data[0]+ ":"  + data[1]+ ":"+ verseNum);

                    rowVerseNum++;

                    if (rowVerseNum == 4) {
                        inlineVerseKeyboardMarkup.addRow(versesButtonsRow);
                        versesButtonsRow = new InlineKeyboardButton[4];
                        rowVerseNum = 0;
                    }


                }

                inlineVerseKeyboardMarkup.addRow(new InlineKeyboardButton(Replies.ALL_VERSES).callbackData(data[0]+ ":"  + data[1]+ ":"+ "all"));


                bot.execute(new EditMessageText(query.inlineMessageId(), Replies.SELECT_VERSE)
                        .replyMarkup(inlineVerseKeyboardMarkup)
                );



            case 3:

                long bookId = Long.parseLong(data[0]);
                int chapter = Integer.parseInt(data[1]);
                String verseChoice = data[2];

                if(Objects.equals(verseChoice, "all")){

                    List<Verse> verses = verseRepository.findAllByChapterAndBookId(chapter, bookId).orElseThrow(() -> {
                        return new NoSuchElementException(Replies.NO_SUCH_CHAPTER);
                    });

                    StringBuilder finalChapter = new StringBuilder();

                    for(Verse verse : verses){
                        finalChapter.append(verse.getVerseText());
                    }

                    bot.execute(new EditMessageText(query.inlineMessageId(), finalChapter.toString())
                    );
                }


                Verse verse = verseRepository.findByBookIdAndChapterAndVerseNumber(bookId, chapter, Integer.parseInt(verseChoice))
                        .orElseThrow(() -> {return new NoSuchElementException(Replies.NO_SUCH_CHAPTER_OR_VERSE);});


                bot.execute(new EditMessageText(query.inlineMessageId(), verse.getVerseText())
                );



        }



    }


    private void  handleMessage(Update update){


            if (Objects.equals(update.message().text(), "/start")) {
                TgBotWrapper.sendMessage(Replies.WELCOME_MESSAGE, update.message().chat().id(), null, replyKeyboardMarkup);
                return;
            }
            else if (Objects.equals(update.message().text(), "/all") || Objects.equals(update.message().text(), "Список всех книг")) {

                List<Book> books = bookRepository.findAll();

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                InlineKeyboardButton[] bookButtonsRow = new InlineKeyboardButton[4];

                int rowNum = 0;
                for (int bookNum = 0; bookNum < books.size(); bookNum++){

                    bookButtonsRow[rowNum] = new InlineKeyboardButton(books.get(bookNum).getBookName()).callbackData(books.get(bookNum).getId().toString());

                    rowNum++;

                    if (rowNum == 4) {
                        inlineKeyboardMarkup.addRow(bookButtonsRow);
                        bookButtonsRow = new InlineKeyboardButton[4];
                        rowNum = 0;
                    }
                }

                if(rowNum > 0){
                    inlineKeyboardMarkup.addRow(Arrays.copyOf(bookButtonsRow, rowNum));
                }

                log.info(String.valueOf(inlineKeyboardMarkup));

                TgBotWrapper.sendMessage(Replies.SELECT_BOOK, update.message().chat().id(), inlineKeyboardMarkup, replyKeyboardMarkup);
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