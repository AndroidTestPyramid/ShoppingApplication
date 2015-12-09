package droidcon.shopping.presenter;

import android.content.res.Resources;
import android.view.View;

import org.junit.Before;
import org.junit.Test;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.shopping.builder.ProductBuilder;
import droidcon.shopping.model.Product;
import droidcon.shopping.repository.ImageRepository;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductDetailView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductDetailsPresenterTest {
  private ProductDetailsPresenter productDetailsPresenter;
  private ProductDetailView productDetailView;

  @Before
  public void setup(){
    Product product = new ProductBuilder()
        .withPrice(25)
        .withTitle("watch")
        .withUpcomingDeal(50)
        .withDescription("watch_desc").build();

    final Resources resources = mock(Resources.class);
    when(resources.getString(R.string.cost)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");

    productDetailView = mock(ProductDetailView.class);
    final ProductPresenter productPresenter = mock(ProductPresenter.class);

    productDetailsPresenter = new ProductDetailsPresenter(productDetailView, product, resources, productPresenter);
    when(resources.getString(R.string.addedToCart)).thenReturn("Item saved to cart");
    productDetailsPresenter.renderDetailedView();
  }

  @Test
  public void shouldInvokeSetDescriptionOnTheDetailsScreen(){
    verify(productDetailView).setDescription("watch_desc");
  }

  @Test
  public void shouldShowToastMessageOnSavingProductToDB(){
    final ProductInCart productInCart = mock(ProductInCart.class);

    productDetailsPresenter.saveProduct(productInCart);

    verify(productInCart).save();
    verify(productDetailView).showToastWithMessage("Item saved to cart");
  }
}