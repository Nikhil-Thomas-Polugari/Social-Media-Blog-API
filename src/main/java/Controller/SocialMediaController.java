package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import java.util.List;

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
        app.get("/messages/user", this::retrieveAllMessagesForUser);
        app.get("/messages", this::retrieveAllMessages);
        app.put("/message", this::updateMessage);
        app.post("/login", this::userLogin);
        app.post("/register", this::userRegistration);

        return app;
    }

    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private <T> T parseRequestBody(Context ctx, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(ctx.body(), clazz);
    }

    private void createMessage(Context ctx) {
        try {
            boolean isCreated = messageService.create_message(parseRequestBody(ctx, Message.class));
            if (isCreated) {
                ctx.status(201).json("Message created successfully");
            } else {
                ctx.status(400).json("Message creation failed");
            }
        } catch (JsonProcessingException e) {
            ctx.status(400).json("Invalid message data");
        }
    }

    private void deleteMessageByMessageId(Context ctx) {
        try {
            Message deletedMessage = messageService.delete_message_by_id(parseRequestBody(ctx, Message.class));
            if (deletedMessage != null) {
                ctx.json(deletedMessage);
            } else {
                ctx.status(400).json("Message deletion failed");
            }
        } catch (JsonProcessingException e) {
            ctx.status(400).json("Invalid message data");
        }
    }

    private void retrieveAllMessagesForUser(Context ctx) {
        try {
            // Extract the message_id from path parameters or query parameters
            String messageId = ctx.pathParam("message_id");  // If passed as a path parameter
            // Alternatively, use queryParam for query parameters
            // String messageId = ctx.queryParam("message_id");
    
            if (messageId != null && !messageId.isEmpty()) {
                // Assuming MessageService has a method to get a message by ID
                Message message = messageService.get_all_message_by_id(messageId);  // Modify service method accordingly
                if (message != null) {
                    ctx.json(message);  // Return the found message
                } else {
                    ctx.status(404).json("Message not found");
                }
            } else {
                ctx.status(400).json("Missing message_id");
            }
        } catch (Exception e) {
            ctx.status(500).json("Error processing request");
        }
    }
    
    

    private void retrieveAllMessages(Context ctx) {
        List<Message> messages = messageService.get_all_messages();
        ctx.json(messages);
    }

    private void updateMessage(Context ctx) {
        try {
            Message message = parseRequestBody(ctx, Message.class);
            Message updatedMessage = messageService.update_message(message);
            if (updatedMessage != null) {
                ctx.json(updatedMessage);
            } else {
                ctx.status(400).json("Message update failed");
            }
        } catch (JsonProcessingException e) {
            ctx.status(400).json("Invalid message data");
        }
    }

    private void userLogin(Context ctx) {
        try {
            Account user = parseRequestBody(ctx, Account.class);
            Account loggedInUser = accountService.user_login(user.getUsername(), user.getPassword());  // Pass username and password
            if (loggedInUser != null) {
                ctx.json(loggedInUser);
            } else {
                ctx.status(400).json("Login failed");
            }
        } catch (JsonProcessingException e) {
            ctx.status(400).json("Invalid login data");
        }
    }

    private void userRegistration(Context ctx) {
        try {
            Account user = parseRequestBody(ctx, Account.class);
            Account registeredUser = accountService.user_registration(user.getUsername(), user.getPassword());  // Pass username and password
            if (registeredUser != null) {
                ctx.json(registeredUser);
            } else {
                ctx.status(400).json("Registration failed");
            }
        } catch (JsonProcessingException e) {
            ctx.status(400).json("Invalid registration data");
        }
    }
}
