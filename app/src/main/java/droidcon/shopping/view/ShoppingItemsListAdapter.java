package droidcon.shopping.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import droidcon.cart.R;
import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductImagePresenter;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.viewmodel.ProductViewModel;

public class ShoppingItemsListAdapter extends BaseAdapter implements ProductImageView {
  private final StringResolver stringResolver;
  public List<ProductViewModel> products = new ArrayList<>();
  private Context context;

  public ShoppingItemsListAdapter(List<ProductViewModel> products, Context context) {
    this.products = products;
    stringResolver = new StringResolver(context);
    this.context = context;
  }

  @Override
  public int getCount() {
    return products.size();
  }

  @Override
  public Object getItem(int position) {
    return products.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
    }
    ProductViewModel product = products.get(position);
    renderProductTitle(convertView, product);
    fetchImage(convertView, product);
    renderProductCost(convertView, product);
    renderProductUpcomingDeal(convertView, product);
    renderProductPopularityStatus(convertView, product);
    return convertView;
  }

  private void renderProductPopularityStatus(View convertView, ProductViewModel product) {
    TextView popularityView = (TextView) convertView.findViewById(R.id.popularity);
    popularityView.setText(product.getPopularityLabel(stringResolver));
    popularityView.setTextColor(context.getResources().getColor(product.getPopularityTextColor()));
    popularityView.setVisibility(product.getPopularityVisibilityStatus());
  }

  private void renderProductUpcomingDeal(View convertView, ProductViewModel product) {
    final LinearLayout upcomingDealView = (LinearLayout) convertView.findViewById(R.id.upcoming_deal);
    upcomingDealView.setVisibility(product.getUpcomingDealVisibilityStatus());
    TextView percentage = (TextView) convertView.findViewById(R.id.percentage);
    percentage.setText(product.getUpcomingDeal(stringResolver));
  }

  private void renderProductTitle(View convertView, ProductViewModel product) {
    TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
    titleTextView.setText(product.getTitle());
  }

  //TODO: look at removing imageView dependency
  private void fetchImage(View convertView, ProductViewModel product) {
    new ProductImagePresenter(this, new ImageFetcher(new APIClient())).fetchImageFor((ImageView) convertView.findViewById(R.id.imageView), product.getImageUrl());
  }

  private void renderProductCost(View convertView, ProductViewModel product) {
    TextView costTextView = (TextView) convertView.findViewById(R.id.cost);
    costTextView.setText(product.getPrice(stringResolver));
  }

  @Override
  public void renderImageFor(ImageView view, Bitmap response) {
    ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
    imageView.setImageBitmap(response);
  }
}
