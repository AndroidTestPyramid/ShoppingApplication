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

import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;

import droidcon.cart.R;
import droidcon.service.APIClient;
import droidcon.service.APIClient.RequestType;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializer;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.model.Product;

public abstract class ProductsBaseFragment extends Fragment {

  public static final String PRODUCT_KEY = "droidcon.cart.current_product";
  private ProgressDialog progressDialog;
  private GridView gridView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fetchProducts();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.products_layout, container, false);
    progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
    gridView = (GridView) view.findViewById(R.id.grid_view);
    return view;
  }

  private void fetchProducts() {
    new APIClient().execute(RequestType.GET, getURL(), productsCallback());
  }

  abstract public String getURL();

  private ResponseCallback<ArrayList<Product>> productsCallback() {
    return new ResponseCallback<ArrayList<Product>>() {
      @Override
      public ArrayList<Product> deserialize(InputStream response) {
        final TypeToken<ArrayList<Product>> typeToken = new TypeToken<ArrayList<Product>>() {
        };
        ResponseDeserializer<ArrayList<Product>> objectResponseDeserializer = ResponseDeserializerFactory.objectDeserializer(typeToken.getType());
        return objectResponseDeserializer.deserialize(response);
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
    gridView.setAdapter(new ShoppingItemsListAdapter(products));

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
