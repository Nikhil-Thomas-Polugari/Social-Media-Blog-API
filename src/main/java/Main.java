import Controller.SocialMediaController;
import io.javalin.Javalin;


import Util.ConnectionUtil;
import Util.FileUtil;

import java.sql.*;

import Model.*;
import Service.*;
import Util.*;
import java.util.*;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {

        String sql = FileUtil.parseSQLFile("SocialMedia.sql");
        try {
            Connection connection = ConnectionUtil.getConnection();
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
            String sqlAccount = "SELECT * FROM account;";
            Statement a = connection.createStatement();
            String sqlMessage = "SELECT * FROM message;";
            Statement m = connection.createStatement();

            ResultSet account_info = a.executeQuery(sqlAccount);
            ResultSet message_info = m.executeQuery(sqlMessage);
            System.out.println(account_info);
            System.out.println(message_info);
            
        } catch (SQLException e) {
            System.out.println("Issue: " + e.getMessage() + '\n');
        }


        

        Account account = new Account("testuser1", "password");
        Account account2 = new Account("testuser2", "password2");
        Account account3 = new Account("testuser3", "password3");
        AccountService accountService = new AccountService();
        
        accountService.user_registration(account);
        accountService.user_registration(account2);
        accountService.user_registration(account3);

        System.out.println();
        
        accountService.user_login(account);
        accountService.user_login(account2);
        accountService.user_login(account3);

        /*
        List<Account> all_registered_users = accountService.registered_users();
        
        System.out.println(all_registered_users.size());
        
        System.out.println();
        System.out.println("1: " + all_registered_users.get(0));
        System.out.println("2: " + all_registered_users.get(1));
        System.out.println("3: " + all_registered_users.get(2));
        
        Message message = new Message(1, "I like tacos.", 1669947792);
        Message message2 = new Message(2, "Same.", 1669947792);
        Message message3 = new Message(3, "I do not like tacos.", 1669947792);
        MessageService service = new MessageService();

        Message created_Message = service.create_message(message);
        Message created_Message2 = service.create_message(message2);
        Message created_Message3 = service.create_message(message3);

        System.out.println();

        System.out.println("4: " + created_Message);
        System.out.println("5: " + created_Message2);
        System.out.println("6: " + created_Message3);

        List<Message> all_messages = service.get_all_messages();

        System.out.println();

        System.out.println("7: ");
        for (int i = 0; i < all_messages.size() ; i++){
            System.out.println(all_messages.get(i));
        }

        List<Message> all_messages_by_id = service.get_all_messages_by_id(created_Message);
        List<Message> all_messages_by_id_2 = service.get_all_messages_by_id(created_Message2);
        List<Message> all_messages_by_id_3 = service.get_all_messages_by_id(created_Message3);
        
        System.out.println();
        
        System.out.println(all_messages_by_id.size() + " " + all_messages_by_id_2.size() + " " + all_messages_by_id_3.size());
        
        System.out.println();
        
        System.out.println("8: ");
        System.out.println(all_messages_by_id.get(0));
        System.out.println(all_messages_by_id_2.get(0));
        System.out.println(all_messages_by_id_3.get(0));
        
        /*
        for (int i = 0; i < all_messages.size() ; i++){
            System.out.println(all_messages_by_id.get(i));
            System.out.println(all_messages_by_id_2.get(i));
            System.out.println(all_messages_by_id_3.get(i));
        }
        

        Message newMessage = new Message(1, "I love tacos", 1669947792);
        Message newMessage2 = new Message(2, "Me and you are alike", 1669947792);
        Message newMessage3 = new Message(3, "I hate tacos", 1669947792);

        Message updated1 = service.update_message(message.getMessage_id(), newMessage);
        Message updated2 = service.update_message(message2.getMessage_id(), newMessage2);
        Message updated3 = service.update_message(message3.getMessage_id(), newMessage3);

        System.out.println();

        System.out.println("9: " + updated1);
        System.out.println("10: " + updated2);
        System.out.println("11: " + updated3);
        
        Message deletedMessage = service.delete_message_by_id(message);
        Message deletedMessage2 = service.delete_message_by_id(message2);
        Message deletedMessage3 = service.delete_message_by_id(message3);

        System.out.println();

        System.out.println("12: " + deletedMessage);
        System.out.println("13: " + deletedMessage2);
        System.out.println("14: " + deletedMessage3);
        */
        

        /*
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        */
    }
}
