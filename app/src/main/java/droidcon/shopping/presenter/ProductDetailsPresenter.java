package droidcon.shopping.presenter;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductDetailView;
import droidcon.shopping.view.ProductView;
import droidcon.shopping.viewmodel.ProductViewModel;

public class ProductDetailsPresenter {

  private final ProductDetailView productDetailView;
  private final StringResolver stringResolver;

  public ProductDetailsPresenter(ProductDetailView productDetailView, StringResolver stringResolver) {
    this.productDetailView = productDetailView;
    this.stringResolver = stringResolver;
  }

  public void renderDetailedView(ProductViewModel productViewModel) {
    productDetailView.setDescription(productViewModel.getDescription());
  }

  public void saveProduct(ProductInCart product) {
    product.save();
    productDetailView.showToastWithMessage(stringResolver.getString(R.string.addedToCart));
  }
}
