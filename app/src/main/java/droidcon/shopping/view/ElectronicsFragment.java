package droidcon.shopping.view;

import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductListPresenter;
import droidcon.shopping.service.ElectronicsFetcher;
import droidcon.shopping.util.StringResolver;

public class ElectronicsFragment extends ProductsBaseFragment implements ProductListView {

  public void fetchProducts() {
    new ProductListPresenter(this, new ElectronicsFetcher(new APIClient()), getResources()).fetch();
  }
}
