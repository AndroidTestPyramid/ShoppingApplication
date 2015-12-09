package droidcon.shopping.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.service.APIClient;
import droidcon.service.APIClient.RequestType;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.model.Product;
import droidcon.shopping.util.ImageCache;
import droidcon.shopping.viewmodel.ProductViewModel;

import static droidcon.shopping.view.ProductsBaseFragment.PRODUCT_KEY;

public class ProductDetailsActivity extends AppCompatActivity {

  private Product product;
  private ProductViewModel productViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
    productViewModel = new ProductViewModel(product, getResources());
    renderProductTitle();
    renderProductDescription();
    renderProductCost();
    renderProductImage();
    renderProductUpcomingDeal();
    renderProductPopularityStatus();
  }

  public void addToCart(View view) {
    Toast.makeText(this, R.string.addedToCart, Toast.LENGTH_SHORT).show();
    final ProductInCart productInCart = new ProductInCart(product.getProductId());
    productInCart.save();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void renderProductPopularityStatus() {
    TextView popularityView = (TextView) findViewById(R.id.popularity);
    popularityView.setText(productViewModel.getPopularityLabel());
    popularityView.setTextColor(getResources().getColor(productViewModel.getPopularityTextColor()));
    popularityView.setVisibility(productViewModel.getPopularityVisibilityStatus());
  }

  private void renderProductDescription() {
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(productViewModel.getDescription());
  }

  private void renderProductTitle() {
    TextView titleTextView = (TextView) findViewById(R.id.product_title);
    titleTextView.setText(productViewModel.getTitle());
  }

  private void renderProductImage() {
    APIClient apiClient = new APIClient();
    final ImageView imageView = (ImageView) findViewById(R.id.product_image);
    final Bitmap image = new ImageCache(this).getImageFor(product.getImageUrl());
    if (image != null) {
      imageView.setImageBitmap(image);
    } else {
      apiClient.execute(RequestType.GET, product.getImageUrl(), bitmapCallback(imageView));
    }
  }

  private void renderProductCost() {
    TextView costTextView = (TextView) findViewById(R.id.cost);
    costTextView.setText(productViewModel.getPrice());
  }

  private void renderProductUpcomingDeal() {
    final LinearLayout upcomingDealView = (LinearLayout)findViewById(R.id.upcoming_deal);
    upcomingDealView.setVisibility(productViewModel.getUpcomingDealVisibilityStatus());

    TextView percentage = (TextView) findViewById(R.id.percentage);
    percentage.setText(productViewModel.getUpcomingDeal());
  }

  private ResponseCallback<Bitmap> bitmapCallback(final ImageView imageView) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        new ImageCache(ProductDetailsActivity.this).save(product.getImageUrl(), response);
        imageView.setImageBitmap(response);
      }

      @Override
      public void onError(Exception exception) {
      }
    };
  }
}
