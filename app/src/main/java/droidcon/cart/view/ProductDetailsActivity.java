package droidcon.cart.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidplugins.Callback;
import androidplugins.imagefetcher.ImageFetcher;
import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.cart.model.Product;

import static droidcon.cart.Constants.PRODUCT_KEY;

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
    ImageFetcher imageFetcher = new ImageFetcher(bitmapCallback(imageView), this);
    imageFetcher.execute(product.getImageUrl());
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(product.getDescription());
  }

  private Callback<Bitmap> bitmapCallback(final ImageView imageView) {
    return new Callback<Bitmap>() {
      @Override
      public void execute(Bitmap object) {
        imageView.setImageBitmap(object);
      }
    };
  }

  public void addToCart(View view) {
    final ProductInCart productInCart = new ProductInCart(product.getProductId());
    productInCart.save();
  }
}
