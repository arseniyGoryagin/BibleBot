package com.biblebot.page;

import com.biblebot.callbackdata.CallBackData;
import com.biblebot.callbackdata.Pages;
import com.biblebot.constants.Replies;
import com.biblebot.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageService {


    private final BookRepository bookRepository;
    private final VerseRepository verseRepository;


    public Page renderTextPage(String data) throws JsonProcessingException {

        String[] dataSplit = data.split(":");

        long bookId = Long.parseLong(dataSplit[0]);
        int chapter = Integer.parseInt(dataSplit[1]);
        String verseChoice = dataSplit[2];

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        CallBackData callBackDataBack = new CallBackData(Pages.VERSE,dataSplit[0] + dataSplit[1]);
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(Replies.GO_BACK).callbackData(CallBackData.toJson(callBackDataBack)));

        if(Objects.equals(verseChoice, "all")){

            List<Verse> verses = verseRepository.findAllByChapterAndBookId(chapter, bookId).orElseThrow(() -> {
                return new NoSuchElementException(Replies.NO_SUCH_CHAPTER);
            });

            StringBuilder finalChapter = new StringBuilder();

            for(Verse verse : verses){
                finalChapter.append(verse.getVerseText());
            }

            return new Page(finalChapter.toString(), inlineKeyboardMarkup);

        }


        Verse verse = verseRepository.findByBookIdAndChapterAndVerseNumber(bookId, chapter, Integer.parseInt(verseChoice))
                .orElseThrow(() -> {return new NoSuchElementException(Replies.NO_SUCH_CHAPTER_OR_VERSE);});

        return new Page(verse.getVerseText(), inlineKeyboardMarkup);

    }



    public Page renderChaptersPage(String data) throws JsonProcessingException {


        String[] dataSplit = data.split(":");

        long totalChapters  = verseRepository.countDistinctChapterByBookId(Long.parseLong(dataSplit[0]));

        log.info("Total chapter " + totalChapters);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton[] chaptersButtonsRow = new InlineKeyboardButton[4];

        int rowNum =0;
        for (int chapterNum= 1; chapterNum != totalChapters; chapterNum++){


            CallBackData callBackData = new CallBackData(Pages.VERSE, data + ":" + chapterNum);

            chaptersButtonsRow[rowNum] = new InlineKeyboardButton("" +chapterNum).callbackData(CallBackData.toJson(callBackData));

            rowNum++;

            if (rowNum == 4) {
                inlineKeyboardMarkup.addRow(chaptersButtonsRow);
                chaptersButtonsRow = new InlineKeyboardButton[4];
                rowNum = 0;
            }


        }

        log.info(String.valueOf(inlineKeyboardMarkup));

        CallBackData callBackDataBack = new CallBackData(Pages.BOOK,null);
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(Replies.GO_BACK).callbackData(CallBackData.toJson(callBackDataBack)));

        return new Page(Replies.SELECT_CHAPTER, inlineKeyboardMarkup);


    }


    public Page renderVersePage(String data) throws JsonProcessingException {

        String[] dataSplit = data.split(":");

        long verseCount = verseRepository.countByBookIdAndChapter(Long.parseLong(dataSplit[0]), Integer.parseInt(dataSplit[1]));

        InlineKeyboardMarkup inlineVerseKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton[] versesButtonsRow = new InlineKeyboardButton[4];


        int rowVerseNum = 0;
        for (int verseNum= 1; verseNum != verseCount; verseNum++){

            CallBackData callBackData = new CallBackData(Pages.TEXT,data + ":" + verseNum );

            versesButtonsRow[rowVerseNum] = new InlineKeyboardButton("" +verseNum).callbackData(CallBackData.toJson(callBackData));

            rowVerseNum++;

            if (rowVerseNum == 4) {
                inlineVerseKeyboardMarkup.addRow(versesButtonsRow);
                versesButtonsRow = new InlineKeyboardButton[4];
                rowVerseNum = 0;
            }


        }


        CallBackData callBackDataAll = new CallBackData(Pages.TEXT,data + ":" + "all" );
        inlineVerseKeyboardMarkup.addRow(new InlineKeyboardButton(Replies.ALL_VERSES).callbackData(CallBackData.toJson(callBackDataAll)));

        CallBackData callBackDataBack = new CallBackData(Pages.CHAPTER, dataSplit[0]);
        inlineVerseKeyboardMarkup.addRow(new InlineKeyboardButton(Replies.GO_BACK).callbackData(CallBackData.toJson(callBackDataBack)));

        return new Page(Replies.SELECT_VERSE, inlineVerseKeyboardMarkup);
    }


    public Page renderBookPage() throws JsonProcessingException {
        List<Book> books = bookRepository.findAll();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton[] bookButtonsRow = new InlineKeyboardButton[4];

        int rowNum = 0;
        for (int bookNum = 0; bookNum < books.size(); bookNum++){

            CallBackData callBackData = new CallBackData(Pages.CHAPTER,String.valueOf(books.get(bookNum).getId()));

            bookButtonsRow[rowNum] = new InlineKeyboardButton(books.get(bookNum).getBookName()).callbackData(CallBackData.toJson(callBackData));

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

        return new Page(Replies.SELECT_BOOK, inlineKeyboardMarkup);
    }

}
