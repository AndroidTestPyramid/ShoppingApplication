package droidcon.login.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import droidcon.cart.R;
import droidcon.login.presenter.LoginPresenter;
import droidcon.login.service.LoginService;
import droidcon.service.APIClient;
import droidcon.shopping.view.ShoppingActivity;

public class LoginActivity extends Activity implements LoginView {

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

    new LoginPresenter(this, new LoginService(new APIClient())).login(email, password);
  }

  @Override
  public void showProgressDialog(int message) {
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading...");
    progressDialog.setCancelable(false);
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.show();
  }

  @Override
  public void hideProgressDialog() {
    progressDialog.hide();
  }

  @Override
  public void showErrorOnInvalidCredential(int errorMessage) {
    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    emailView.setText(null);
    passwordView.setText(null);
    emailView.requestFocus();
  }

  @Override
  public void navigateToShoppingActivity() {
    LoginActivity.this.startActivity(new Intent(LoginActivity.this, ShoppingActivity.class));
  }

  @Override
  public void showTechnicalDifficultyError(int technicalDifficultyError) {
    Toast.makeText(LoginActivity.this, technicalDifficultyError, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showErrorOnInvalidEmail(int errorMessage) {
    emailView.setError(getString(errorMessage));
    emailView.requestFocus();
  }

  @Override
  public void showErrorOnInvalidPassword(int errorMessage) {
    passwordView.setError(getString(errorMessage));
    passwordView.requestFocus();
  }
}
