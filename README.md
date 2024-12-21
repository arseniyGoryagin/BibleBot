# BibleBot
BibleBot is a Telegram bot designed to provide easy access to Bible verses. Users can request specific verses or explore the Bible using an intuitive menu and inline keyboard navigation.

## Features
🌟 Search for Bible verses by directly sending commands in the format [Book] [Chapter]:[Verse] (e.g., Gen 1:1 for Genesis 1:1).
📚 Browse through books, chapters, and verses using an inline keyboard.
🔍 Quickly find verses without needing a physical Bible.
## Technologies Used
**Java**: Core backend programming language for the bot's logic and operations.

**Pengrams** Bot API: Library for seamless interaction with the Telegram Bot API.

**Postgres:** Database for storing Bible text/books.

**Spring:** Framework used for dependency injection, data management, and REST API support.

## Setup and Usage
Clone the repository:

```
git clone https://github.com/yourusername/BibleVerseBot.git
cd BibleVerseBot
```
Configure the database:


Set up a connection with environment variables
```
export SPRING_DATASOURCE_URL=your_connection_url
export SPRING_DATASOURCE_USERNAME=your_connection_username
export SPRING_DATASOURCE_PASSWORD=your_connection_password
```

* Update application.properties with your database configuration details.

You can download thw bible sql from:
https://github.com/arseniyGoryagin/BibleVersesSql

Set up your bot:
Obtain a bot token from BotFather on Telegram.
Add your bot token to the environment variables:

```
export BOT_TOKEN=your-bot-token
```
Run the application:

```
./mvnw spring-boot:run
```

Interact with the bot on Telegram and enjoy exploring the Bible!

## Contributing
Contributions are welcome! Feel free to fork the repository, make changes, and submit a pull request.
