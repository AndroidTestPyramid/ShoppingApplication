package droidcon.shopping.presenter;

import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import droidcon.cart.R;
import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializer;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.model.Product;
import droidcon.shopping.service.ProductsFetcherService;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductListView;
import droidcon.shopping.viewmodel.ProductViewModel;

public class ProductListPresenter {
  private final ProductListView productListView;
  private final ProductsFetcherService productsFetcherService;
  private final StringResolver stringResolver;

  public ProductListPresenter(ProductListView productListView, ProductsFetcherService productsFetcherService, StringResolver stringResolver) {
    this.productListView = productListView;
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
        final TypeToken<ArrayList<Product>> typeToken = new TypeToken<ArrayList<Product>>() {
        };
        ResponseDeserializer<ArrayList<Product>> objectResponseDeserializer = ResponseDeserializerFactory.objectDeserializer(typeToken.getType());
        return objectResponseDeserializer.deserialize(response);
      }

      @Override
      public void onSuccess(ArrayList<Product> response) {
        productListView.dismissLoader();
        final List<ProductViewModel> productViewModels = new ArrayList<>();
        for (Product product : response) {
          productViewModels.add(new ProductViewModel(product));
        }
        productListView.render(productViewModels);
      }

      @Override
      public void onError(Exception exception) {
        productListView.dismissLoader();
        productListView.showErrorDialog(stringResolver.getString(R.string.technical_difficulty));
      }
    };
  }
}
