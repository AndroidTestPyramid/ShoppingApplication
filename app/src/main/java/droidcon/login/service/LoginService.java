package droidcon.login.service;

import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;

import static droidcon.service.APIClient.RequestType.GET;

public class LoginService {

  public static final String LOGIN_URL = "http://xplorationstudio.com/sample_images/%s/%s.json";

  private APIClient apiClient;

  public LoginService(APIClient apiClient){
    this.apiClient = apiClient;
  }

  public void doLogin(String email, String password, ResponseCallback responseCallback) {
    apiClient.execute(GET, String.format(LOGIN_URL, email, password), responseCallback);
  }
}
