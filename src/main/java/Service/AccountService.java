package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO(); 
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    public Account user_registration(String username, String password){
        return accountDAO.user_registration(username, password);
    }

    public Account user_login(String username, String password){
        return accountDAO.user_login(username, password);
    }
}
