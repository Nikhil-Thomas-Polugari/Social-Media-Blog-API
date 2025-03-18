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

    public Account user_registration(Account account){
        return accountDAO.user_registration(account);
    }

    public Account user_login(Account account){
        return accountDAO.user_login(account);
    }
}
