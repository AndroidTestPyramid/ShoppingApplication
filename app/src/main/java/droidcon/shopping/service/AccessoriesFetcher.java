package droidcon.shopping.service;

import droidcon.login.service.EnvironmentManager;
import droidcon.service.APIClient;

public class AccessoriesFetcher extends ProductsFetcherService {

  public static final String ACCESSORIES_URL = "%s/sample_images/droidcon_accessories.json";

  public AccessoriesFetcher(APIClient apiClient){
    super(String.format(ACCESSORIES_URL, EnvironmentManager.getInstance().environment()), apiClient);
  }
}
