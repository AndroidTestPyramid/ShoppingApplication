package droidcon.login.presenter;

import java.io.InputStream;

import droidcon.cart.R;
import droidcon.login.service.LoginService;
import droidcon.login.view.LoginView;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializerFactory;

public class LoginPresenter {

  private LoginView loginView;
  private LoginService loginService;

  public LoginPresenter(LoginView loginView, LoginService loginService) {
    this.loginView = loginView;
    this.loginService = loginService;
  }

  public void login(String email, String password) {
    boolean emailValidation = validateEmail(email);
    if (emailValidation) {
      boolean passwordValidation = validatePassword(password);
      if(passwordValidation){
        loginView.showProgressDialog(R.string.signing_in);
        checkLogin(email, password);
      }
    }
  }

  private void checkLogin(String email, String password) {
    loginService.doLogin(email, password, loginResponseCallback());
  }

  private ResponseCallback loginResponseCallback() {
    return new ResponseCallback<String>() {
      @Override
      public String deserialize(InputStream response) {
        return ResponseDeserializerFactory.jsonStringDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(String response) {
        loginView.hideProgressDialog();
        if (response == null) {
          loginView.showErrorOnInvalidCredential(R.string.invalid_credential);
        } else
          loginView.navigateToShoppingActivity();
      }

      @Override
      public void onError(Exception exception) {
        loginView.hideProgressDialog();
        loginView.showTechnicalDifficultyError(R.string.technical_difficulty);
      }
    };
  }

  private boolean validateEmail(String email) {
    if (isEmptyOrNull(email)) {
      loginView.showErrorOnInvalidEmail(R.string.error_field_required);
      return false;
    }
    if (!isEmailValid(email)) {
      loginView.showErrorOnInvalidEmail(R.string.error_invalid_email);
      return false;
    }
    return true;
  }

  private boolean isEmptyOrNull(String email) {
    return email == null || email.isEmpty();
  }

  private boolean isEmailValid(String email) {
    return email.contains("@");
  }

  private boolean validatePassword(String password) {
    if (isEmptyOrNull(password)) {
      loginView.showErrorOnInvalidPassword(R.string.error_field_required);
      return false;
    }
    if (!isPasswordValid(password)) {
      loginView.showErrorOnInvalidPassword(R.string.error_invalid_password);
      return false;
    }
    return true;
  }

  private boolean isPasswordValid(String password) {
    return password.length() > 4;
  }
}
