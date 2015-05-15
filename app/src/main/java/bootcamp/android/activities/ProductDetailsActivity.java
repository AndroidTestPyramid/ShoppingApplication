package bootcamp.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import bootcamp.android.R;
import bootcamp.android.fragments.ProductDetailsFragment;


public class ProductDetailsActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details_container);

    Bundle extraArguments = getIntent().getExtras();
    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
    productDetailsFragment.setArguments(extraArguments);
    getSupportFragmentManager().beginTransaction().add(R.id.product_details_container, productDetailsFragment, "products_fragment").commit();
  }
}
