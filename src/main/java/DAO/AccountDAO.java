package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    private boolean registered_user(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
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

    public Account user_registration(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        //System.out.println(username + " " + password);
        Connection connection = ConnectionUtil.getConnection();
        try{
            if (username == null || username.isEmpty() || password.length() < 4) {
                //System.out.println("Checking if the Password is too short or username is empty");
                return null;
            }
        
            // Check if user already exists
            String checkUserSQL = "SELECT account_id FROM account WHERE username = ?;";
            PreparedStatement checkStmt = connection.prepareStatement(checkUserSQL);
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();
        
            if (resultSet.next()) {
                // User already exists, return the existing user
                int existingAccountId = resultSet.getInt("account_id");
                //System.out.println("User already registered, returning existing account.");
                return new Account(existingAccountId, username, password);
            }
        
            // User not found, proceed with registration
            //System.out.println("Registering new user.");
            String insertUserSQL = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement insertStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();
        
            // Get the newly generated account ID
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newAccountId = generatedKeys.getInt(1);
                return new Account(newAccountId, username, password);
            }
        
            return null;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account user_login(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        Connection connection = ConnectionUtil.getConnection();
        try{
            if (password.length() < 4) {
                return null; // Short password check before DB operations
            }
            
            // Check if user exists
            String sql = "SELECT account_id FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet loginResultSet = preparedStatement.executeQuery();
            
            if (loginResultSet.next()) {
                // User exists, update the account object
                account.setAccount_id(loginResultSet.getInt("account_id"));
            }
            
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return account;
    }
}
