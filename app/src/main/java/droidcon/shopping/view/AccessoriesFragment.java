package droidcon.shopping.view;

import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductResultsPresenter;
import droidcon.shopping.service.AccessoriesFetcher;
import droidcon.shopping.util.StringResolver;

public class AccessoriesFragment extends ProductsBaseFragment implements ProductResultsView {

  public void fetchProducts() {
    new ProductResultsPresenter(this, new AccessoriesFetcher(new APIClient()), new StringResolver(getActivity())).fetch();
  }
}
