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
import droidcon.shopping.presenter.ImagePresenter;
import droidcon.shopping.presenter.ProductDetailsPresenter;
import droidcon.shopping.presenter.ProductPresenter;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.viewmodel.ProductViewModel;

import static droidcon.shopping.view.ProductsBaseFragment.PRODUCT_KEY;

public class ProductDetailsActivity extends AppCompatActivity implements ProductDetailView, ProductView {

  private ProductViewModel product;
  private ProductDetailsPresenter productDetailsPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
    final StringResolver stringResolver = new StringResolver(this);

    ImagePresenter imagePresenter = new ImagePresenter(this, new ImageFetcher(new APIClient()));
    imagePresenter.fetchImageFor(product.getImageUrl());

    ProductPresenter productPresenter = new ProductPresenter(this, stringResolver);
    productPresenter.renderViewFor(product);

    productDetailsPresenter = new ProductDetailsPresenter(this, stringResolver);
    productDetailsPresenter.renderDetailedView(product);
  }

  public void addToCart(View view) {
    productDetailsPresenter.saveProduct(new ProductInCart(product.getProductId()));
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

  @Override
  public void renderProductPopularityStatus(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus) {
    final TextView popularityLabelTextView = (TextView) findViewById(R.id.popularity);
    popularityLabelTextView.setText(popularityLabel);
    popularityLabelTextView.setTextColor(getResources().getColor(popularityTextColor));
    popularityLabelTextView.setVisibility(popularityVisibilityStatus);
  }

  @Override
  public void renderProductUpcomingDeal(int upcomingDealVisibilityStatus, String upcomingDeal) {
    final LinearLayout upcomingDealLayout = (LinearLayout) findViewById(R.id.upcoming_deal);
    final TextView upcomingDealTextView = (TextView) findViewById(R.id.percentage);
    upcomingDealLayout.setVisibility(upcomingDealVisibilityStatus);
    upcomingDealTextView.setText(upcomingDeal);
  }

  @Override
  public void renderProductTitle(String title) {
    final TextView titleTextView = (TextView) findViewById(R.id.product_title);
    titleTextView.setText(title);
  }

  @Override
  public void renderProductCost(String price) {
     TextView productCostTextView = (TextView) findViewById(R.id.cost);
     productCostTextView.setText(price);
  }

  @Override
  public void renderImage(Bitmap response) {
    ImageView imageView = (ImageView) findViewById(R.id.product_image);
    imageView.setImageBitmap(response);
  }

  @Override
  public void setDescription(String description) {
    final TextView descriptionTextView = (TextView) findViewById(R.id.product_description);
    descriptionTextView.setText(description);
  }

  @Override
  public void showToastWithMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}