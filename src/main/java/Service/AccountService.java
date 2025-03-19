package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.*;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO(); 
    }

    public List<Account> registered_users(){
        return accountDAO.all_users();
    }

    public boolean registered_user(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        return accountDAO.registered_user(username, password);
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account user_registration(Account account){
        return accountDAO.user_registration(account);
    }

    public Account user_login(Account account){
        return accountDAO.user_login(account);
    }
}
