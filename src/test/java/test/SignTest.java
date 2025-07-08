package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import javafx.application.Platform;
import model.Model;
import dao.mappers.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import dao.mappers.UserMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(ApplicationExtension.class)
public class SignTest {

    private UserMapper userMapper;
    private Model model;

    /**
     * Initializes test environment before each test execution.
     * <ul>
     *   <li>Creates mock UserMapper</li>
     *   <li>Instantiates Model</li>
     *   <li>Injects mock UserMapper into Model using reflection</li>
     *   <li>Configures mock database behavior</li>
     * </ul>
     *
     * @throws Exception if reflection injection fails
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Create mock UserMapper
        userMapper = mock(UserMapper.class);

        // Create real Model instance
        model = new Model();

        // Inject mock UserMapper using reflection
        Field userMapperField = Model.class.getDeclaredField("userMapper");
        userMapperField.setAccessible(true);
        userMapperField.set(model, userMapper);

        // Configure mock database behavior
        when(userMapper.getUserByName("qwe")).thenReturn(new User());
        when(userMapper.getUserByName("ezra123")).thenReturn(null);
    }

    /**
     * Test Case 1: Valid registration (EC V1, V6, V11).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (not in database)</li>
     *   <li>Password: "pass1234" (valid format)</li>
     * </ul>
     * Expected: Registration successful (returns true)
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testSignUpValid() throws Exception {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() ->
                future.complete(model.sign("ezra123", "pass1234")));
        assertTrue(future.get());
    }

    /**
     * Test Case 3: Username already exists (EC V3).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "qwe" (exists in database)</li>
     *   <li>Password: "pass1234" (any valid password)</li>
     * </ul>
     * Expected: Registration fails (returns false)
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testSignUpUsernameExists() throws Exception {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() ->
                future.complete(model.sign("qwe", "pass1234")));
        assertFalse(future.get());
    }
}