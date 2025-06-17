package chatapp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class LoginTest {
    
    public LoginTest() {
    }
    private Login login = new Login();
    @Test
    //Test for username
    public void testValidUsername() {
        assertTrue(login.checkUserName("kyl_1"));
    }
    @Test
    public void testInvalidUserName() {
        assertFalse(login.checkUserName("kyle!!!!!"));
    }
    @Test
    //Test for password complexity
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    @Test
    public void testInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }
    
    @Test
    //Test for SA phone number
    public void testValidPhoneNumber() {
        assertTrue(login.checkPhoneNumber("+27838968976"));
    }
    @Test
    public void testInvalidPhoneNumber() {
        assertFalse(login.checkPhoneNumber("08966553"));
    }
    
    @Test
    //Tests for loginUser() method
    public void testSuccessLogin() {
        login.registerUser("Test", "User", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test
    public void testFailedLogin() {
        login.registerUser("Test", "User", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyle!!!!!", "password"));
    }
    
    @Test
    //Tests for returnLoginStatus() method
    public void testLoginStatusMessages() {
        login.registerUser("Test", "User", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
    
    //TRUE login
    boolean isLoggedIn = login.loginUser("kyl_1", "Ch&&sec@ke99!");
    String successMsg = login.returnLoginStatus(isLoggedIn);
    assertTrue(successMsg.contains("Welcome Test, User"));
    
    //FALSE login
    isLoggedIn = login.loginUser("kyle!!!!!!", "password");
    String failMsg = login.returnLoginStatus(isLoggedIn);
    assertTrue(failMsg.contains("Incorrect"));    
    
  }
}
