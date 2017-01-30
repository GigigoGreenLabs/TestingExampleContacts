package gigigo.com.mvpexample.model.interactors.errors;

import com.gigigo.interactorexecutor.interactors.InteractorError;
import com.gigigo.interactorexecutor.responses.BusinessError;

public class NetworkInteractorError implements InteractorError{
  @Override public BusinessError getError() {
    return BusinessError.createKOInstance("");
  }
}
