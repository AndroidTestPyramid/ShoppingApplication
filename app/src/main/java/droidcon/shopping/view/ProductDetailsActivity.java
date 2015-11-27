package droidcon.shopping.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseParserFactory;
import droidcon.shopping.model.Product;

import static droidcon.shopping.Constants.PRODUCT_KEY;

public class ProductDetailsActivity extends Activity {

  private Product product;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
    TextView imageTitle = (TextView) findViewById(R.id.product_title);
    imageTitle.setText(product.getTitle());
    ImageView imageView = (ImageView) findViewById(R.id.product_image);
    APIClient contentFetcher = new APIClient("GET", bitmapCallback(imageView));
    contentFetcher.execute(product.getImageUrl());
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(product.getDescription());
  }

  private ResponseCallback<Bitmap> bitmapCallback(final ImageView imageView) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap parse(InputStream response) {
        return ResponseParserFactory.bitmapParser().parse(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        imageView.setImageBitmap(response);
      }

      @Override
      public void onError(Exception exception) {

      }
    };
  }

  public void addToCart(View view) {
    final ProductInCart productInCart = new ProductInCart(product.getProductId());
    productInCart.save();
  }
}
