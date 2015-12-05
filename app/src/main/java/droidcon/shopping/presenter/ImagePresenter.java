package droidcon.shopping.presenter;

import android.graphics.Bitmap;
import java.io.InputStream;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductView;

public class ImagePresenter {
  private final ProductView productView;
  private final ImageFetcher imageFetcher;

  public ImagePresenter(ProductView productView, ImageFetcher imageFetcher) {
    this.productView = productView;
    this.imageFetcher = imageFetcher;
  }

  public void fetchImageFor(String imageUrl) {
    imageFetcher.execute(imageUrl, bitmapCallback());
  }

  private ResponseCallback<Bitmap> bitmapCallback() {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        productView.renderImage(response);
      }

      @Override
      public void onError(Exception exception) {
      }
    };
  }
}
