package droidcon.shopping.presenter;

import android.content.res.Resources;
import android.view.View;

import org.junit.Test;

import droidcon.cart.R;
import droidcon.shopping.builder.ProductBuilder;
import droidcon.shopping.model.Product;
import droidcon.shopping.repository.ImageRepository;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductView;
import droidcon.shopping.viewmodel.ProductViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductPresenterTest {

  @Test
  public void shouldRenderDataOnView(){
    final ProductView productView = mock(ProductView.class);

    final ImagePresenter imagePresenter = mock(ImagePresenter.class);
    final Resources resources = mock(Resources.class);
    when(resources.getString(R.string.cost)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");

    Product product = new ProductBuilder()
        .withTitle("watch")
        .withPrice(25)
        .withUpcomingDeal(50).build();

    final ProductPresenter productPresenter = new ProductPresenter(productView, product, imagePresenter, resources);

    productPresenter.renderView();

    verify(productView).renderProductTitle("watch");
    verify(productView).renderProductCost("Rs. 25");
    verify(productView).renderProductUpcomingDeal(View.VISIBLE, "50%");
    verify(productView).renderProductPopularityStatus("", R.color.white, View.GONE);
  }
}