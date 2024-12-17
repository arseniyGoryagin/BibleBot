package com.biblebot.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="verses")
@Data
public class Verse {

    @Id
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    private int chapter;

    @Column(name = "verse_number")
    private int verseNumber;

    @Column(name = "verse_text")
    private String verseText;

    private String translation;
    private String language;
    private String abbreviation;
    private String direction;
    private String encoding;





}
