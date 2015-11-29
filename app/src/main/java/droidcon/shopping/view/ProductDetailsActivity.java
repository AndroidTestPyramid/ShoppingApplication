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

import droidcon.cart.R;
import droidcon.cart.model.ProductInCart;
import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductImagePresenter;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.viewmodel.ProductViewModel;

import static droidcon.shopping.view.ElectronicsFragment.PRODUCT_KEY;

// TODO: Do we need another presenter?
public class ProductDetailsActivity extends AppCompatActivity implements ProductImageView {

  private ProductViewModel product;
  private StringResolver stringResolver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
    stringResolver = new StringResolver(this);
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
    popularityView.setText(product.getPopularityLabel(stringResolver));
    popularityView.setTextColor(getResources().getColor(product.getPopularityTextColor()));
    popularityView.setVisibility(product.getPopularityVisibilityStatus());
  }

  private void renderProductDescription() {
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(product.getDescription());
  }

  private void renderProductTitle() {
    TextView productTitle = (TextView) findViewById(R.id.product_title);
    productTitle.setText(product.getTitle());
  }

  private void renderProductImage() {
    new ProductImagePresenter(this, new ImageFetcher(new APIClient())).fetchImageFor((ImageView) findViewById(R.id.product_image), product.getImageUrl());
  }

  private void renderProductCost() {
    TextView costTextView = (TextView) findViewById(R.id.cost);
    costTextView.setText(product.getPrice(stringResolver));
  }

  private void renderProductUpcomingDeal() {
    final LinearLayout upcomingDealView = (LinearLayout) findViewById(R.id.upcoming_deal);
    upcomingDealView.setVisibility(product.getUpcomingDealVisibilityStatus());
    TextView percentage = (TextView) findViewById(R.id.percentage);
    percentage.setText(product.getUpcomingDeal(stringResolver));
  }

  @Override
  public void renderImageFor(ImageView view, Bitmap response) {
    view.setImageBitmap(response);
  }
}
