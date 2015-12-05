package droidcon.shopping.view;

import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductListPresenter;
import droidcon.shopping.service.AccessoriesFetcher;
import droidcon.shopping.util.StringResolver;

public class AccessoriesFragment extends ProductsBaseFragment implements ProductListView {

  public void fetchProducts() {
    new ProductListPresenter(this, new AccessoriesFetcher(new APIClient()), new StringResolver(getActivity())).fetch();
  }
}
