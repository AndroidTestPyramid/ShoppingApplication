package droidcon.shopping.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.InputStream;

import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.repository.ImageRepository;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductView;

public class ImagePresenter {
  private final ProductView productView;
  private final ImageFetcher imageFetcher;
  private ImageRepository imageRepository;

  public ImagePresenter(ProductView productView, ImageFetcher imageFetcher, ImageRepository imageRepository) {
    this.productView = productView;
    this.imageFetcher = imageFetcher;
    this.imageRepository = imageRepository;
  }

  public void fetchImageFor(ImageView imageView, String imageUrl) {
    Bitmap image = imageRepository.getImageFor(imageUrl);
    if (image != null)
      productView.renderImage(imageView, image);
    else
      imageFetcher.execute(imageUrl, bitmapCallback(imageView, imageUrl));
  }

  private ResponseCallback<Bitmap> bitmapCallback(final ImageView imageView, final String imageUrl) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        imageRepository.save(imageUrl, response);
        productView.renderImage(imageView, response);
      }

      @Override
      public void onError(Exception exception) {
      }
    };
  }
}
