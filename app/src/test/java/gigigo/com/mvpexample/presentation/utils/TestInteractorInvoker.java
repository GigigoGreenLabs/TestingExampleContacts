package gigigo.com.mvpexample.presentation.utils;

import com.gigigo.interactorexecutor.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.base.invoker.InteractorInvoker;
import com.gigigo.interactorexecutor.interactors.InteractorError;
import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestInteractorInvoker {

  private TestInteractorInvoker() {
  }

  public static InteractorInvoker create() {
    InteractorInvoker interactorInvoker = mock(InteractorInvoker.class);
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        InteractorExecution execution = (InteractorExecution) invocation.getArguments()[0];
        InteractorResponse response = execution.getInteractor().call();
        InteractorError error = response.getError();
        if (response.hasError() && execution.getInteractorErrorResult(error.getClass()) != null) {
          execution.getInteractorErrorResult(error.getClass()).onResult(error);
        } else if (execution.getInteractorResult() != null) {
          execution.getInteractorResult().onResult(response.getResult());
        }
        return null;
      }
    }).when(interactorInvoker).execute(anyInteractorExecution());
    return interactorInvoker;
  }

  private static InteractorExecution<?> anyInteractorExecution() {
    return any();
  }
}
