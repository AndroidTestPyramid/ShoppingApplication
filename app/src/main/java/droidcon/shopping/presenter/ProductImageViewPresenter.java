package droidcon.shopping.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.InputStream;

import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductImageView;

public class ProductImageViewPresenter {

  private final ProductImageView productImageView;
  private final ImageFetcher imageFetcher;

  public ProductImageViewPresenter(ProductImageView productImageView, ImageFetcher imageFetcher) {
    this.productImageView = productImageView;
    this.imageFetcher = imageFetcher;
  }

  public void fetchImageFor(ImageView imageView, String imageUrl) {
    imageFetcher.execute(imageUrl, bitmapCallback(imageView));
  }

  private ResponseCallback<Bitmap> bitmapCallback(final ImageView view) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        productImageView.renderImageFor(view, response);
      }

      @Override
      public void onError(Exception exception) {
      }
    };
  }
}
