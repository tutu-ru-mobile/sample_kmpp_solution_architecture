package com.sample

import com.github.kotlintelegrambot.HandleUpdate
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandler
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN_V2
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.github.kotlintelegrambot.entities.TelegramFile.ByUrl
import com.github.kotlintelegrambot.entities.dice.DiceEmoji
import com.github.kotlintelegrambot.entities.inlinequeryresults.InlineQueryResult
import com.github.kotlintelegrambot.entities.inlinequeryresults.InputMessageContent
import com.github.kotlintelegrambot.entities.inputmedia.InputMediaPhoto
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.entities.polls.PollType.QUIZ
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import com.github.kotlintelegrambot.network.fold

val CHAT_ID = 185159406L

fun runBot(telegramBotToken: String) {

    val bot = bot {
        token = telegramBotToken
        timeout = 30
        logLevel = LogLevel.Network.Body

        dispatch {
            command("markdown") {
                val markdownV2Text = """
                    *bold \*text*
                    _italic \*text_
                    __underline__
                    ~strikethrough~
                    *bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
                    [inline URL](http://www.example.com/)
                    [inline mention of a user](tg://user?id=123456789)
                    `inline fixed-width code`
                    ```kotlin
                    fun main() {
                        println("Hello Kotlin!")
                    }
                    ```
                """.trimIndent()
                bot.sendMessage(
                    chatId = message.chat.id,
                    text = markdownV2Text,
                    parseMode = MARKDOWN_V2
                )
            }

            command("start") {//
                val inlineKeyboardMarkup = InlineKeyboardMarkup(generateButtons())
                bot.sendMessage(
                    chatId = message.chat.id,
                    text = "Hello, inline buttons!",
                    replyMarkup = inlineKeyboardMarkup
                )
            }

            command("userButtons") {// todo навигация
                val keyboardMarkup =
                    KeyboardReplyMarkup(keyboard = generateUsersButton(), resizeKeyboard = true)
                bot.sendMessage(
                    chatId = message.chat.id,
                    text = "Hello, users buttons!",
                    replyMarkup = keyboardMarkup
                )
            }

            callbackQuery("ca") { bot, update ->
                update.callbackQuery?.let {
                    val chatId = it.message?.chat?.id ?: return@callbackQuery
                    bot.sendMessage(
                        chatId = chatId,
                        text = "выберите",
                        replyMarkup = InlineKeyboardMarkup(
                            listOf(
                                listOf(InlineKeyboardButton("билет1", callbackData = "quality")),
                                listOf(InlineKeyboardButton("билет2", callbackData = "quality"))
                            )
                        )
                    )
                }
            }

            callbackQuery("quality") { bot, update ->
                update.callbackQuery?.let {
                    val chatId = it.message?.chat?.id ?: return@callbackQuery
                    bot.sendPoll(
                        chatId = chatId,
                        type = QUIZ,
                        question = "Ура! Вы приобрели билет. Довольны качеством сервиса?",
                        options = listOf("Да, доволен", "Средне...", "Нет, не доволен"),
                        correctOptionId = 0,
                        isAnonymous = false
                    )
                }
            }

            callbackQuery("testButton") { bot, update ->
                update.callbackQuery?.let {
                    val chatId = it.message?.chat?.id ?: return@callbackQuery
                    bot.sendMessage(chatId = chatId, text = it.data + " more data")
                }
            }

            text("ping") { bot, update ->
                bot.sendMessage(chatId = update.message!!.chat.id, text = "Pong")
            }

            channel { bot, update ->//todo unused?
                println("handle channel: update.message?.text: ${update.message?.text}")
            }

            text(/* may specify text */) { bot, update ->
                println("handle text: update.message?.text: ${update.message?.text}, chatId: ${update.message?.chat?.id}")
            }

            telegramError { _, telegramError ->
                println(telegramError.getErrorMessage())
            }
        }
    }

    bot.startPolling()
    bot.sendMessage(
        chatId = CHAT_ID,
        text = "immediately message"
    )
}

fun generateUsersButton(): List<List<KeyboardButton>> {//todo навигация
    return listOf(
        listOf(
            KeyboardButton(
                "Request location (not supported on desktop)",
                requestLocation = true
            )
        ),
        listOf(KeyboardButton("Request contact", requestContact = true))
    )
}

fun createAlertCallbackQueryHandler(handler: HandleUpdate): CallbackQueryHandler {
    return CallbackQueryHandler(
        callbackData = "showAlert",
        callbackAnswerText = "HelloText",
        callbackAnswerShowAlert = true,
        handler = handler
    )
}

fun generateButtons(): List<List<InlineKeyboardButton>> {
    return listOf(
        listOf(InlineKeyboardButton(text = "Test Inline Button", callbackData = "testButton")),
        listOf(InlineKeyboardButton(text = "Show alert", callbackData = "showAlert")),
        listOf(
            InlineKeyboardButton(text = "a", callbackData = "ca"),
            InlineKeyboardButton(text = "b", callbackData = "cb")
        )
    )
}