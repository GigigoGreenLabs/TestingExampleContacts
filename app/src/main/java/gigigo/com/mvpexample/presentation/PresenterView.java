package gigigo.com.mvpexample.presentation;

import gigigo.com.mvpexample.model.entities.User;
import me.panavtec.threaddecoratedview.views.qualifiers.NotDecorated;
import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView
public interface PresenterView {

  @NotDecorated
  void initUi();

  void showValidationUserErrorView();

  void showProgress(boolean visible);

  void showLoginSuccessful(User user);

  void showLoginFailed();
}
