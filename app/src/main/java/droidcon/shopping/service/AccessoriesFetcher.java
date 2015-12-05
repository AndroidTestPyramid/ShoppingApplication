package droidcon.shopping.service;

import droidcon.service.APIClient;

public class AccessoriesFetcher extends ProductsFetcherService {

  public static final String ACCESSORIES_URL = "http://xplorationstudio.com/sample_images/accessories.json";

  public AccessoriesFetcher(APIClient apiClient){
    super(ACCESSORIES_URL, apiClient);
  }
}
