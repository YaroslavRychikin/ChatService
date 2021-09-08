fun main() {
    val service = ChatService()
    service.createChat(Chat(ownerId = 10,
        userId = 99))
    service.deleteChat(chatId = 1,
        userId = 11)
    service.deleteChat(chatId = 1,
        userId = 10)
    service.createChat(Chat(ownerId = 10,
        userId = 66))
    service.sendMessage(chatId = 2,
        Message(text = "Hello"),
        userId = 66)
    service.sendMessage(chatId = 2,
        Message(text = "How are you"),
        userId = 66)
    service.sendMessage(chatId = 2,
        Message(text = "Answer"),
        userId = 66)
    service.sendMessage(chatId = 2,
        Message(text = "How are you"),
        userId = 99)
    service.sendMessage(chatId = 3,
        Message(text = "How are you"),
        userId = 2)
    service.createChat(Chat(ownerId = 10,
        userId = 99))
    service.getChats(userId = 10)
    service.getUnreadChatsCount(userId = 7)
    service.getUnreadChatsCount(userId = 10)
    service.getMessages(chatId = 2,
        firstMessageId = 1,
        count = 2,
        userId = 10)
    service.getMessages(chatId = 2,
        firstMessageId = 1,
        count = 2,
        userId = 1)
    service.redactMessage(chatId = 2,
        messageId = 2,
        userId = 11,
        text = "Let's go to the cinema")
    service.redactMessage(chatId = 2,
        messageId = 2,
        userId = 10,
        text = "Let's go to the cinema")
    service.redactMessage(chatId = 2,
        messageId = 2,
        userId = 66,
        text = "Let's go to the cinema")
    service.deleteMessage(chatId = 2,
        messageId = 3,
        userId = 88)
    service.deleteMessage(chatId = 2,
        messageId = 3,
        userId = 10)
}