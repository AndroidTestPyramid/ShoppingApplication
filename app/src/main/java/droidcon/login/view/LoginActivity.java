package droidcon.login.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import droidcon.cart.R;
import droidcon.service.APIClient;
import droidcon.service.APIClient.RequestType;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseParserFactory;
import droidcon.shopping.view.ShoppingActivity;

public class LoginActivity extends Activity {

  public static final String LOGIN_URL = "http://xplorationstudio.com/sample_images/%s/%s.json";
  private EditText emailView;
  private EditText passwordView;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);

    emailView = (EditText) findViewById(R.id.email);
    passwordView = (EditText) findViewById(R.id.password);
    passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
          login(findViewById(R.id.sign_in_button));
          return true;
        }
        return false;
      }
    });
  }

  public void login(View signInButtonView) {
    String email = emailView.getText().toString();
    String password = passwordView.getText().toString();

    boolean emailValidation = validateEmail(email);
    if (emailValidation) {
      boolean passwordValidation = validatePassword(password);
      if (passwordValidation) {
        showProgress();
        checkLogin(email, password);
      }
    }
  }

  private void checkLogin(String email, String password) {
    new APIClient(RequestType.GET, getCallback()).execute(String.format(LOGIN_URL, email, password));
  }

  @NonNull
  private ResponseCallback<String> getCallback() {
    return new ResponseCallback<String>() {
      @Override
      public String deserialize(InputStream response) {
        return ResponseParserFactory.jsonParser().parse(response);
      }

      @Override
      public void onSuccess(String response) {
        hideProgress();
        if (response == null) {
          Toast.makeText(LoginActivity.this, R.string.invalid_credential, Toast.LENGTH_SHORT).show();
          emailView.setText(null);
          passwordView.setText(null);
          emailView.requestFocus();
        } else
          LoginActivity.this.startActivity(new Intent(LoginActivity.this, ShoppingActivity.class));
      }

      @Override
      public void onError(Exception exception) {
        hideProgress();
        Toast.makeText(LoginActivity.this, R.string.technical_difficulty, Toast.LENGTH_SHORT).show();
      }
    };
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
    progressDialog = ProgressDialog.show(this, "", getString(R.string.signing_in, true, false));
  }

  private void hideProgress() {
    progressDialog.hide();
  }
}

