package droidcon.shopping.presenter;

import org.junit.Before;
import org.junit.Test;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.shopping.model.Product;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductDetailView;
import droidcon.shopping.viewmodel.ProductViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductDetailsPresenterTest {
  private int price = 25;
  private String imageUrl = "";
  private int productId = 1;
  private String title = "watch";
  private String description = "watch_new";
  private int upcomingDeal = 50;
  private Boolean isNew = false;
  private Boolean isPopular = false;
  private ProductViewModel productViewModel;
  private ProductDetailsPresenter productDetailsPresenter;
  private ProductDetailView productDetailView;

  @Before
  public void setup(){
    final Product product = new Product(productId, imageUrl, price, title, description, upcomingDeal, isNew, isPopular);
    productViewModel = new ProductViewModel(product);
    productDetailView = mock(ProductDetailView.class);
    final StringResolver stringResolver = mock(StringResolver.class);
    productDetailsPresenter = new ProductDetailsPresenter(productDetailView, stringResolver);
    when(stringResolver.getString(R.string.addedToCart)).thenReturn("Item saved to cart");
  }

  @Test
  public void shouldInvokeSetDescriptionOnTheDetailsScreen(){
    productDetailsPresenter.renderDetailedView(productViewModel);

    verify(productDetailView).setDescription("watch_new");
  }

  @Test
  public void shouldShowToastMessageOnSavingProductToDB(){
    final ProductInCart productInCart = mock(ProductInCart.class);

    productDetailsPresenter.saveProduct(productInCart);

    verify(productInCart).save();
    verify(productDetailView).showToastWithMessage("Item saved to cart");
  }
}