package droidcon.shopping.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.service.APIClient;
import droidcon.service.APIClient.RequestType;
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
    renderProductTitle();
    renderProductDescription();
    renderProductImage((ImageView) findViewById(R.id.product_image));
  }

  public void addToCart(View view) {
    Toast.makeText(this, R.string.addedToCart, Toast.LENGTH_SHORT).show();
    final ProductInCart productInCart = new ProductInCart(product.getProductId());
    productInCart.save();
  }

  private void renderProductDescription() {
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(product.getDescription());
  }

  private void renderProductTitle() {
    TextView imageTitle = (TextView) findViewById(R.id.product_title);
    imageTitle.setText(product.getTitle());
  }

  private void renderProductImage(ImageView imageView) {
    APIClient apiClient = new APIClient(RequestType.GET, bitmapCallback(imageView));
    apiClient.execute(product.getImageUrl());
  }

  private ResponseCallback<Bitmap> bitmapCallback(final ImageView imageView) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
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

}
