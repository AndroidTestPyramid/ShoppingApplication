package droidcon.cart.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product  implements Parcelable {

  private final String imageUrl;
  private final int productId;
  private String title;
  private String description;

  public Product(int productId, String title, String description, String imageUrl) {
    this.productId = productId;
    this.title = title;
    this.description = description;
    this.imageUrl = imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getProductId() {
    return productId;
  }

  @Override
  public String toString() {
    return title;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(productId);
    dest.writeString(title);
    dest.writeString(description);
    dest.writeString(imageUrl);
  }

  private Product(Parcel in){
    this.productId = in.readInt();
    this.title = in.readString();
    this.description = in.readString();
    this.imageUrl = in.readString();
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public Product createFromParcel(Parcel in) {
      return new Product(in);
    }

    public Product[] newArray(int size) {
      return new Product[size];
    }
  };
}