package droidcon.login.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import droidcon.cart.R;
import droidcon.shopping.view.ShoppingActivity;

public class LoginActivity extends Activity {

  private UserLoginTask userLoginTask = null;

  private EditText emailView;
  private EditText passwordView;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);

    final Button signInButtonView = (Button) findViewById(R.id.sign_in_button);

    emailView = (EditText) findViewById(R.id.email);
    passwordView = (EditText) findViewById(R.id.password);
    passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
          attemptLogin(signInButtonView);
          return true;
        }
        return false;
      }
    });
  }

  public void attemptLogin(View signInButtonView) {
    String email = emailView.getText().toString();
    String password = passwordView.getText().toString();

    boolean emailValidation = validateEmail(email);
    if (emailValidation) {
      boolean passwordValidation = validatePassword(password);
      if (passwordValidation) {
        showProgress();
        userLoginTask = new UserLoginTask(email, password);
        userLoginTask.execute((Void) null);
      }
    }
  }

  private boolean validateEmail(String email) {
    if (validateEmptyTextField(emailView)) {
      if (!isEmailValid(email)) {
        emailView.setError(getString(R.string.error_invalid_email));
        emailView.requestFocus();
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean validatePassword(String password) {
    if (validateEmptyTextField(passwordView)) {
      if (!isPasswordValid(password)) {
        passwordView.setError(getString(R.string.error_invalid_password));
        passwordView.requestFocus();
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean validateEmptyTextField(EditText editText) {
    if (TextUtils.isEmpty(editText.getText())) {
      editText.setError(getString(R.string.error_field_required));
      editText.requestFocus();
      return false;
    }
    return true;
  }

  private boolean isEmailValid(String email) {
    return email.contains("@");
  }

  private boolean isPasswordValid(String password) {
    return password.length() > 4;
  }


  private void showProgress() {
    progressDialog = ProgressDialog.show(this, "", getString(R.string.logging_in, true, false));
  }

  private void hideProgress() {
    progressDialog.hide();
  }

  private class UserLoginTask extends AsyncTask<Void, Void, LoginResponse> {

    private static final String DUMMY_EMAIL = "admin@droidcon.com";
    private static final String DUMMY_PASSWORD = "admin";

    private final String email;
    private final String password;

    UserLoginTask(String email, String password) {
      this.email = email;
      this.password = password;
    }

    @Override
    protected LoginResponse doInBackground(Void... params) {
      try {
        // Simulate network access.
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        return new LoginResponse(R.string.technical_difficulty);
      }
      if (DUMMY_EMAIL.equals(email)) {
        if (DUMMY_PASSWORD.equals(password)) return new LoginResponse();
      }

      return new LoginResponse(R.string.invalid_credential);
    }

    @Override
    protected void onPostExecute(final LoginResponse loginResponse) {
      userLoginTask = null;
      hideProgress();

      if (loginResponse.isSuccess()) {
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, ShoppingActivity.class));
      } else {
        Toast.makeText(LoginActivity.this, loginResponse.getError(), Toast.LENGTH_SHORT).show();
        emailView.setText(null);
        passwordView.setText(null);
        emailView.requestFocus();
      }
    }
  }

  private class LoginResponse {
    private static final int NO_ERROR = 0;
    private int error = NO_ERROR;

    public LoginResponse(int error) {
      this.error = error;
    }

    public LoginResponse() {
    }

    public boolean isSuccess() {
      return error == NO_ERROR;
    }

    public int getError() {
      return error;
    }
  }
}

