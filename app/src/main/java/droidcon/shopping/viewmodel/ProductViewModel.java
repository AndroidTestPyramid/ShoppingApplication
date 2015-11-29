package droidcon.shopping.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import droidcon.cart.R;
import droidcon.shopping.model.Product;
import droidcon.shopping.util.StringResolver;

public class ProductViewModel implements Parcelable {
  private final Product product;

  public ProductViewModel(Product product) {
    this.product = product;
  }

  public String getTitle() {
    return product.getTitle();
  }

  public String getImageUrl() {
    return product.getImageUrl();
  }

  public String getPrice(StringResolver stringResolver) {
    return String.format("%s%d", stringResolver.getString(R.string.cost), product.getPrice());
  }

  public String getUpcomingDeal(StringResolver stringResolver) {
    if(anyUpcomingDeal())
     return String.format("%d%s", product.getUpcomingDeal(), stringResolver.getString(R.string.percentage_sign));
    return "";
  }

  public int getUpcomingDealVisibilityStatus() {
   if(anyUpcomingDeal())
     return View.VISIBLE;
   return View.GONE;
  }

  //TODO: Repeated - should move to constructor?
  public String getPopularityLabel(StringResolver stringResolver) {
    if (product.isPopular()) {
      return stringResolver.getString(R.string.popular);
    }
    if (product.isNew()) {
      return stringResolver.getString(R.string.product_new);
    }
    return "";
  }

  public int getPopularityVisibilityStatus() {
    if(product.isNew() || product.isPopular()){
      return View.VISIBLE;
    }
    return View.GONE;
  }

  public int getProductId() {
    return product.getProductId();
  }

  public String getDescription() {
    return product.getDescription();
  }

  public int getPopularityTextColor() {
    if (product.isPopular()) {
      return R.color.purple;
    }
    if (product.isNew()) {
      return R.color.green;
    }
    return R.color.white;
  }

  private boolean anyUpcomingDeal() {
    return product.getUpcomingDeal() != 0;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeParcelable(product, flags);
  }

  protected ProductViewModel(Parcel in) {
    product = in.readParcelable(Product.class.getClassLoader());
  }

  public static final Creator<ProductViewModel> CREATOR = new Creator<ProductViewModel>() {
    @Override
    public ProductViewModel createFromParcel(Parcel in) {
      return new ProductViewModel(in);
    }

    @Override
    public ProductViewModel[] newArray(int size) {
      return new ProductViewModel[size];
    }
  };
}
