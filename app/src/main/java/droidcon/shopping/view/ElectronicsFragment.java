package droidcon.shopping.view;

import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductResultsPresenter;
import droidcon.shopping.service.ElectronicsFetcher;
import droidcon.shopping.util.StringResolver;

public class ElectronicsFragment extends ProductsBaseFragment implements ProductResultsView {

  public void fetchProducts() {
    new ProductResultsPresenter(this, new ElectronicsFetcher(new APIClient()), new StringResolver(getActivity())).fetch();
  }
}
