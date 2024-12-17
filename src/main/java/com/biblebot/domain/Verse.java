package com.biblebot.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="rst_verses")
@Data
public class Verse {

    @Id
    private Long id;

    @Column(name = "book_id")
    @JoinColumn(name = "book_id")
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
