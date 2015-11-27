package droidcon.shopping.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class Product  implements Parcelable {

  @SerializedName("image_url")
  private final String imageUrl;
  @SerializedName("product_id")
  private final int productId;
  private final int price;
  private String title;
  private String description;

  public Product(int productId, String title, String description, String imageUrl, int price) {
    this.productId = productId;
    this.title = title;
    this.description = description;
    this.imageUrl = imageUrl;
    this.price = price;
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

  public int getPrice() {
    return price;
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
    dest.writeInt(price);
  }

  private Product(Parcel in){
    this.productId = in.readInt();
    this.title = in.readString();
    this.description = in.readString();
    this.imageUrl = in.readString();
    this.price = in.readInt();
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