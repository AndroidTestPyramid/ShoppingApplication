package droidcon.shopping.service;

import org.junit.Test;

import droidcon.service.APIClient;
import droidcon.service.ResponseCallback;

import static droidcon.service.APIClient.RequestType.GET;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElectronicsFetcherTest {

  public static final String ELECTRONICS_URL = "http://xplorationstudio.com/sample_images/droidcon_electronics.json";

  @Test
  public void shouldInvokeExecuteRequestWithElectronicsUrl(){
    APIClient apiClientMock = mock(APIClient.class);

    ElectronicsFetcher electronicsFetcher = new ElectronicsFetcher(apiClientMock);

    ResponseCallback responseCallback = mock(ResponseCallback.class);
    electronicsFetcher.execute(responseCallback);

    verify(apiClientMock).execute(eq(GET), eq(ELECTRONICS_URL), eq(responseCallback));
  }
}