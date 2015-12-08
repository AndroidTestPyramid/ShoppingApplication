package droidcon.shopping.view;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public interface ProductView {
  void renderImage(ImageView imageView, Bitmap response);
  void renderProductTitle(String title);
  void renderProductCost(String price);
  void renderProductUpcomingDeal(int upcomingDealVisibilityStatus, String upcomingDeal);
  void renderProductPopularityStatus(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus);
}
