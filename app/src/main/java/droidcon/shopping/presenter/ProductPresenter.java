package droidcon.shopping.presenter;

import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductView;
import droidcon.shopping.viewmodel.ProductViewModel;

public class ProductPresenter {

  private final ProductView productView;
  private final StringResolver stringResolver;

  public ProductPresenter(ProductView productView, StringResolver stringResolver) {
    this.productView = productView;
    this.stringResolver = stringResolver;
  }

  public void renderViewFor(ProductViewModel product) {
    productView.renderProductTitle(product.getTitle());
    productView.renderProductCost(product.getPrice(stringResolver));
    productView.renderProductUpcomingDeal(product.getUpcomingDealVisibilityStatus(), product.getUpcomingDeal(stringResolver));
    productView.renderProductPopularityStatus(product.getPopularityLabel(stringResolver), product.getPopularityTextColor(), product.getPopularityVisibilityStatus());
  }
}
