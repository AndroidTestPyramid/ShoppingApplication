package droidcon.shopping.viewmodel;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import droidcon.cart.R;
import droidcon.shopping.builder.ProductBuilder;
import droidcon.shopping.model.Product;
import droidcon.shopping.util.StringResolver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductViewModelTest {

  private ProductViewModel productViewModel;
  private StringResolver stringResolver;

  @Before
  public void setup(){
    stringResolver = mock(StringResolver.class);
    when(stringResolver.getString(R.string.cost)).thenReturn("Rs. ");
    when(stringResolver.getString(R.string.percentage_sign)).thenReturn("%");
    when(stringResolver.getString(R.string.product_new)).thenReturn("New");
    when(stringResolver.getString(R.string.popular)).thenReturn("Popular");

    Product product = new ProductBuilder()
        .withPrice(25)
        .withUpcomingDeal(50).build();

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
    Product product = new ProductBuilder()
        .withUpcomingDeal(0).build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getUpcomingDeal(stringResolver), "");
  }

  @Test
  public void upcomingDealShouldBeVisible(){
    assertEquals(productViewModel.getUpcomingDealVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void upcomingDealShouldBeNotVisible(){
    Product product = new ProductBuilder()
        .withUpcomingDeal(0).build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getUpcomingDealVisibilityStatus(), View.GONE);
  }

  @Test
  public void newLabelShouldBeVisibleAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityLabel(stringResolver), "New");
  }

  @Test
  public void popularLabelShouldBeVisibleAsProductIsPopularAndNew(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityLabel(stringResolver), "Popular");
  }

  @Test
  public void popularLabelShouldBeVisibleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityLabel(stringResolver), "Popular");
  }

  @Test
  public void popularityViewShouldBeVisible(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityVisibilityStatus(), View.VISIBLE);
  }

  @Test
  public void popularityLabelShouldBeGreenAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityTextColor(), R.color.green);
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopularAndNew(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityTextColor(), R.color.purple);
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product);

    assertEquals(productViewModel.getPopularityTextColor(), R.color.purple);
  }
}