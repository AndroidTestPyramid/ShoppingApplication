package bootcamp.android.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import bootcamp.android.fragments.ProductDetailsFragment;
import bootcamp.android.models.Product;

import static bootcamp.android.constants.Constants.PRODUCT_KEY;

public class ProductDetailsPagerAdapter extends FragmentStatePagerAdapter {

  private ArrayList<Product> products;

  public ProductDetailsPagerAdapter(FragmentManager fm, ArrayList<Product> products) {
    super(fm);
    this.products = products;
  }

  @Override
  public Fragment getItem(int index) {
    Fragment productDetailsFragment = new ProductDetailsFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(PRODUCT_KEY, products.get(index));
    productDetailsFragment.setArguments(bundle);
    return productDetailsFragment;
  }

  @Override
  public int getCount() {
    return products.size();
  }
}
