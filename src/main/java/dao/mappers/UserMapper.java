package dao.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis mapper interface for User entity operations.
 * This interface defines database operations for user management including
 * CRUD operations and user authentication queries.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public interface UserMapper {

    /**
     * Adds a new user to the database.
     * 
     * @param user the User object to be added to the database
     */
    void addUser(@Param("user") User user);

    /**
     * Deletes a user from the database.
     * 
     * @param user the User object to be deleted from the database
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteUser(@Param("user") User user);

    /**
     * Updates an existing user in the database.
     * 
     * @param user the User object with updated information
     * @return true if the update was successful, false otherwise
     */
    boolean updateUser(@Param("user") User user);

    /**
     * Retrieves a user by their unique ID.
     * 
     * @param userId the unique identifier of the user
     * @return the User object if found, null otherwise
     */
    User getUserById(@Param("id") int userId);

    /**
     * Retrieves a user by their username.
     * This method is commonly used for user authentication.
     * 
     * @param name the username to search for
     * @return the User object if found, null otherwise
     */
    User getUserByName(@Param("name") String name);

}
