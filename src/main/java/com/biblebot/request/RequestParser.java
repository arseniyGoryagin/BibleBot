package com.biblebot.request;

import com.biblebot.request.domain.Request;

public class RequestParser {

    static public Request parseRequest(String request){

        if(request == null || request.isEmpty()){
            throw new IllegalArgumentException("Request не может быть пустым");
        }

        String[] message = request.split(" ");

        if(message.length != 2){
            throw new IllegalArgumentException("Request не в той форме");
        }

        String bookName = message[0];

        String[] chapterAndOrVerseNumber = message[1].split(":");

        try {
            if (chapterAndOrVerseNumber.length == 1) {
                return new Request(bookName, Integer.parseInt(chapterAndOrVerseNumber[0]), null);
            }
            return new Request(bookName, Integer.parseInt(chapterAndOrVerseNumber[0]), Integer.parseInt(chapterAndOrVerseNumber[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Request глава и праграф должны быть цифрами");
        }
    }
}
