package droidcon.shopping.viewmodel;

import android.content.res.Resources;
import android.view.View;

import droidcon.cart.R;
import droidcon.shopping.model.Product;

public class ProductViewModel {
  private final Product product;
  private final Resources resources;
  private final Label label;

  public ProductViewModel(Product product, Resources resources) {
    this.product = product;
    this.resources = resources;
    label = Label.createFrom(product);
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

  public String getLabel() {
    return resources.getString(label.getTextId());
  }

  public int getLabelVisibilityStatus() {
    return label.getVisibility();
  }

  public String getDescription() {
    return product.getDescription();
  }

  public int getLabelTextColor() {
    return label.getColor();
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

  private enum Label {
    POPULAR(R.string.popular, R.color.purple, View.VISIBLE),
    NEW(R.string.product_new, R.color.red, View.VISIBLE),
    DEFAULT(R.string.empty_string, R.color.white, View.GONE);

    private final int textId;
    private final int color;
    private final int visibility;

    Label(int textId, int color, int visibility) {
      this.textId = textId;
      this.color = color;
      this.visibility = visibility;
    }

    public static Label createFrom(Product product){
      if (product.isPopular()) {
        return POPULAR;
      }
      if (product.isNew()) {
        return NEW;
      }
      return DEFAULT;
    }

    public int getTextId() {
      return textId;
    }

    public int getColor() {
      return color;
    }

    public int getVisibility() {
      return visibility;
    }
  }
}
