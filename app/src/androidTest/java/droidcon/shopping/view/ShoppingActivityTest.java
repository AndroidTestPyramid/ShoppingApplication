package droidcon.shopping.view;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.rule.DataResetRule;
import droidcon.rule.MockWebServerRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ShoppingActivityTest {

  @Rule
  public ActivityTestRule<ShoppingActivity> activityTestRule = new ActivityTestRule<>(ShoppingActivity.class, false, false);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule
  public DataResetRule dataResetRule = new DataResetRule();

  @Test
  public void numOfItemsInCartShouldBeVisible(){
    ProductInCart productInCart = new ProductInCart(0);
    productInCart.save();

    activityTestRule.launchActivity(new Intent());

    onView(withId(R.id.num_of_products)).check(matches(withText("1")));
  }

  // TODO: 1. Swipe(facing issues becoz data even though hidden it is present..num of tabs assertion have to look at
  //2. click and start next activity
  //3. data rendering - need mock server rule to work
}