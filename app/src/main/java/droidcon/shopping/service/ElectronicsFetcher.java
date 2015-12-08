package droidcon.shopping.service;

import droidcon.service.APIClient;

public class ElectronicsFetcher extends ProductsFetcherService {

  public static final String ELECTRONICS_URL = "http://xplorationstudio.com/sample_images/droidcon_electronics.json";

  public ElectronicsFetcher(APIClient apiClient){
    super(ELECTRONICS_URL, apiClient);
  }
}
