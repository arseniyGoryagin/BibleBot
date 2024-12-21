package com.biblebot.page;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Page {


    private String text;
    private InlineKeyboardMarkup inlineKeyboardMarkup;


}
