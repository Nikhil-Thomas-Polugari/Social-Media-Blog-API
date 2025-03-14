package Controller;

import org.eclipse.jetty.http.HttpTester.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.create_message(message);
        if(createdMessage != null){
            ctx.json(mapper.writeValueAsString(createdMessage));
        }
    }
    private void deleteMessageByMessageId(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message deletedMessage = messageService.delete_message_by_id(message);
        if(deletedMessage != null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }
    }
    private void retrieveAllMessagesForUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message deletedMessage = messageService.get_all_messages_by_id(message);
        if(deletedMessage != null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }
    }
    private void retrieveAllMessages(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.get_all_messages();
        ctx.json(messages);
    }
    private void updateMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.update_message(message);
        if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }
    }
    private void userLogin(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account logged_in_user = messageService.user_login(user);
        if(logged_in_user != null){
            ctx.json(mapper.writeValueAsString(logged_in_user));
        }
    }
    private void userRegistration(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account registered_user = messageService.user_registration(user);
        if(registered_user != null){
            ctx.json(mapper.writeValueAsString(registered_user));
        }
    }
}