package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class MessageDAO {
    private boolean registered_user(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT account_id FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            ResultSet loginResultSet = preparedStatement.getGeneratedKeys();
            if (loginResultSet.next()){
                return true;
            }
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return false;
    }
    private boolean valid_message(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        int message_id_from_table = 0;
        try{
            String sql = "SELECT message_id FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeQuery();
            ResultSet loginResultSet = preparedStatement.getGeneratedKeys();
            if (loginResultSet.next()){
                message_id_from_table = loginResultSet.getInt(1);
            }
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return message_id == message_id_from_table;
    }

    public Message create_message(Message message){
        int posted_by = message.getPosted_by();
        String message_text = message.getMessage_text();
        long time_posted_epoch = message.getTime_posted_epoch();
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(registered_user(posted_by) && message_text.length() < 255 && (message_text != "" || message_text != null)){
                String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, posted_by);
                preparedStatement.setString(2, message_text);
                preparedStatement.setLong(3,time_posted_epoch);
                preparedStatement.executeUpdate();
                ResultSet messageResultSet = preparedStatement.getGeneratedKeys();
                if (messageResultSet.next()){
                    //int generated_message_id = (int) messageResultSet.getLong(1);
                    int generated_message_id  = messageResultSet.getInt("message_id");
                    return new Message(generated_message_id, posted_by, message_text, time_posted_epoch);
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return null;
    }

    public List<Message> get_all_messages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public List<Message> get_all_messages_by_id(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    

    public Message delete_message_by_id(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(valid_message(message_id)){
                String sql = "SELECT message WHERE message_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, message_id);
                ResultSet deleteMessageResultSet = preparedStatement.executeQuery();
                if(deleteMessageResultSet.next()){
                    int generated_message_id  = deleteMessageResultSet.getInt("message_id");
                    int generated_posted_by = deleteMessageResultSet.getInt("posted_by");
                    String generated_message_text = deleteMessageResultSet.getString("message_text");
                    long generated_time_posted_epoch = deleteMessageResultSet.getLong("time_posted_epoch");
                    return new Message(generated_message_id, generated_posted_by, generated_message_text, generated_time_posted_epoch);
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Message updated_message(int message_id, Message message){
        String message_text = message.getMessage_text();
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(valid_message(message_id) && (message_text.length() < 255 && (message_text != "" || message_text != null))){
                String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, message_text);
                preparedStatement.setInt(2, message_id);
                preparedStatement.executeUpdate();
                ResultSet messageResultSet = preparedStatement.getGeneratedKeys();
                if (messageResultSet.next()){
                    int generated_posted_by = (int) messageResultSet.getLong(2);
                    long generated_time_posted_epoch = messageResultSet.getLong(4);
                    return new Message(message_id, generated_posted_by, message_text, generated_time_posted_epoch);
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return null;
    }
}
