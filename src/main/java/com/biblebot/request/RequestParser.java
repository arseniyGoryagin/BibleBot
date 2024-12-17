package com.biblebot.request;

import com.biblebot.request.domain.Request;

public class RequestParser {

    static public Request parseRequest(String request){

        String[] message = request.split(" ");
        String bookName = message[0];
        String chapter = message[1].split(":")[0];
        String verseNumber = message[1].split(":")[1];

        return new Request(bookName, Integer.parseInt(chapter), Integer.parseInt(verseNumber));
    }
}
