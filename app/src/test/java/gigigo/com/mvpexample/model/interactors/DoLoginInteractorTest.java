package gigigo.com.mvpexample.model.interactors;

import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.interactors.errors.NetworkInteractorError;
import gigigo.com.mvpexample.model.mappers.UserMapper;
import gigigo.com.mvpexample.model.networks.ContactServiceFake;
import gigigo.com.mvpexample.model.networks.ContactsService;
import gigigo.com.mvpexample.model.networks.NetworkDataSource;
import gigigo.com.mvpexample.model.networks.vto.ApiId;
import gigigo.com.mvpexample.model.networks.vto.ApiLocation;
import gigigo.com.mvpexample.model.networks.vto.ApiName;
import gigigo.com.mvpexample.model.networks.vto.ApiPicture;
import gigigo.com.mvpexample.model.networks.vto.ApiUser;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class DoLoginInteractorTest {

  @Test public void shouldReturnNetworkInteractorErrorWhenAServerErrorIsProduced()
      throws Exception {

    String fakeResponse = "{\"results\": [{\"gender\": \"male\",\"name\": {\"title\": \"mr\",\"first\": \"romain\",\"last\": \"hoogmoed\"},\"location\": {\"street\": \"1861 jan pieterszoon coenstraat\",\"city\": \"maasdriel\",\"state\": \"zeeland\",\"postcode\": 69217},\"email\": \"romain.hoogmoed@example.com\",\"login\":{\"username\":\"lazyduck408\",\"password\":\"jokers\",\"salt\":\"UGtRFz4N\",\"md5\":\"6d83a8c084731ee73eb5f9398b923183\",\"sha1\":\"cb21097d8c430f2716538e365447910d90476f6e\",\"sha256\":\"5a9b09c86195b8d8b01ee219d7d9794e2abb6641a2351850c49c309f1fc204a0\"},\"dob\": \"1983-07-14 07:29:45\",\"registered\": \"2010-09-24 02:10:42\",\"phone\": \"(656)-976-4980\",\"cell\": \"(065)-247-9303\",\"id\": {\"name\": \"BSN\",\"value\": \"04242023\"},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/men/83.jpg\",      \"medium\":\"https://randomuser.me/api/portraits/med/men/83.jpg\",\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/83.jpg\"},\"nat\": \"NL\"}],\"info\": {\"seed\": \"2da87e9305069f1d\",\"results\": 1,\"page\": 1,\"version\": \"1.1\"}}";

    ContactsService contactsService = new ContactServiceFake().retrieveContacts(fakeResponse, 500);

    NetworkDataSource networkDataSource =
        new NetworkDataSource(contactsService);

    DoLoginInteractor interactor = new DoLoginInteractor(networkDataSource, new UserMapper());

    interactor.setEmail("aaa@hotmail.com");
    interactor.setPassword("12asda");

    InteractorResponse<User> interactorResponse = interactor.call();

    assertTrue(interactorResponse.hasError());
    assertThat(interactorResponse.getError(), instanceOf(NetworkInteractorError.class));
  }

  @Test public void shouldReturnAUserWhenAValidEmailAndPasswordIsPassedAsParams() throws Exception {
    String fakeResponse = "{\"results\": [{\"gender\": \"male\",\"name\": {\"title\": \"mr\",\"first\": \"romain\",\"last\": \"hoogmoed\"},\"location\": {\"street\": \"1861 jan pieterszoon coenstraat\",\"city\": \"maasdriel\",\"state\": \"zeeland\",\"postcode\": 69217},\"email\": \"romain.hoogmoed@example.com\",\"login\":{\"username\":\"lazyduck408\",\"password\":\"jokers\",\"salt\":\"UGtRFz4N\",\"md5\":\"6d83a8c084731ee73eb5f9398b923183\",\"sha1\":\"cb21097d8c430f2716538e365447910d90476f6e\",\"sha256\":\"5a9b09c86195b8d8b01ee219d7d9794e2abb6641a2351850c49c309f1fc204a0\"},\"dob\": \"1983-07-14 07:29:45\",\"registered\": \"2010-09-24 02:10:42\",\"phone\": \"(656)-976-4980\",\"cell\": \"(065)-247-9303\",\"id\": {\"name\": \"BSN\",\"value\": \"04242023\"},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/men/83.jpg\",      \"medium\":\"https://randomuser.me/api/portraits/med/men/83.jpg\",\"thumbnail\": \"https://randomuser.me/api/portraits/thumb/men/83.jpg\"},\"nat\": \"NL\"}],\"info\": {\"seed\": \"2da87e9305069f1d\",\"results\": 1,\"page\": 1,\"version\": \"1.1\"}}";

    ContactsService contactsService = new ContactServiceFake().retrieveContacts(fakeResponse, 200);

    NetworkDataSource networkDataSource =
        new NetworkDataSource(contactsService);

    DoLoginInteractor interactor = new DoLoginInteractor(networkDataSource, new UserMapper());

    interactor.setEmail("aaa@hotmail.com");
    interactor.setPassword("12asda");

    InteractorResponse<User> interactorResponse = interactor.call();

    assertFalse(interactorResponse.hasError());
    assertEquals("male", interactorResponse.getResult().getGender());
    assertEquals("(656)-976-4980", interactorResponse.getResult().getPhone());
    assertEquals("2010-09-24 02:10:42", interactorResponse.getResult().getRegistered());

    assertEquals("BSN", interactorResponse.getResult().getId().getName());
    assertEquals("04242023", interactorResponse.getResult().getId().getValue());

    assertEquals("mr", interactorResponse.getResult().getName().getTitle());
    assertEquals("romain", interactorResponse.getResult().getName().getFirst());
    assertEquals("hoogmoed", interactorResponse.getResult().getName().getLast());

    assertEquals("1861 jan pieterszoon coenstraat", interactorResponse.getResult().getLocation().getStreet());
    assertEquals("maasdriel", interactorResponse.getResult().getLocation().getCity());
    assertEquals("zeeland", interactorResponse.getResult().getLocation().getState());
    assertEquals(69217, interactorResponse.getResult().getLocation().getPostcode());

    assertEquals("https://randomuser.me/api/portraits/men/83.jpg", interactorResponse.getResult().getPicture().getLarge());
    assertEquals("https://randomuser.me/api/portraits/med/men/83.jpg", interactorResponse.getResult().getPicture().getMedium());
    assertEquals("https://randomuser.me/api/portraits/thumb/men/83.jpg", interactorResponse.getResult().getPicture().getThumbnail());
  }

  @Test public void shoulReturnNetworkErrordWhenAnExceptionIsProduced() throws Exception {

    ContactsService mockContactsService = Mockito.mock(ContactsService.class);

    NetworkDataSource networkDataSource = new NetworkDataSource(mockContactsService);

    DoLoginInteractor interactor = new DoLoginInteractor(networkDataSource, new UserMapper());

    when(mockContactsService.retrieveContacts()).thenThrow(IOException.class);

    InteractorResponse<User> response = interactor.call();

    assertTrue(response.hasError());
    assertThat(response.getError(), instanceOf(NetworkInteractorError.class));
    assertThat(response.getError().getError().getMessage(), is(""));
  }
}