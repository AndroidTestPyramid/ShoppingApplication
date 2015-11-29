package droidcon.shopping.service;

import java.util.ArrayList;

import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;
import droidcon.shopping.model.Product;

import static droidcon.service.APIClient.RequestType.GET;

public class ProductsFetcherService {
  private final String request;
  private final APIClient apiClient;

  public ProductsFetcherService(String request, APIClient apiClient) {
    this.request = request;
    this.apiClient = apiClient;
  }

  public void execute(ResponseCallback<ArrayList<Product>> productsCallback) {
    apiClient.execute(GET, request, productsCallback);
  }
}
