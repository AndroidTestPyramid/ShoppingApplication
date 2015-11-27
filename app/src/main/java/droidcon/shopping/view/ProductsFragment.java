package droidcon.shopping.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.InputStream;
import java.util.ArrayList;

import droidcon.cart.R;
import droidcon.service.APIClient;
import droidcon.service.APIClient.RequestType;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseParserFactory;
import droidcon.shopping.model.Product;
import droidcon.shopping.service.ProductsParser;

import static droidcon.shopping.Constants.PRODUCT_KEY;

public class ProductsFragment extends Fragment {

  public static final String PRODUCTS_URL = "http://xplorationstudio.com/sample_images/products.json";
  private ProgressDialog progressDialog;
  private GridView gridView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new APIClient(RequestType.GET, productsCallback()).execute(PRODUCTS_URL);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.products_layout, container, false);
    progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true, true);
    gridView = (GridView) view.findViewById(R.id.grid_view);
    return view;
  }

  private ResponseCallback<ArrayList<Product>> productsCallback() {
    return new ResponseCallback<ArrayList<Product>>() {
      @Override
      public ArrayList<Product> parse(InputStream response) {
        return new ProductsParser().parseProducts(ResponseParserFactory.jsonParser().parse(response));
      }

      @Override
      public void onSuccess(ArrayList<Product> response) {
        progressDialog.dismiss();
        renderProducts(gridView, response);
      }

      @Override
      public void onError(Exception exception) {
        progressDialog.dismiss();
        new AlertDialog.Builder(getActivity()).setMessage(R.string.technical_difficulty).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            getActivity().finish();
          }
        });
      }
    };
  }

  private void renderProducts(GridView gridView, final ArrayList<Product> products) {
    gridView.setAdapter(new ShoppingItemsListAdapter(getActivity(), products));

    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Product product = (Product) adapterView.getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(PRODUCT_KEY, product);
        startActivity(intent);
      }
    });
  }
}
