package droidcon.shopping.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import droidcon.cart.R;
import droidcon.service.APIClient;
import droidcon.shopping.presenter.ProductListPresenter;
import droidcon.shopping.service.ElectronicsFetcher;

public class ElectronicsFragment extends ProductsBaseFragment implements ProductListView {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);
    view.setContentDescription(getString(R.string.electronics));
    return view;

  }

  public void fetchProducts() {
    new ProductListPresenter(this, new ElectronicsFetcher(new APIClient()), getResources()).fetch();
  }
}
