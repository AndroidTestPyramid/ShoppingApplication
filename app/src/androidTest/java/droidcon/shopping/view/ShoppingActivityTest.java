package droidcon.shopping.view;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.rule.DataResetRule;
import droidcon.rule.MockWebServerRule;
import droidcon.shopping.model.Product;
import droidcon.shopping.viewmodel.ProductViewModel;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ShoppingActivityTest {

  @Rule
  public IntentsTestRule<ShoppingActivity> activityTestRule = new IntentsTestRule<>(ShoppingActivity.class, false, false);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule
  public DataResetRule dataResetRule = new DataResetRule();

  @Test
  public void numOfItemsInCartShouldBeVisible() {
    ProductInCart productInCart = new ProductInCart(0);
    productInCart.save();

    activityTestRule.launchActivity(new Intent());

    onView(withId(R.id.num_of_products)).check(matches(withText("1")));
  }

  @Test //May need a counting Idling resource
  public void shouldSwipeToAccessoriesTabAndClickOnItemToSeeDetails() throws InterruptedException {
    activityTestRule.launchActivity(new Intent());

    onView(withId(R.id.viewPager))
        .perform(swipeLeft());

    intending(anyIntent()).respondWith(new Instrumentation.ActivityResult(RESULT_OK, new Intent()));

    onView(withText("Rs.2500")).perform(click());

//    Thread.sleep(10000);

    final ProductViewModel productViewModel = new ProductViewModel(new Product(1,
        "http://xplorationstudio.com/sample_images/watch_2.jpeg", 2500,
        "SONY SMARTWATCH (BLACK)", "Taking the popular and coveted touch technology on wristwatches to a whole new level in terms of touch and tech, is this awe-inspiring smartwatch from Sony.",
        25, true, false));
    
    intended(allOf(hasComponent(ProductDetailsActivity.class.getName()),
        hasExtra(ProductsBaseFragment.PRODUCT_KEY, productViewModel)));
  }
}