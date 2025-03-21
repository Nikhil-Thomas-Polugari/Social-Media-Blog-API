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
    private final AccountService accountService;
    private final MessageService messageService;
    private final ObjectMapper mapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/messages", this::createMessage);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.get("/accounts/{posted_by}", this::retrieveAllMessagesForUser);
        app.get("/messages", this::retrieveAllMessages);
        app.get("/messages/{message_id}", this::retrieveMessagesById);
        app.put("/messages/{message_id}", this::updateMessage);
        app.post("/login", this::userLogin);
        app.post("/register", this::userRegistration);
        return app;
    }

    private void createMessage(Context ctx) {
        try {
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message createdMessage = messageService.create_message(message);
            if (createdMessage != null) {
                ctx.json(mapper.writeValueAsString(createdMessage)).contentType("application/json");
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            ctx.status(400);
        }
    }

    private void deleteMessageById(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message deletedMessage = messageService.delete_message_by_id(messageId);
            if (deletedMessage != null) {
                ctx.json(mapper.writeValueAsString(deletedMessage)).contentType("application/json");
            } else {
                ctx.status(200);
            }
        } catch (JsonProcessingException e) {
            ctx.status(200);
        }
    }

    private void retrieveAllMessagesForUser(Context ctx) {
        try {
            int userId = Integer.parseInt(ctx.pathParam("posted_by"));
            List<Message> messages = messageService.get_all_messages_by_user(userId);
            if (!messages.isEmpty()) {
                ctx.json(mapper.writeValueAsString(messages)).contentType("application/json");
            } else {
                ctx.status(200);
            }
        } catch (JsonProcessingException e) {
            ctx.status(400);
        }
    }

    private void retrieveAllMessages(Context ctx) {
        try{
            List<Message> messages = messageService.get_all_messages();
            ctx.json(mapper.writeValueAsString(messages));
        }
        catch (JsonProcessingException e){
            ctx.status(400);
        }
    }

    private void retrieveMessagesById(Context ctx){
        try{
            int messageid = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.get_all_messages_by_id(messageid);
            if (message!= null){
                ctx.json(mapper.writeValueAsString(message)).contentType("application/json");
            }
        }
        catch (JsonProcessingException e){
            ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message updatedMessage = messageService.update_message(messageId, message.getMessage_text());
            if (updatedMessage != null) {
                ctx.json(mapper.writeValueAsString(updatedMessage)).contentType("application/json");
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            ctx.status(400);
        } catch (NumberFormatException e) {
            ctx.status(400);
        }
    }

    private void userLogin(Context ctx) {
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account loggedInUser = accountService.user_login(account);
            if (loggedInUser != null) {
                ctx.json(mapper.writeValueAsString(loggedInUser)).contentType("application/json");
            } else {
                ctx.status(401);
            }
        } catch (JsonProcessingException e) {
            ctx.status(400);
        }
    }

    private void userRegistration(Context ctx) {
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account registeredUser = accountService.user_registration(account);
            if (registeredUser != null) {
                ctx.json(mapper.writeValueAsString(registeredUser)).contentType("application/json") ;
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            ctx.status(400);
        }
    }
}