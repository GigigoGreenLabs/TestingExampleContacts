package gigigo.com.mvpexample.model.networks;

import gigigo.com.mvpexample.model.networks.vto.ApiResponse;
import gigigo.com.mvpexample.model.networks.vto.ApiUser;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class NetworkDataSource {

  private final ContactsService contactsService;

  public NetworkDataSource(ContactsService contactsService) {
    this.contactsService = contactsService;
  }

  public ApiUser doLogin(String email, String password) {
    try {
      Call<ApiResponse> apiUserCaller = contactsService.retrieveContacts();

      Response<ApiResponse> apiUserResponse = apiUserCaller.execute();

      if (apiUserResponse.isSuccessful()) {
        return apiUserResponse.body().getResults().get(0);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }
}
