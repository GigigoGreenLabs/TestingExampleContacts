package gigigo.com.mvpexample.presentation.base.viewInjector;

import me.panavtec.threaddecoratedview.views.ThreadSpec;
import me.panavtec.threaddecoratedview.views.ViewInjector;

public class ThreadViewInjector implements GenericViewInjector {
  private ThreadSpec threadSpec;

  public ThreadViewInjector(ThreadSpec threadSpec) {
    this.threadSpec = threadSpec;
  }

  @Override public <V> V injectView(V view) {
    return ViewInjector.inject(view, threadSpec);
  }

  @Override public <V> V nullObjectPatternView(V view) {
    return ViewInjector.nullObjectPatternView(view);
  }
}
