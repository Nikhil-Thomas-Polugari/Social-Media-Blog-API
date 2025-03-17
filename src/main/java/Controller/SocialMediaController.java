package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import java.util.*;

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/message", this::createMessage);
        app.delete("/message", this::deleteMessageByMessageId);
        app.get("/messages/message_id", this::retrieveAllMessagesForUser);
        app.get("/messages", this::retrieveAllMessages);
        app.put("/message", this::updateMessage);
        app.post("/login", this::userLogin);
        app.post("/register", this::userRegistration);
        //app.start(8080);
        return app;
    }

    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    private void createMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message created_Message = messageService.create_message(message);
        if (created_Message != null) {
            ctx.json(message);
        } else {
            ctx.status(400);
        }
    }

    private void deleteMessageByMessageId(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        boolean deletedMessage = messageService.delete_message_by_id(message);
        if (!deletedMessage) {
            ctx.status(200);
        } else {
            ctx.json("Deleted messege");
        }
    }

    private void retrieveAllMessagesForUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        List<Message> retrievedMessage = messageService.get_all_messages_by_id(message);
        if(retrievedMessage.size() == 0){
            ctx.status(400);
        }else{
            for(Message messages : retrievedMessage){
                ctx.json(messages);
            }
        }
    }
    
    

    private void retrieveAllMessages(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.get_all_messages();
        ctx.json(messages);
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.update_message(Integer.parseInt(ctx.pathParam("messages")), message);
        if (updatedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(updatedMessage);
        }
    }

    private void userLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInUser = accountService.user_login(account);
        if (loggedInUser == null) {
            ctx.status(400);
        } else {
            ctx.json(loggedInUser);
        }
    }

    private void userRegistration(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredUser = accountService.user_registration(account);
        if (registeredUser == null) {
            ctx.status(400);
        } else {
            ctx.json(registeredUser);
        }
    }
}
