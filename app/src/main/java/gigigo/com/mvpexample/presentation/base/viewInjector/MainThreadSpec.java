package gigigo.com.mvpexample.presentation.base.viewInjector;

import android.os.Handler;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class MainThreadSpec implements ThreadSpec {
  private Handler handler = new Handler();

  @Override public void execute(Runnable action) {
    handler.post(action);
  }
}