import Controller.SocialMediaController;
import io.javalin.Javalin;

import Model.*;
import Util.*;
import Service.*;
import java.util.*;
/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {

        ConnectionUtil connection = new ConnectionUtil();
        connection.resetTestDatabase();

        AccountService acc_Service = new AccountService();
        Account account1 = new Account("TESTUSER1", "PASSWORD");
        Account account2 = new Account("Testuser2", "password");

        System.out.println("1: " + acc_Service.user_registration(account1));
        System.out.println("2: " + acc_Service.user_registration(account2));

        System.out.println();

        System.out.println("3: " + acc_Service.user_login(account1));
        System.out.println("4: " + acc_Service.user_login(account2));


        MessageService msg_Service = new MessageService();
        Message message1 = new Message(1, "Hi there!", 199966441);
        Message message2 = new Message(2, "Hello", 199966443);


        Message created_message_1 = msg_Service.create_message(message1);
        Message created_message_2 = msg_Service.create_message(message2);

        System.out.println("5: " + created_message_1);
        System.out.println("6: " + created_message_2);

        System.out.println();


        List<Message> all_messages = msg_Service.get_all_messages();
        for(Message message: all_messages){
            System.out.println("7: " + message);
        }

        System.out.println();

        Message gottenMessage1 = msg_Service.get_all_messages_by_id(created_message_1.getMessage_id());
        Message gottenMessage2 = msg_Service.get_all_messages_by_id(created_message_2.getMessage_id());

        System.out.println("8: " + gottenMessage1);
        System.out.println("9: " + gottenMessage2);

        System.out.println();

        //Message updateMessage1 = new Message(1, "Hello back", 199966450);
        //Message updateMessage2 = new Message(2, "How are you?", 199966470);

        System.out.println("10: " + msg_Service.update_message(1, "Hello back"));
        System.out.println("11: " + msg_Service.update_message(1, "How are you?"));


        System.out.println();

        System.out.println("12: " + msg_Service.delete_message_by_id(created_message_1.getMessage_id()));
        System.out.println("13: " + msg_Service.delete_message_by_id(created_message_2.getMessage_id()));


        System.out.println();


        all_messages = msg_Service.get_all_messages();
        for(Message message: all_messages){
            System.out.println("14: " + message);
        }

        /*
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        */
    }
}
