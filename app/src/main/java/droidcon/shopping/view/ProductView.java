package droidcon.shopping.view;

import android.graphics.Bitmap;

public interface ProductView {
  void renderImage(Bitmap response);
  void renderProductTitle(String title);
  void renderProductCost(String price);
  void renderProductUpcomingDeal(int upcomingDealVisibilityStatus, String upcomingDeal);
  void renderProductPopularityStatus(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus);
}
