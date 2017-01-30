package gigigo.com.mvpexample.model.networks;

import gigigo.com.mvpexample.model.networks.vto.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ContactsService {

    @GET("api/") Call<ApiResponse> retrieveContacts();

}
