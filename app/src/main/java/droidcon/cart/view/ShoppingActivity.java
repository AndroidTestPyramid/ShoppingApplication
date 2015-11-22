package droidcon.cart.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import droidcon.cart.R;

public class ShoppingActivity extends AppCompatActivity {

  private ViewPager viewPager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping);
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
    getMenuInflater().inflate(R.menu.cart_menu, menu);
    return super.onCreateOptionsMenu(menu);
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
