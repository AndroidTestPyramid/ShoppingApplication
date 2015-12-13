package droidcon.shopping.viewmodel;

import android.content.res.Resources;
import android.view.View;

import droidcon.cart.R;
import droidcon.shopping.model.Product;

public class ProductViewModel {
  private final Product product;
  private final Resources resources;

  public ProductViewModel(Product product, Resources resources) {
    this.product = product;
    this.resources = resources;
  }

  public String getTitle() {
    return product.getTitle();
  }

  public String getPrice() {
    return String.format("%s%d", resources.getString(R.string.cost), product.getPrice());
  }

  public String getUpcomingDeal() {
    if (anyUpcomingDeal())
      return String.format("%d%s", product.getUpcomingDeal(), resources.getString(R.string.percentage_sign));
    return "";
  }

  public int getUpcomingDealVisibilityStatus() {
    if (anyUpcomingDeal())
      return View.VISIBLE;
    return View.GONE;
  }

  //TODO: Repeated - found it not so useful to use ENUM..still the if persists.
  public String getPopularityLabel() {
    if (product.isPopular()) {
      return resources.getString(R.string.popular);
    }
    if (product.isNew()) {
      return resources.getString(R.string.product_new);
    }
    return "";
  }

  public int getPopularityVisibilityStatus() {
    if (product.isNew() || product.isPopular()) {
      return View.VISIBLE;
    }
    return View.GONE;
  }

  public String getDescription() {
    return product.getDescription();
  }

  public int getPopularityTextColor() {
    if (product.isPopular()) {
      return R.color.purple;
    }
    if (product.isNew()) {
      return R.color.red;
    }
    return R.color.white;
  }

  private boolean anyUpcomingDeal() {
    return product.getUpcomingDeal() != 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProductViewModel that = (ProductViewModel) o;

    return product.equals(that.product);

  }

  @Override
  public int hashCode() {
    return product.hashCode();
  }

}
