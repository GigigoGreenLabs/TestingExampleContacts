package gigigo.com.mvpexample.presentation.base.viewInjector;

public interface GenericViewInjector {
  <V> V injectView(V view);
  <V> V nullObjectPatternView(V view);
}
