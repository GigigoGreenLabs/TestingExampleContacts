package gigigo.com.mvpexample.presentation;

import android.support.annotation.NonNull;
import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.interactors.DoLoginInteractor;
import gigigo.com.mvpexample.model.interactors.errors.NetworkInteractorError;
import gigigo.com.mvpexample.model.mappers.UserMapper;
import gigigo.com.mvpexample.model.networks.ContactServiceFake;
import gigigo.com.mvpexample.model.networks.ContactsService;
import gigigo.com.mvpexample.model.networks.NetworkDataSource;
import gigigo.com.mvpexample.model.validators.CredentialsChecker;
import gigigo.com.mvpexample.presentation.utils.TestInteractorInvoker;
import gigigo.com.mvpexample.presentation.utils.TestViewInjector;
import java.util.concurrent.CountDownLatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class MainPresenterTest {

  @Mock PresenterView presenterView;

  @NonNull private MainPresenter getFakePresenter(int codeServer) throws Exception {
    String fakeResponse = "{\"results\": [{\"gender\": \"male\",\"name\": {\"title\": \"mr\",\"first\": \"romain\",\"last\": \"hoogmoed\"},\"location\": {\"street\": \"1861 jan pieterszoon coenstraat\",\"city\": \"maasdriel\",\"state\": \"zeeland\",\"postcode\": 69217},\"email\": \"romain.hoogmoed@example.com\",\"login\":{\"username\":\"lazyduck408\",\"password\":\"jokers\",\"salt\":\"UGtRFz4N\",\"md5\":\"6d83a8c084731ee73eb5f9398b923183\",\"sha1\":\"cb21097d8c430f2716538e365447910d90476f6e\",\"sha256\":\"5a9b09c86195b8d8b01ee219d7d9794e2abb6641a2351850c49c309f1fc204a0\"},\"dob\": \"1983-07-14 07:29:45\",\"registered\": \"2010-09-24 02:10:42\",\"phone\": \"(656)-976-4980\",\"cell\": \"(065)-247-9303\",\"id\": {\"name\": \"BSN\",\"value\": \"04242023\"},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/men/83.jpg\",      \"medium\":\"https://randomuser.me/api/portraits/med/men/83.jpg\",\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/83.jpg\"},\"nat\": \"NL\"}],\"info\": {\"seed\": \"2da87e9305069f1d\",\"results\": 1,\"page\": 1,\"version\": \"1.1\"}}";

    ContactsService contactsService = new ContactServiceFake().retrieveContacts(fakeResponse, codeServer);

    NetworkDataSource networkDataSource =
        new NetworkDataSource(contactsService);

    DoLoginInteractor doLoginInteractor = new DoLoginInteractor(networkDataSource, new UserMapper());

    return new MainPresenter(new TestViewInjector(), new CredentialsChecker(), doLoginInteractor,
        TestInteractorInvoker.create());
  }

  @Test public void shouldShowDataWhenDoLoginRequestIsSuccessful() throws Exception {
    final CountDownLatch countDownLatch = new CountDownLatch(1);

    //Arrange
    MainPresenter fakePresenter = getFakePresenter(200);

    fakePresenter.attachView(presenterView);

    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        countDownLatch.countDown();
        return null;
      }
    }).when(presenterView).showLoginSuccessful(any(User.class));


    //Act
    fakePresenter.doLogin("email@hotmail.com", "asf1234");

    countDownLatch.await();


    //Assert
    verify(presenterView).showLoginSuccessful(any(User.class));

    fakePresenter.detachView(presenterView);
  }

  @Test public void shouldShowLoginFailedWhenServerFailed() throws Exception {
    final CountDownLatch countDownLatch = new CountDownLatch(1);

    //Arrange
    MainPresenter fakePresenter = getFakePresenter(500);
    fakePresenter.attachView(presenterView);

    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        countDownLatch.countDown();
        return null;
      }
    }).when(presenterView).showLoginFailed();

    //Act
    fakePresenter.doLogin("email@hotmail.com", "asf1234");

    countDownLatch.await();

    verify(presenterView).showLoginFailed();

    fakePresenter.detachView(presenterView);
  }

  @Test public void shouldShowValidationErrorWhenEmailIsNotValid() throws Exception {
    //Arrange
    MainPresenter fakePresenter = getFakePresenter(200);
    fakePresenter.attachView(presenterView);

    //Act
    fakePresenter.doLogin("email@.com", "1213aa");

    //Assert
    verify(presenterView).showValidationUserErrorView();

    fakePresenter.detachView(presenterView);
  }

  @Test public void shouldShowValidationErrorWhenPasswordIsNotValid() throws Exception {
    //Arrange
    MainPresenter fakePresenter = getFakePresenter(200);
    fakePresenter.attachView(presenterView);

    //Act
    fakePresenter.doLogin("email@hotmail.com", "");

    //Assert
    verify(presenterView).showValidationUserErrorView();

    fakePresenter.detachView(presenterView);
  }

  //@Test public void shouldShowDataWhenDoLoginRequestIsSuccessful1() throws Exception {
  //  DoLoginInteractor mockDoLoginInteractor = mock(DoLoginInteractor.class);
  //
  //  MainPresenter presenter = new MainPresenter(new TestViewInjector(), new CredentialsChecker(), mockDoLoginInteractor,
  //      TestInteractorInvoker.create());
  //
  //  presenter.attachView(presenterView);
  //
  //  when(mockDoLoginInteractor.call()).thenReturn(new InteractorResponse<>(new User()));
  //
  //  presenter.doLogin("email@hotmail.com", "asf1234");
  //
  //  verify(presenterView).showLoginSuccessful(any(User.class));
  //}

  //@Test public void shouldShowLoginFailedWhenServerFailed1() throws Exception {
  //  DoLoginInteractor mockDoLoginInteractor = mock(DoLoginInteractor.class);
  //
  //  MainPresenter presenter = new MainPresenter(new TestViewInjector(), new CredentialsChecker(), mockDoLoginInteractor,
  //      TestInteractorInvoker.create());
  //
  //  presenter.attachView(presenterView);
  //
  //  InteractorResponse<User> networkInteractorError =
  //      new InteractorResponse<>(new NetworkInteractorError());
  //
  //  when(mockDoLoginInteractor.call()).thenReturn(networkInteractorError);
  //
  //  presenter.doLogin("email@hotmail.com", "asf1234");
  //
  //  verify(presenterView).showLoginFailed();
  //}
}