package droidcon.shopping.view;

import java.util.List;

import droidcon.shopping.model.Product;
import droidcon.shopping.viewmodel.ProductViewModel;

public interface ProductListView {
  void render(List<Product> products, List<ProductViewModel> productViewModels);
  void dismissLoader();
  void showErrorDialog(String string);
}
