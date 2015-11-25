package droidcon.cart.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import droidcon.cart.R;

public class ShoppingActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private Button cart;
  private int numOfItems = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    tabLayout.addTab(tabLayout.newTab().setText(R.string.electronics));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.accessories));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    viewPager = (ViewPager) findViewById(R.id.viewPager);
    viewPager.setAdapter(getCategoryAdapter(tabLayout.getTabCount()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.cart_menu, menu);

    final MenuItem item = menu.findItem(R.id.cart);
    MenuItemCompat.setActionView(item, R.layout.cart_update_count);
    View count = item.getActionView();
    cart = (Button) count.findViewById(R.id.num_of_items);
    cart.setText(String.valueOf(numOfItems));
    return super.onCreateOptionsMenu(menu);
  }

  private void setNumOfItems(int count){
    numOfItems = count;
    invalidateOptionsMenu();
  }

  @NonNull
  private FragmentPagerAdapter getCategoryAdapter(final int count) {
    return new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return new ProductsFragment();
      }

      @Override
      public int getCount() {
        return count;
      }
    };
  }
}
