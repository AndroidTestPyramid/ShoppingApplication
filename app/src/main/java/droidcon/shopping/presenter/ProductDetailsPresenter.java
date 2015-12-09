package droidcon.shopping.presenter;

import android.content.res.Resources;
import android.widget.ImageView;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.shopping.model.Product;
import droidcon.shopping.repository.ImageRepository;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductDetailView;
import droidcon.shopping.viewmodel.ProductViewModel;

public class ProductDetailsPresenter {

  private final ProductDetailView productDetailView;
  private final Resources resources;
  private final ProductPresenter productPresenter;
  private final ProductViewModel productViewModel;

  public ProductDetailsPresenter(ProductDetailView productDetailView, Product product, Resources resources, ProductPresenter productPresenter) {
    this.productDetailView = productDetailView;
    this.resources = resources;
    this.productPresenter = productPresenter;
    productViewModel = new ProductViewModel(product, resources);
  }

  public void renderDetailedView() {
    productPresenter.renderView();
    productDetailView.setDescription(productViewModel.getDescription());
  }

  public void saveProduct(ProductInCart product) {
    product.save();
    productDetailView.showToastWithMessage(resources.getString(R.string.addedToCart));
  }

  public void renderImageFor(ImageView imageView) {
    productPresenter.renderImageFor(imageView);
  }
}
