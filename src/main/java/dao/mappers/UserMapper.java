package dao.mappers;

import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    
    void addUser(@Param("user") User user);

    
    boolean deleteUser(@Param("user") User user);

    
    boolean updateUser(@Param("user") User user);

    
    User getUserById(@Param("id") int userId);

    
    User getUserByName(@Param("name") String name);

}
