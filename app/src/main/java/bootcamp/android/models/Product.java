package bootcamp.android.models;

public class Product {

  private String imageUrl;
  private String title;
  private String description;

  public Product(String title, String description, String imageUrl) {
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

  @Override
  public String toString() {
    return title;
  }
}