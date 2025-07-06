package dao.mappers;
import org.apache.ibatis.type.Alias;
import java.io.Serializable;
import java.util.Random;

/**
 * User entity class representing a user in the recipe management system.
 * This class is mapped to the database user table and contains user authentication information.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
@Alias("User")
public class User implements Serializable {

    /** Unique identifier for the user */
    private int user_id;
    
    /** Username for the user account */
    private String user_name;
    
    /** Password for the user account */
    private String password;

    /**
     * Default constructor for User.
     * Creates an empty User object.
     */
    public User(){
    }

    /**
     * Parameterized constructor for User.
     * 
     * @param user_id the unique identifier for the user
     * @param user_name the username for the account
     * @param password the password for the account
     */
    public User(int user_id, String user_name, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
    }

    /**
     * Gets the user ID.
     * 
     * @return the unique identifier of the user
     */
    public int getUserId() {
        return user_id;
    }

    /**
     * Sets the user information including username and password.
     * Also generates a random user ID between 10000000 and 99999999.
     * 
     * @param userName the username to set
     * @param password the password to set
     */
    public void setUser(String userName, String password) {
        this.user_name = userName;
        this.password = password;
        // Generate a random 8-digit user ID
        this.user_id = 10000000 + new Random().nextInt(90000000);
    }

    /**
     * Gets the username.
     * 
     * @return the username of the user
     */
    public String getUserName() {
        return user_name;
    }

    /**
     * Gets the password.
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     * 
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the User object.
     * 
     * @return a string containing user ID, username, and password
     */
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
