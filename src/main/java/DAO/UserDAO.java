package DAO;

/**
 * Created by Amir Shams on 9/20/2016.
 */
public class UserDAO {

    private static UserDAO instance = new UserDAO();

    private UserDAO(){}

    public static UserDAO getInstance()
    {
        return instance;
    }

    public boolean searchUser(long chatID)
    {
        return true;
    }
}
