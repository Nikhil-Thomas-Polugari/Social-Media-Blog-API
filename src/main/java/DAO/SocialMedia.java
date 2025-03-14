package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;


public class SocialMedia {

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
        try{
            String sql = "SELECT account_id FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message_id);
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

    public Account user_registration(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(username != null || username != "" || password.length() < 4){
                String sql = "INSERT INTO account (username, password) VALUES (?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
                ResultSet registrationResultSet = preparedStatement.getGeneratedKeys();
                if (registrationResultSet.next()){
                    int generated_registration_id = (int) registrationResultSet.getLong(1);
                    return new Account(generated_registration_id, username, password);
                }
            }
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Account user_login(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(username != null || username != "" || password.length() < 4){
                String sql = "SELECT account_id, username FROM account WHERE username = ? AND password = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeQuery();
                ResultSet loginResultSet = preparedStatement.getGeneratedKeys();
                if (loginResultSet.next()){
                    return new Account(loginResultSet.getInt("account_id"), loginResultSet.getString("username"), loginResultSet.getString("password"));
                }
            }
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Message create_message(int posted_by, String message_text, int time_posted_epoch){
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(registered_user(posted_by) && message_text.length() > 255 && (message_text != "" || message_text != null)){
                String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, posted_by);
                preparedStatement.setString(2, message_text);
                preparedStatement.setLong(3,time_posted_epoch);
                preparedStatement.executeUpdate();
                ResultSet messageResultSet = preparedStatement.getGeneratedKeys();
                if (messageResultSet.next()){
                    int generated_message_id = (int) messageResultSet.getLong(1);
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
    

    public boolean delete_message_by_id(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(valid_message(message_id)){
                String sql = "DELETE FROM message WHERE message_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, message_id);
                ResultSet deleteMessageResultSet = preparedSatement.executeUpdate();
                if(deleteMessageResultSet.next()){
                    return true;
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return false;
    }
}
