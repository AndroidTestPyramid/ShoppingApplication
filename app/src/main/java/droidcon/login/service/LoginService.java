package droidcon.login.service;

import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;

import static droidcon.service.APIClient.RequestType.GET;

public class LoginService {

  private static final String LOGIN_URL = "%s/sample_images/%s/%s.json";

  private APIClient apiClient;

  public LoginService(APIClient apiClient){
    this.apiClient = apiClient;
  }

  public void doLogin(String email, String password, ResponseCallback responseCallback) {
    apiClient.execute(GET, String.format(LOGIN_URL, EnvironmentManager.getInstance().environment(), email, password), responseCallback);
  }
}
