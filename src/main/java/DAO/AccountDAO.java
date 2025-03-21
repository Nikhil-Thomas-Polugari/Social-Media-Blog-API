package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {

    public List<Account> all_users(){
        List<Account> registered_users = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet loginResultSet = preparedStatement.executeQuery();
            if (loginResultSet.next()){
                Account account = new Account(loginResultSet.getInt(1), loginResultSet.getString(2), loginResultSet.getString(3));
                registered_users.add(account);
                while(loginResultSet.next()){
                    Account account2 = new Account(loginResultSet.getInt(1), loginResultSet.getString(2), loginResultSet.getString(3));
                    registered_users.add(account2);
                }
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return registered_users;
    }
    
    public boolean registered_user(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND  password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2,password);
            ResultSet loginResultSet = preparedStatement.executeQuery();
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
        Connection connection = ConnectionUtil.getConnection();
        try{
            if (username == null || username.isEmpty() || password.length() < 4) {
                return null;
            }
            String checkUserSQL = "SELECT account_id FROM account WHERE username = ?;";
            PreparedStatement checkStmt = connection.prepareStatement(checkUserSQL);
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();
            
            if (resultSet.next()) {
                return null;
            }

            String insertUserSQL = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement insertStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();
        
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
                return null; // Check for short passwords before querying the DB
            }
            
            String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet loginResultSet = preparedStatement.executeQuery();
            
            if (loginResultSet.next()) {
                account.setAccount_id(loginResultSet.getInt("account_id"));
            } else {
                return null;
            }            
            
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return account;
    }
}
