package droidcon.shopping.service;

import android.graphics.Bitmap;

import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;

public class ImageFetcher {

  private final APIClient apiClient;

  public ImageFetcher(APIClient apiClient) {
    this.apiClient = apiClient;
  }

  public void execute(String imageUrl, ResponseCallback<Bitmap> bitmapResponseCallback) {
    apiClient.execute(APIClient.RequestType.GET, imageUrl, bitmapResponseCallback);
  }
}
