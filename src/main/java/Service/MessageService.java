package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message create_message(Message message){
        return messageDAO.create_message(message);
    }

    public List<Message> get_all_messages(){
        return messageDAO.get_all_messages();
    }

    public Message get_all_messages_by_id(Message message){
        int message_id = message.getMessage_id();
        return messageDAO.get_all_messages_by_id(message_id);
    }

    public Message delete_message_by_id(Message message){
        int message_id = message.getMessage_id();
        return messageDAO.delete_message_by_id(message_id);
    }

    public Message update_message(int message_id, String message_text){
        return messageDAO.updated_message(message_id, message_text);
    }
}
