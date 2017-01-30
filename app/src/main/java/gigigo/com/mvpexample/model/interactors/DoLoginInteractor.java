package gigigo.com.mvpexample.model.interactors;

import com.gigigo.interactorexecutor.interactors.Interactor;
import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.interactors.errors.NetworkInteractorError;
import gigigo.com.mvpexample.model.mappers.UserMapper;
import gigigo.com.mvpexample.model.networks.NetworkDataSource;
import gigigo.com.mvpexample.model.networks.vto.ApiUser;

public class DoLoginInteractor implements Interactor<InteractorResponse<User>> {

  private final NetworkDataSource networkDataSource;
  private final UserMapper userMapper;

  private String email;
  private String password;

  public DoLoginInteractor(NetworkDataSource networkDataSource, UserMapper userMapper) {
   this.networkDataSource = networkDataSource;
   this.userMapper = userMapper;
  }

  @Override public InteractorResponse<User> call() throws Exception {

    ApiUser apiUser = networkDataSource.doLogin(email, password);

    if (apiUser == null) {
      return new InteractorResponse<>(new NetworkInteractorError());
    }

    User user = userMapper.mapFromDataSourceToDomain(apiUser);

    return new InteractorResponse<>(user);
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
