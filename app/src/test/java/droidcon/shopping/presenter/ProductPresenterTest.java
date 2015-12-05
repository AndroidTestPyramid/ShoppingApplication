package droidcon.shopping.presenter;

import android.view.View;

import org.junit.Test;

import droidcon.cart.R;
import droidcon.shopping.builder.ProductBuilder;
import droidcon.shopping.model.Product;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductView;
import droidcon.shopping.viewmodel.ProductViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductPresenterTest {

  private ProductViewModel productViewModel;

  @Test
  public void shouldRenderDataOnView(){
    final ProductView productView = mock(ProductView.class);

    final StringResolver stringResolver = mock(StringResolver.class);
    when(stringResolver.getString(R.string.cost)).thenReturn("Rs. ");
    when(stringResolver.getString(R.string.percentage_sign)).thenReturn("%");

    Product product = new ProductBuilder()
        .withTitle("watch")
        .withPrice(25)
        .withUpcomingDeal(50).build();

    productViewModel = new ProductViewModel(product);

    final ProductPresenter productPresenter = new ProductPresenter(productView, stringResolver);

    productPresenter.renderViewFor(productViewModel);

    verify(productView).renderProductTitle("watch");
    verify(productView).renderProductCost("Rs. 25");
    verify(productView).renderProductUpcomingDeal(View.VISIBLE, "50%");
    verify(productView).renderProductPopularityStatus("", R.color.white, View.GONE);
  }
}