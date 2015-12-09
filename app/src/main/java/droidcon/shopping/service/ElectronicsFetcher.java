package droidcon.shopping.service;

import droidcon.login.service.EnvironmentManager;
import droidcon.service.APIClient;

public class ElectronicsFetcher extends ProductsFetcherService {

  public static final String ELECTRONICS_URL = "%s/sample_images/droidcon_electronics.json";

  public ElectronicsFetcher(APIClient apiClient){
    super(String.format(ELECTRONICS_URL, EnvironmentManager.getInstance().environment()), apiClient);
  }
}
