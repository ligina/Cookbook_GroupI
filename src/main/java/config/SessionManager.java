package config;

/**
 * Session management utility class for handling user session data.
 * This class provides static methods to manage the current logged-in user's information.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class SessionManager {

    /** The username of the currently logged-in user */
    private static String currentUserName;

    /**
     * Sets the current user's username for the session.
     * 
     * @param userName the username to set for the current session
     */
    public static void setCurrentUserName(String userName) {
        currentUserName = userName;
    }

    /**
     * Gets the current user's username from the session.
     * 
     * @return the username of the currently logged-in user, or null if no user is logged in
     */
    public static String getCurrentUserName() {
        return currentUserName;
    }
}
