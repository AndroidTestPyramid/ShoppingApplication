package droidcon.shopping.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import droidcon.cart.R;
import droidcon.service.ResponseCallback;
import droidcon.shopping.model.Product;
import droidcon.shopping.service.ProductsFetcherService;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductResultsView;
import droidcon.shopping.viewmodel.ProductViewModel;

import static droidcon.service.ResponseDeserializerFactory.jsonParser;

public class ProductResultsPresenter {
  private final ProductResultsView productResultsView;
  private final ProductsFetcherService productsFetcherService;
  private final StringResolver stringResolver;

  public ProductResultsPresenter(ProductResultsView productResultsView, ProductsFetcherService productsFetcherService, StringResolver stringResolver) {
    this.productResultsView = productResultsView;
    this.productsFetcherService = productsFetcherService;
    this.stringResolver = stringResolver;
  }

  public void fetch() {
    productsFetcherService.execute(productsCallback());
  }

  private ResponseCallback<ArrayList<Product>> productsCallback() {
    return new ResponseCallback<ArrayList<Product>>() {
      @Override
      public ArrayList<Product> deserialize(InputStream response) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
        return gson.fromJson(jsonParser().deserialize(response), listType);
      }

      //TODO: To test view model creation - need a better approach
      @Override
      public void onSuccess(ArrayList<Product> response) {
        productResultsView.dismissLoader();
        final List<ProductViewModel> productViewModels = new ArrayList<>();
        for (Product product : response) {
          productViewModels.add(new ProductViewModel(product));
        }
        productResultsView.render(productViewModels);
      }

      @Override
      public void onError(Exception exception) {
        productResultsView.dismissLoader();
        productResultsView.showErrorDialog(stringResolver.getString(R.string.technical_difficulty));
      }
    };
  }
}
