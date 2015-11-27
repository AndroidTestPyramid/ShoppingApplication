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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import droidcon.cart.R;
import droidcon.service.APIClient;
import droidcon.service.APIClient.RequestType;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseParserFactory;
import droidcon.shopping.model.Product;

public class ShoppingItemsListAdapter extends BaseAdapter {
  private final Context context;
  public List<Product> products = new ArrayList<>();

  public ShoppingItemsListAdapter(Context context, List<Product> products) {
    this.context = context;
    this.products = products;
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
      convertView = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
    }
    Product product = products.get(position);
    renderProductTitle(convertView, product);
    renderProductImage(convertView, product);
    renderProductCost(convertView, product);
    renderUpcomingDeal(convertView, product);
    return convertView;
  }

  private void renderUpcomingDeal(View convertView, Product product) {
    if(product.getUpcomingDeal() != 0){
      final LinearLayout upcomingDealView = (LinearLayout)convertView.findViewById(R.id.upcoming_deal);
      upcomingDealView.setVisibility(View.VISIBLE);
      TextView percentage = (TextView) convertView.findViewById(R.id.percentage);
      percentage.setText(String.format("%d%s", product.getUpcomingDeal(), context.getString(R.string.percentage_sign)));
    }
  }

  private void renderProductTitle(View convertView, Product product) {
    TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
    titleTextView.setText(product.getTitle());
  }

  private void renderProductImage(View convertView, Product product) {
    ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
    fetchBitmap(imageView, product.getImageUrl());
  }

  private void renderProductCost(View convertView, Product product) {
    TextView costTextView = (TextView) convertView.findViewById(R.id.cost);
    costTextView.setText(String.format("%s%d", context.getString(R.string.cost), product.getPrice()));
  }

  private void fetchBitmap(ImageView imageView, String imageUrl) {
    APIClient apiClient = new APIClient(RequestType.GET, bitmapCallback(imageView));
    apiClient.execute(imageUrl);
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
