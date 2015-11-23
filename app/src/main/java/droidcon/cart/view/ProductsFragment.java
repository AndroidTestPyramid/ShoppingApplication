package droidcon.cart.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import androidplugins.Callback;
import droidcon.cart.R;
import droidcon.cart.model.Product;
import droidcon.cart.service.ProductServiceClient;

public class ProductsFragment extends Fragment {

  private ProgressDialog progressDialog;
  private GridView gridView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ProductServiceClient productServiceClient = new ProductServiceClient();
    productServiceClient.getProducts(productsCallback());
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.products_layout, container, false);
    progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true, true);
    gridView = (GridView) view.findViewById(R.id.grid_view);
    return view;
  }

  private Callback<ArrayList<Product>> productsCallback() {
    return new Callback<ArrayList<Product>>() {
      @Override
      public void execute(ArrayList<Product> products) {
        progressDialog.dismiss();
        renderProducts(gridView, products);
      }
    };
  }

  private void renderProducts(GridView gridView, final ArrayList<Product> products) {
    gridView.setAdapter(new ShoppingItemsListAdapter(getActivity(), products));
  }
}
