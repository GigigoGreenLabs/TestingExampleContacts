package gigigo.com.mvpexample.model.validators;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class) public class CredentialsCheckerPasswordTest {

  private CredentialsChecker credentialsChecker;

  @Parameterized.Parameters public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { "pass1234", true },
        { "pass1234+", false },
        { "", false },
        { null, false },
    });
  }

  @Parameterized.Parameter public String password;

  @Parameterized.Parameter(value = 1) public boolean expectedValue;

  @Before public void setUp() throws Exception {
    credentialsChecker = new CredentialsChecker();
  }

  @Test public void shouldVerifyIfAPasswordIsValid() throws Exception {
    boolean isValidEmail = credentialsChecker.isAValidPassword(password);

    assertEquals(expectedValue, isValidEmail);
  }
}