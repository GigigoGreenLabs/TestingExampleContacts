package gigigo.com.mvpexample.presentation;

import com.gigigo.interactorexecutor.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.base.invoker.InteractorInvoker;
import com.gigigo.interactorexecutor.base.invoker.InteractorResult;
import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.interactors.DoLoginInteractor;
import gigigo.com.mvpexample.model.interactors.errors.NetworkInteractorError;
import gigigo.com.mvpexample.model.validators.CredentialsChecker;
import gigigo.com.mvpexample.presentation.base.Presenter;
import gigigo.com.mvpexample.presentation.base.viewInjector.GenericViewInjector;

public class MainPresenter extends Presenter<PresenterView> {

  private CredentialsChecker credentialsChecker;
  private DoLoginInteractor doLoginInteractor;
  private InteractorInvoker interactorInvoker;

  public MainPresenter(GenericViewInjector genericViewInjector,
      CredentialsChecker credentialsChecker, DoLoginInteractor doLoginInteractor,
      InteractorInvoker interactorInvoker) {
    super(genericViewInjector);

    this.credentialsChecker = credentialsChecker;
    this.doLoginInteractor = doLoginInteractor;
    this.interactorInvoker = interactorInvoker;
  }

  @Override public void onViewAttached() {
    getView().initUi();
  }

  public void doLogin(String email, String password) {
    boolean validEmail = credentialsChecker.isAValidEmail(email);

    boolean validPassword = credentialsChecker.isAValidPassword(password);

    if (validEmail && validPassword) {
      loginUser(email, password);
    } else {
      getView().showValidationUserErrorView();
    }
  }

  private void loginUser(String email, String password) {
    getView().showProgress(true);

    doLoginInteractor.setEmail(email);
    doLoginInteractor.setPassword(password);

    new InteractorExecution<>(doLoginInteractor).result(new InteractorResult<User>() {
      @Override public void onResult(User user) {
        getView().showLoginSuccessful(user);
        getView().showProgress(false);
      }
    }).error(NetworkInteractorError.class, new InteractorResult<NetworkInteractorError>() {
      @Override public void onResult(NetworkInteractorError interactorError) {
        getView().showLoginFailed();
        getView().showProgress(false);
      }
    }).execute(interactorInvoker);
  }
}
