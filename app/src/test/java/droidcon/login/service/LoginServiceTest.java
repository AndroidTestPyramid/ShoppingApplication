package droidcon.login.service;

import org.junit.Test;

import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginServiceTest {

  @Test
  public void shouldCallAPIClientWithProperParameters() {

    APIClient apiClient = mock(APIClient.class);
    LoginService loginService = new LoginService(apiClient);
    ResponseCallback responseCallback = mock(ResponseCallback.class);

    String email = "admin@droidcon.com";
    String password = "admin";
    loginService.doLogin(email, password, responseCallback);

    String loginURL = String.format("http://xplorationstudio.com/sample_images/%s/%s.json", email, password);
    verify(apiClient).execute(eq(APIClient.RequestType.GET), eq(loginURL), eq(responseCallback));
  }


}