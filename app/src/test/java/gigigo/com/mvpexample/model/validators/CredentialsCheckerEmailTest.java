package gigigo.com.mvpexample.model.validators;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) public class CredentialsCheckerEmailTest {

  private CredentialsChecker credentialsChecker;

  @Parameterized.Parameters public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { "aaa@hotmail.com", true },
        { "@hotmail.com", false },
        { "a@.com", false },
        { "a@hotmail.", false },
        { "", false },
        { null, false },
    });
  }

  @Parameterized.Parameter public String email;

  @Parameterized.Parameter(value = 1) public boolean expectedValue;

  @Before public void setUp() throws Exception {
    credentialsChecker = new CredentialsChecker();
  }

  @Test public void shouldVerifyIfAEmailIsValid() throws Exception {
    boolean isValidEmail = credentialsChecker.isAValidEmail(email);

    assertEquals(expectedValue, isValidEmail);
  }
}