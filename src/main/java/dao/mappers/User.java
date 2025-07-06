package dao.mappers;
import org.apache.ibatis.type.Alias;
import java.io.Serializable;
import java.util.Random;


@Alias("User")
public class User implements Serializable {

    private int user_id;
    private String user_name;
    private String password;

    
    public User(){
    }

    
    public User(int user_id, String user_name, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
    }

    
    public int getUserId() {
        return user_id;
    }

    

    public void setUser(String userName, String password) {
        this.user_name = userName;
        this.password = password;
        this.user_id = 10000000 + new Random().nextInt(90000000);
    }

    
    public String getUserName() {
        return user_name;
    }

    
    public String getPassword() {
        return password;
    }


    
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
