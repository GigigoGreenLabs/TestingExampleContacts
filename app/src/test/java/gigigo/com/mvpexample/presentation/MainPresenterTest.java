package gigigo.com.mvpexample.presentation;

import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.interactors.DoLoginInteractor;
import gigigo.com.mvpexample.model.interactors.errors.NetworkInteractorError;
import gigigo.com.mvpexample.model.validators.CredentialsChecker;
import gigigo.com.mvpexample.presentation.utils.TestInteractorInvoker;
import gigigo.com.mvpexample.presentation.utils.TestViewInjector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class MainPresenterTest {

  @Mock DoLoginInteractor doLoginInteractor;
  @Mock PresenterView presenterView;

  MainPresenter presenter;

  @Before public void setUp() throws Exception {
    presenter =
        new MainPresenter(new TestViewInjector(), new CredentialsChecker(), doLoginInteractor,
            TestInteractorInvoker.create());

    presenter.attachView(presenterView);
  }

  @Test public void shouldShowDataWhenDoLoginRequestIsSuccessful() throws Exception {
    when(doLoginInteractor.call()).thenReturn(new InteractorResponse<>(new User()));

    presenter.doLogin("email@hotmail.com", "asf1234");

    verify(presenterView).showLoginSuccessful(any(User.class));
  }

  @Test public void shouldShowValidationErrorWhenEmailIsNotValid() throws Exception {
    presenter.doLogin("email@.com", "1213aa");

    verify(presenterView).showValidationUserErrorView();
  }

  @Test public void shouldShowValidationErrorWhenPasswordIsNotValid() throws Exception {
    presenter.doLogin("email@hotmail.com", "");

    verify(presenterView).showValidationUserErrorView();
  }

  @Test public void shouldShowLoginFailedWhenServerFailed() throws Exception {
    InteractorResponse<User> networkInteractorError =
        new InteractorResponse<>(new NetworkInteractorError());

    when(doLoginInteractor.call()).thenReturn(networkInteractorError);

    presenter.doLogin("email@hotmail.com", "asf1234");

    verify(presenterView).showLoginFailed();
  }

  @After public void tearDown() throws Exception {
    presenter.detachView(presenterView);
  }
}