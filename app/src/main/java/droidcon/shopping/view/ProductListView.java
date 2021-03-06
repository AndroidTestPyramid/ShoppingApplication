package droidcon.shopping.view;

import java.util.List;

import droidcon.shopping.viewmodel.ProductViewModel;

public interface ProductListView {
  void render(List<ProductViewModel> products);
  void dismissLoader();
  void showErrorDialog(String string);
}
