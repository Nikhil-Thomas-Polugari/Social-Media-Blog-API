package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.*;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO(); 
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    /*
    public boolean registered_user(String username){
        return accountDAO.registered_user(username);
    }
    */

    public List<Account> registered_accounts(){
        return accountDAO.all_users();
    }

    public Account user_registration(Account account){
        return accountDAO.user_registration(account);
    }

    public Account user_login(Account account){
        return accountDAO.user_login(account);
    }
}
