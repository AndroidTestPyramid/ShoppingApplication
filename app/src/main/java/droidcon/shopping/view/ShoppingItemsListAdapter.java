package droidcon.shopping.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
    TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
    Product product = products.get(position);
    titleTextView.setText(product.getTitle());
    APIClient apiClient = new APIClient(RequestType.GET, bitmapCallback(imageView));
    apiClient.execute(product.getImageUrl());

    return convertView;
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
}
