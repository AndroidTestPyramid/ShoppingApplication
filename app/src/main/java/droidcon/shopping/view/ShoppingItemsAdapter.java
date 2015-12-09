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
import droidcon.shopping.model.Product;
import droidcon.shopping.presenter.ImagePresenter;
import droidcon.shopping.presenter.ProductPresenter;
import droidcon.shopping.repository.ImageRepository;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.viewmodel.ProductViewModel;

public class ShoppingItemsAdapter extends BaseAdapter implements ProductView {
  public List<ProductViewModel> productViewModels = new ArrayList<>();
  private final List<Product> products;
  private Context context;
  private ViewHolderItem viewHolder;

  public ShoppingItemsAdapter(List<ProductViewModel> productViewModels, List<Product> products, Context context) {
    this.productViewModels = productViewModels;
    this.products = products;
    this.context = context;
  }

  @Override
  public int getCount() {
    return productViewModels.size();
  }

  @Override
  public Object getItem(int position) {
    return productViewModels.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
      viewHolder = new ViewHolderItem();
      viewHolder.popularityLabelTextView = (TextView) convertView.findViewById(R.id.popularity);
      viewHolder.upcomingDeal = (TextView) convertView.findViewById(R.id.percentage);
      viewHolder.upcomingDealLayout = (LinearLayout) convertView.findViewById(R.id.upcoming_deal);
      viewHolder.productTitleTextView = (TextView) convertView.findViewById(R.id.title);
      viewHolder.productCostTextView = (TextView) convertView.findViewById(R.id.cost);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolderItem) convertView.getTag();
    }
    final ImageFetcher imageFetcher = new ImageFetcher(new APIClient());
    final ImageRepository imageRepository = new ImageRepository(context);

    ProductPresenter productPresenter = new ProductPresenter(this, products.get(position), new ImagePresenter(this, imageFetcher, imageRepository), context.getResources());

    productPresenter.renderView();
    productPresenter.renderImageFor((ImageView) convertView.findViewById(R.id.imageView));

    return convertView;
  }

  @Override
  public void renderProductPopularityStatus(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus) {
    final TextView popularityLabelTextView = viewHolder.popularityLabelTextView;
    popularityLabelTextView.setText(popularityLabel);
    popularityLabelTextView.setTextColor(context.getResources().getColor(popularityTextColor));
    popularityLabelTextView.setVisibility(popularityVisibilityStatus);
  }

  @Override
  public void renderProductUpcomingDeal(int upcomingDealVisibilityStatus, String upcomingDeal) {
    viewHolder.upcomingDealLayout.setVisibility(upcomingDealVisibilityStatus);
    viewHolder.upcomingDeal.setText(upcomingDeal);
  }

  @Override
  public void renderProductTitle(String title) {
    viewHolder.productTitleTextView.setText(title);
  }

  @Override
  public void renderProductCost(String price) {
    viewHolder.productCostTextView.setText(price);
  }

  @Override
  public void renderImage(ImageView imageView, Bitmap response) {
    imageView.setImageBitmap(response);
  }

  public Product getProductAt(int position) {
    return products.get(position);
  }

  static class ViewHolderItem {
    TextView popularityLabelTextView;
    LinearLayout upcomingDealLayout;
    TextView upcomingDeal;
    TextView productTitleTextView;
    TextView productCostTextView;
  }
}
