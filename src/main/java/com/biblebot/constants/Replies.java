package com.biblebot.constants;

public class Replies {

    public static final String WELCOME_MESSAGE = "Добро пожаловать в Бот";
    public static final String NO_SUCH_CHAPTER = "Нету такой главы";
    public static final String NO_SUCH_CHAPTER_OR_VERSE = "Нету такой главы или стиха";

    public static final String NO_BOOKS_WITH_THAT_NAME = "Нету книги с таким названием(, отправте /all что бы увидеть список всех книг";
    public static final String INCORRECT_FORMAT = "Не правильный формат данных вводите данные в форме - Бытие 1:1 или Бытие 1 для получение всей главы, напишити /all что бы увидеть сптсок всех все книги";

    public static final String ERROR_OCCURED_TRY_AGAIN = "Произошла ошибка попробуйте ёшё раз";

    public static final String ALL_VERSES = "Все стихи";

    public static final String GO_BACK = "Обратно";

    public static final String SELECT_BOOK = "Выбирете книгу";
    public static final String SELECT_CHAPTER = "Выбирете главу";
    public static final String SELECT_VERSE = "Выбирете стих";

    public static final String ALL_BOOKS = "Для выбора книги можно ввести либо полное название, либо указанную аббревиатуру. Учтите, что для некоторых книг с пробелами в названии необходимо вводить только аббревиатуру:\n" +
            "\n" +
            "- Бытие (Быт) — Введите \"Бытие\" или \"Быт\".\n" +
            "- Исход (Исх) — Введите \"Исход\" или \"Исх\".\n" +
            "- Левит (Лев) — Введите \"Левит\" или \"Лев\".\n" +
            "- Числа (Чис) — Введите \"Числа\" или \"Чис\".\n" +
            "- Второзаконие (Втор) — Введите \"Второзаконие\" или \"Втор\".\n" +
            "- Иисус Навин (Нав) — Введите \"Иисус Навин\" или \"Нав\".\n" +
            "- Судьи (Суд) — Введите \"Судьи\" или \"Суд\".\n" +
            "- Руфь (Руфь) — Введите \"Руфь\".\n" +
            "- 1-яЦарств (1Цар) — Введите \"1Цар\".\n" +
            "- 2-яЦарств (2Цар) — Введите \"2Цар\".\n" +
            "- 3-яЦарств (3Цар) — Введите \"3Цар\".\n" +
            "- 4-яЦарств (4Цар) — Введите \"4Цар\".\n" +
            "- 1-яПаралипоменон (1Пар) — Введите \"1Пар\".\n" +
            "- 2-яПаралипоменон (2Пар) — Введите \"2Пар\".\n" +
            "- Ездра (Ездр) — Введите \"Ездра\" или \"Ездр\".\n" +
            "- Неемия (Неем) — Введите \"Неемия\" или \"Неем\".\n" +
            "- Есфирь (Есф) — Введите \"Есфирь\" или \"Есф\".\n" +
            "- Иов (Иов) — Введите \"Иов\".\n" +
            "- Псалтирь (Пс) — Введите \"Псалтирь\" или \"Пс\".\n" +
            "- Притчи (Притч) — Введите \"Притчи\" или \"Притч\".\n" +
            "- Екклесиаст (Еккл) — Введите \"Екклесиаст\" или \"Еккл\".\n" +
            "- Песня Песней (Песн) — Введите \"Песн\".\n" +
            "- Исаия (Ис) — Введите \"Исаия\" или \"Ис\".\n" +
            "- Иеремия (Иер) — Введите \"Иеремия\" или \"Иер\".\n" +
            "- Плач Иеремии (Плач) — Введите \"Плач\".\n" +
            "- Иезекииль (Иез) — Введите \"Иезекииль\" или \"Иез\".\n" +
            "- Даниил (Дан) — Введите \"Даниил\" или \"Дан\".\n" +
            "- Осия (Ос) — Введите \"Осия\" или \"Ос\".\n" +
            "- Иоиль (Иоиль) — Введите \"Иоиль\".\n" +
            "- Амос (Ам) — Введите \"Амос\" или \"Ам\".\n" +
            "- Авдий (Авд) — Введите \"Авдий\" или \"Авд\".\n" +
            "- Иона (Иона) — Введите \"Иона\".\n" +
            "- Михей (Мих) — Введите \"Михей\" или \"Мих\".\n" +
            "- Наум (Наум) — Введите \"Наум\".\n" +
            "- Аввакум (Авв) — Введите \"Аввакум\" или \"Авв\".\n" +
            "- Софония (Соф) — Введите \"Софония\" или \"Соф\".\n" +
            "- Аггей (Агг) — Введите \"Аггей\" или \"Агг\".\n" +
            "- Захария (Зах) — Введите \"Захария\" или \"Зах\".\n" +
            "- Малахия (Мал) — Введите \"Малахия\" или \"Мал\".\n" +
            "- От Матфея (Матф) — Введите \"Матф\".\n" +
            "- От Марка (Мар) — Введите \"Мар\".\n" +
            "- От Луки (Лук) — Введите \"Лук\".\n" +
            "- От Иоанна (Иоан) — Введите \"Иоан\".\n" +
            "- Деяния (Деян) — Введите \"Деян\".\n" +
            "- Иакова (Иак) — Введите \"Иакова\" или \"Иак\".\n" +
            "- 1-еПетра (1Пет) — Введите \"1Пет\".\n" +
            "- 2-еПетра (2Пет) — Введите \"2Пет\".\n" +
            "- 1-еИоанна (1Ин) — Введите \"1Ин\".\n" +
            "- 2-еИоанна (2Ин) — Введите \"2Ин\".\n" +
            "- 3-еИоанна (3Ин) — Введите \"3Ин\".\n" +
            "- Иуды (Иуд) — Введите \"Иуд\".\n" +
            "- К Римлянам (Рим) — Введите \"Рим\".\n" +
            "- 1-еКоринфянам (1Кор) — Введите \"1Кор\".\n" +
            "- 2-еКоринфянам (2Кор) — Введите \"2Кор\".\n" +
            "- К Галатам (Гал) — Введите \"Гал\".\n" +
            "- К Ефесянам (Еф) — Введите \"Еф\".\n" +
            "- К Филиппийцам (Флп) — Введите \"Флп\".\n" +
            "- К Колоссянам (Кол) — Введите \"Кол\".\n" +
            "- 1-еФессалоникийцам (1Фес) — Введите \"1Фес\".\n" +
            "- 2-еФессалоникийцам (2Фес) — Введите \"2Фес\".\n" +
            "- 1-еТимофею (1Тим) — Введите \"1Тим\".\n" +
            "- 2-еТимофею (2Тим) — Введите \"2Тим\".\n" +
            "- К Титу (Тит) — Введите \"Тит\".\n" +
            "- К Филимону (Флм) — Введите \"Флм\".\n" +
            "- К Евреям (Евр) — Введите \"Евр\".\n" +
            "- Откровение (Откр) — Введите \"Откр\".";


}
