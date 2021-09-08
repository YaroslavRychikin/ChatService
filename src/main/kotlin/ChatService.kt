open class Id(var id: Int)
class ChatService{
    private var nextId = 1
    private var nextIdOfMessage = 1
    private val chats = mutableListOf<Chat>()

    fun createChat(chat: Chat){
        chat.id = nextId
        chats += chat
        nextId++
    }

    fun deleteChat(chatId: Int, userId: Int) {
        print("___\n")
        val chat = chats.find{it.id == chatId}
        if (chat != null){
            if(chat.userId == userId || chat.ownerId == userId){
                chats.remove(chat)
            }else println("Нет доступа к этому чату")
        } else println("Такого чата не существует")
    }

    fun sendMessage(chatId: Int, message: Message, userId: Int){
        print("___\n")
        val chat = chats.find { it.id == chatId }
        if (chat != null) {
            if (chat.userId == userId || chat.ownerId == userId) {
                chat.messages.add(message.copy(id = nextIdOfMessage++, ownerId = userId))
            } else println("Нет доступа к этому чату")
        } else println("Такого чата не существует")
    }

    fun redactMessage(chatId: Int, messageId: Int, userId: Int, text: String) {
        print("___\n")
        val chat = chats.find { it.id == chatId }
        if (chat != null) {
            if (chat.userId == userId || chat.ownerId == userId) {
                if (chat.messages.isNotEmpty()) {
                    val message = chat.messages.find { it.id == messageId }
                    if (message != null) {
                        if (message.ownerId == userId){
                            val index = chat.messages.indexOf(message)
                            chat.messages[index] = message.copy(text = text)
                        } else println("Вы не можете редактировать это сообщение")
                    } else println("Данного сообщения нет в чате")
                } else println("В данном чате нет сообщений")
            } else println("Нет доступа к этому чату")
        } else println("Такого чата не существует")
    }

    fun deleteMessage(chatId: Int, messageId: Int, userId: Int) {
        print("___\n")
        val chat = chats.find { it.id == chatId }
        if (chat != null) {
            if (chat.userId == userId || chat.ownerId == userId) {
                if (chat.messages.isNotEmpty()) {
                    val message = chat.messages.find { it.id == messageId }
                    if (message != null) {
                        chat.messages.remove(message)
                        if (chat.messages.isEmpty()) chats.remove(chat)
                    } else println("Данного сообщения нет в чате")
                } else println("В данном чате нет сообщений")
            } else println("Нет доступа к этому чату")
        } else println("Такого чата не существует")
    }

    fun getUnreadChatsCount(userId: Int) {
        print("___\n")
        var countOfChats = 0
        var countOfMessages = 0
        chats.filter {it.userId == userId || it.ownerId == userId}
            .map{it.messages}
            .filter{messages -> messages.isNotEmpty()}
            .forEach{it.forEach { message -> if(!message.read) countOfMessages++}
                if (countOfMessages > 0) {
                    countOfChats++
                    countOfMessages = 0
                } }
        println("Количество чатов, где есть хотя бы одно непрочитанное сообщение" +
                ", к которым есть у вас доступ: $countOfChats")
    }
    fun getChats(userId: Int) {
        print("___\n")
        chats.forEach{chat -> if (chat.ownerId == userId || chat.userId == userId) {
            println("Chat: id:${chat.id}, ownerId:${chat.ownerId}, " +
               "userId:${chat.userId}, колличество сообщений:" +
               "${if(chat.messages.size == 0) "Нет Сообщений" else chat.messages.size}")
        } else println("Нет доступа к этому чату")}
    }

    fun getMessages(chatId: Int, firstMessageId: Int, count: Int, userId: Int) {
        print("___\n")
        var index = 1
        val chat = chats.find { it.id == chatId }
        if (chat != null) {
            if (chat.userId == userId || chat.ownerId == userId) {
                if (chat.messages.isNotEmpty()) {
                    chat.messages.filter { message -> message.id >= firstMessageId }
                        .forEach { message ->
                            if (index++ <= count) {
                                message.read = true
                                println(
                                    "Message: id:${message.id}, ownerId:${message.ownerId}, " +
                                            "текст:${message.text}, прочитано ли:${message.read}"
                                )
                            }
                        }
                } else println("В данном чате нет сообщений")
            } else println("Нет доступа к этому чату")
        } else println("Такого чата не существует")
    }
}

class Chat(id: Int = 0,
           val ownerId: Int,
           val userId: Int, ): Id(id){
    val messages = mutableListOf<Message>()
}
data class Message(val id: Int = 0,
                   val ownerId: Int = 0,
                   val text: String,
                   var read: Boolean = false)