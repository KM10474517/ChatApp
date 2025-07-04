package chatapp;

import static org.junit.Assert.*;
import org.junit.Test;

public class LoginTest {

    // Username tests (from first image)
    @Test
    public void testUsernameCorrectFormat() {
        assertTrue(Login.checkUsername("kyl_1"));
    }
    
    @Test 
    public void testUsernameWrongFormat() {
        assertFalse(Login.checkUsername("kylell!!!!!"));
    }

    // Password tests (from second image)
    @Test
    public void testPasswordMeetsRequirements() {
        assertTrue(Login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testPasswordFailsRequirements() {
        assertFalse(Login.checkPasswordComplexity("password"));
    }

    // Cellphone tests (from third image)
    @Test
    public void testCellphoneCorrectFormat() {
        assertTrue(Login.checkCellPhoneNumber("+27838968976"));
    }
    
    @Test
    public void testCellphoneWrongFormat() {
        assertFalse(Login.checkCellPhoneNumber("08966553"));
    }

    // Boolean result tests (from fourth image)
    @Test
    public void testUsernameFormatBoolean() {
        assertTrue(Login.checkUsername("valid_1"));
        assertFalse(Login.checkUsername("invalid"));
    }
    
    @Test
    public void testPasswordComplexityBoolean() {
        assertTrue(Login.checkPasswordComplexity("Valid1!"));
        assertFalse(Login.checkPasswordComplexity("invalid"));
    }
    
    @Test
    public void testCellphoneFormatBoolean() {
        assertTrue(Login.checkCellPhoneNumber("+27831234567"));
        assertFalse(Login.checkCellPhoneNumber("0831234567"));
    }
}
