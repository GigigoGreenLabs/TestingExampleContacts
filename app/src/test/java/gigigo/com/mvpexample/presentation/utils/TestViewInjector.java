package gigigo.com.mvpexample.presentation.utils;

import gigigo.com.mvpexample.presentation.base.viewInjector.GenericViewInjector;

public class TestViewInjector  implements GenericViewInjector {
  @Override public <V> V injectView(V view) {
    return view;
  }

  @Override public <V> V nullObjectPatternView(V view) {
    return view;
  }
}
