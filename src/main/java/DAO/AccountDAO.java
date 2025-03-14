package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

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
}
