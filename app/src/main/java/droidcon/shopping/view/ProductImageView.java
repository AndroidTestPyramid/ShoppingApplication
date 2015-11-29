package droidcon.shopping.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ProductImageView {
  void renderImageFor(ImageView view, Bitmap response);
}
