package gigigo.com.mvpexample.model.networks;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactServiceFake {

  public ContactsService retrieveContacts(String fakeResponse, int serverCode) throws Exception {

    MockWebServer mockWebServer = new MockWebServer();
    mockWebServer.start();
    mockWebServer.enqueue(new MockResponse().setBody(fakeResponse)
        .setResponseCode(serverCode));

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    OkHttpClient okHttpClient = okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS).
        readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(mockWebServer.url("/").toString())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    ContactsService contactsService = retrofit.create(ContactsService.class);

    //mockWebServer.shutdown();

    return contactsService;

  }
}
