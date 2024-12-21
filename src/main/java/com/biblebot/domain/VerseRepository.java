package com.biblebot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VerseRepository extends JpaRepository<Verse, Long> {

    Optional<Verse> findByBookIdAndChapterAndVerseNumber(Long bookId, int chapter, int verseNumber);

    Optional<List<Verse>> findAllByChapterAndBookId(int chapter, Long bookId);

    @Query("SELECT COUNT(DISTINCT v.chapter) FROM Verse v WHERE v.bookId = :bookId")
    long countDistinctChapterByBookId(@Param("bookId") long bookId);

    long countByBookIdAndChapter(long bookId, int chapter);

}
