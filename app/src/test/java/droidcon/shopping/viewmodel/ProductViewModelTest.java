package droidcon.shopping.viewmodel;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import droidcon.cart.R;
import droidcon.shopping.model.Product;
import droidcon.shopping.util.StringResolver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductViewModelTest {

  private ProductViewModel productViewModel;
  private StringResolver stringResolver;
  private int price = 25;
  private String imageUrl = "";
  private int productId = 1;
  private String title = "watch";
  private String description = "watch_new";
  private int upcomingDeal = 50;
  private Boolean isNew = false;
  private Boolean isPopular = false;

  @Before
  public void setup(){
    stringResolver = mock(StringResolver.class);
    when(stringResolver.getString(R.string.cost)).thenReturn("Rs. ");
    when(stringResolver.getString(R.string.percentage_sign)).thenReturn("%");
    when(stringResolver.getString(R.string.product_new)).thenReturn("New");
    when(stringResolver.getString(R.string.popular)).thenReturn("Popular");

    //TODO:Builder?
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);
  }

  @Test
  public void shouldGetMassagedPrice(){
    assertEquals(productViewModel.getPrice(stringResolver), "Rs. 25");
  }

  @Test
  public void shouldGetMassagedUpcomingDeal(){
    assertEquals(productViewModel.getUpcomingDeal(stringResolver), "50%");
  }

  @Test
  public void shouldGetEmptyUpcomingDeal(){
    upcomingDeal = 0;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getUpcomingDeal(stringResolver), "");
  }

  @Test
  public void upcomingDealShouldBeVisible(){
    assertEquals(productViewModel.getUpcomingDealVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void upcomingDealShouldBeNotVisible(){
    upcomingDeal = 0;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getUpcomingDealVisibilityStatus(), View.GONE);
  }

  @Test
  public void newLabelShouldBeVisibleAsProductIsNew(){
    isNew = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityLabel(stringResolver), "New");
  }

  @Test
  public void popularLabelShouldBeVisibleAsProductIsPopularAndNew(){
    isNew = true;
    isPopular = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityLabel(stringResolver), "Popular");
  }

  @Test
  public void popularLabelShouldBeVisibleAsProductIsPopular(){
    isPopular = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityLabel(stringResolver), "Popular");
  }

  @Test
  public void popularityViewShouldBeVisible(){
    isNew = true;
    isPopular = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsNew(){
    isNew = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsPopular(){
    isPopular = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void popularityLabelShouldBeGreenAsProductIsNew(){
    isNew = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityTextColor(), R.color.green);
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopularAndNew(){
    isNew = true;
    isPopular = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityTextColor(), R.color.purple);
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopular(){
    isPopular = true;
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityTextColor(), R.color.purple);
  }
}