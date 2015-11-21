package droidcon.cart.service;

import java.util.ArrayList;

import androidplugins.Callback;
import droidcon.cart.model.Product;
import droidcon.cart.service.ProductsParser;

public class ProductServiceClient {

  public void getProducts(Callback<ArrayList<Product>> productsCallback) {
    new androidplugins.contentfetcher.ContentFetcher(responseCallback(productsCallback), "GET").execute("http://xplorationstudio.com/sample_images/products_json.json");
  }

  private Callback<String> responseCallback(final Callback<ArrayList<Product>> productsCallback) {
    return new Callback<String>() {
      @Override
      public void execute(String strJSONData) {
        productsCallback.execute(new ProductsParser().parseProducts(strJSONData));
      }
    };
  }
}