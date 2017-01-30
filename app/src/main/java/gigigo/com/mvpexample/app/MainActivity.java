package gigigo.com.mvpexample.app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.gigigo.interactorexecutor.invoker.InteractorInvokerImp;
import com.gigigo.interactorexecutor.invoker.InteractorOutputThreadFactory;
import com.gigigo.interactorexecutor.invoker.InteractorPriorityBlockingQueue;
import com.gigigo.interactorexecutor.invoker.LogExceptionHandler;
import com.gigigo.interactorexecutor.invoker.PriorizableThreadPoolExecutor;
import gigigo.com.mvpexample.R;
import gigigo.com.mvpexample.model.entities.Location;
import gigigo.com.mvpexample.model.entities.Name;
import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.interactors.DoLoginInteractor;
import gigigo.com.mvpexample.model.mappers.UserMapper;
import gigigo.com.mvpexample.model.networks.ContactsService;
import gigigo.com.mvpexample.model.networks.NetworkDataSource;
import gigigo.com.mvpexample.model.validators.CredentialsChecker;
import gigigo.com.mvpexample.presentation.MainPresenter;
import gigigo.com.mvpexample.presentation.PresenterView;
import gigigo.com.mvpexample.presentation.base.viewInjector.MainThreadSpec;
import gigigo.com.mvpexample.presentation.base.viewInjector.ThreadViewInjector;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PresenterView {

  private static final int CONCURRENT_INTERACTORS = 3;

  private EditText emailEditText;
  private EditText passwordEditText;
  private Button loginButton;
  private View progress;

  private View userLayout;

  private MainPresenter presenter;
  private TextView textviewGender;
  private TextView textviewName;
  private TextView textviewLocation;
  private TextView textviewPhone;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initInjections();

    presenter.attachView(this);
  }

  private void initInjections() {
    MainThreadSpec mainThreadSpec = new MainThreadSpec();
    ThreadViewInjector threadViewInjector = new ThreadViewInjector(mainThreadSpec);

    CredentialsChecker credentialsChecker = new CredentialsChecker();

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    okHttpClientBuilder.addInterceptor(loggingInterceptor);

    OkHttpClient okHttpClient = okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS).
        readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://randomuser.me/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    ContactsService contactsService = retrofit.create(ContactsService.class);

    NetworkDataSource networkDataSource = new NetworkDataSource(contactsService);

    UserMapper userMapper = new UserMapper();

    DoLoginInteractor doLoginInteractor = new DoLoginInteractor(networkDataSource, userMapper);
    InteractorPriorityBlockingQueue blockingQueue = new InteractorPriorityBlockingQueue(100);
    InteractorOutputThreadFactory threadFactory = new InteractorOutputThreadFactory();
    PriorizableThreadPoolExecutor executorService =
        new PriorizableThreadPoolExecutor(CONCURRENT_INTERACTORS, CONCURRENT_INTERACTORS, 0L,
            TimeUnit.MILLISECONDS, blockingQueue, threadFactory);
    InteractorInvokerImp interactorInvoker =
        new InteractorInvokerImp(executorService, new LogExceptionHandler());

    presenter = new MainPresenter(threadViewInjector, credentialsChecker, doLoginInteractor,
        interactorInvoker);
  }

  @Override public void initUi() {
    initViews();
    setListeners();
  }

  private void initViews() {
    emailEditText = (EditText) findViewById(R.id.emailEditText);
    passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    loginButton = (Button) findViewById(R.id.loginButton);
    progress = findViewById(R.id.progress);

    userLayout = findViewById(R.id.userLayout);
    textviewName = (TextView) findViewById(R.id.textviewName);
    textviewLocation = (TextView) findViewById(R.id.textviewLocation);
    textviewGender = (TextView) findViewById(R.id.textviewGender);
    textviewPhone = (TextView) findViewById(R.id.textviewPhone);
  }

  private void setListeners() {
    loginButton.setOnClickListener(onClickLoginButton);
  }

  private View.OnClickListener onClickLoginButton = new View.OnClickListener() {
    @Override public void onClick(View view) {
      String email = emailEditText.getText().toString();
      String password = passwordEditText.getText().toString();

      presenter.doLogin(email, password);
    }
  };

  @Override public void showValidationUserErrorView() {
    Snackbar.make(loginButton, "Email o Password en formato incorrecto", Snackbar.LENGTH_LONG)
        .show();
  }

  @Override public void showProgress(boolean visible) {
    progress.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
  }

  @Override public void showLoginSuccessful(User user) {
    userLayout.setVisibility(View.VISIBLE);

    Name name = user.getName();
    textviewName.setText("Nombre: " + name.getFirst() + " " + name.getLast());

    textviewGender.setText("Género: " + user.getGender());
    textviewPhone.setText(user.getPhone());

    Location location = user.getLocation();
    textviewLocation.setText("Dirección: "
        + location.getStreet()
        + " Ciudad: "
        + location.getCity()
        + " Provincia: "
        + location.getState());
  }

  @Override public void showLoginFailed() {
    Snackbar.make(loginButton, "Error de servidor", Snackbar.LENGTH_LONG).show();
  }
}
