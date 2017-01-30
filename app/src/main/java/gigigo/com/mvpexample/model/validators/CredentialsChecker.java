package gigigo.com.mvpexample.model.validators;

import android.text.TextUtils;
import android.util.Patterns;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsChecker {

  public boolean isAValidEmail(String email) {
    if (email == null || email.isEmpty()) {
      return false;
    } else {
      String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
      java.util.regex.Pattern p = java.util.regex.Pattern.compile(EMAIL_PATTERN);
      java.util.regex.Matcher m = p.matcher(email);
      return m.matches();
    }
  }

  public boolean isAValidPassword(String password) {
    if (password == null || password.isEmpty()) {
      return false;
    } else {
      Pattern pattern;
      Matcher matcher;
      final String PASSWORD_PATTERN = "[A-Za-z0-9]+";
      pattern = Pattern.compile(PASSWORD_PATTERN);
      matcher = pattern.matcher(password);

      return matcher.matches();
    }
  }
}
