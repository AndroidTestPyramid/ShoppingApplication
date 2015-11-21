package droidcon.cart.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import droidcon.cart.R;

public class ShoppingActivity extends FragmentActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping);

    ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
    viewPager.setAdapter(getCategoryAdapter(R.string.electronics, R.string.accessories));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.cart_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @NonNull
  private FragmentPagerAdapter getCategoryAdapter(final int... categories) {
    return new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return new ProductsFragment();
      }

      @Override
      public int getCount() {
        return categories.length;
      }

      @Override
      public CharSequence getPageTitle(int position) {
        return getString(categories[position]);
      }
    };
  }
}
