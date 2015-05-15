package bootcamp.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import bootcamp.android.R;
import bootcamp.android.adapters.ProductDetailsPagerAdapter;
import bootcamp.android.models.Product;

import static bootcamp.android.constants.Constants.CURRENT_PRODUCT_KEY;
import static bootcamp.android.constants.Constants.PRODUCTS_KEY;


public class ProductDetailsActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details_container);

    Bundle extraArguments = getIntent().getExtras();
    ArrayList<Product> products = extraArguments.getParcelableArrayList(PRODUCTS_KEY);
    int current = extraArguments.getInt(CURRENT_PRODUCT_KEY);
    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    viewPager.setAdapter(new ProductDetailsPagerAdapter(getSupportFragmentManager(), products));
    viewPager.setCurrentItem(current);
  }
}