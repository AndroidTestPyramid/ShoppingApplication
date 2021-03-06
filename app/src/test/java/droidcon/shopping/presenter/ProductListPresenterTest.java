package droidcon.shopping.presenter;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import droidcon.cart.R;
import droidcon.service.ResponseCallback;
import droidcon.shopping.model.Product;
import droidcon.shopping.service.ProductsFetcherService;
import droidcon.shopping.util.StringResolver;
import droidcon.shopping.view.ProductListView;
import droidcon.shopping.viewmodel.ProductViewModel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductListPresenterTest {

  @Captor
  private ArgumentCaptor<ArrayList<ProductViewModel>> argumentCaptor;
  private ProductListView productListViewMock;
  private ProductsFetcherService productsFetcherServiceMock;
  private ProductListPresenter productListPresenter;
  private StringResolver stringResolver;

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
    productListViewMock = mock(ProductListView.class);
    productsFetcherServiceMock = mock(ProductsFetcherService.class);
    stringResolver = mock(StringResolver.class);

    productListPresenter = new ProductListPresenter(productListViewMock, productsFetcherServiceMock, stringResolver);
    when(stringResolver.getString(R.string.technical_difficulty)).thenReturn("There is some technical difficulties");
  }

  @Test
  public void shouldCreateProductViewModel(){
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[0];

        final String fileToString = FileUtils.readFileToString(new File("src/test/resources/products.json"));

        final InputStream byteArrayInputStream = new ByteArrayInputStream(fileToString.getBytes());
        final ArrayList<Product> deserializedObjects = (ArrayList<Product>) callback.deserialize(byteArrayInputStream);
        callback.onSuccess(deserializedObjects);
        return null;
      }
    }).when(productsFetcherServiceMock).execute(Matchers.<ResponseCallback<ArrayList<Product>>>any());

    productListPresenter.fetch();

    verify(productListViewMock).render(argumentCaptor.capture());

    final ArrayList<ProductViewModel> viewModels = argumentCaptor.getValue();

    assertThat("SONY SMARTWATCH (BLACK)", is(viewModels.get(0).getTitle()));
    assertThat("Sony watch desc", is(viewModels.get(0).getDescription()));
    assertThat("http://xplorationstudio.com/sample_images/watch_2.jpeg", is(viewModels.get(0).getImageUrl()));
    assertThat(1, is(viewModels.get(0).getProductId()));
  }

  @Test
  public void checkCallSequenceInFailureOfFetchingProducts(){
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[0];
        callback.onError(new Exception());
        return null;
      }
    }).when(productsFetcherServiceMock).execute((Matchers.<ResponseCallback<ArrayList<Product>>>any()));

    productListPresenter.fetch();

    InOrder inOrder = inOrder(productListViewMock);
    inOrder.verify(productListViewMock).dismissLoader();
    inOrder.verify(productListViewMock).showErrorDialog(stringResolver.getString(R.string.technical_difficulty));
  }

  @Test
  public void checkCallSequenceOnSuccessOfFetchingProducts(){
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[0];
        callback.onSuccess(new ArrayList<Product>());
        return null;
      }
    }).when(productsFetcherServiceMock).execute((Matchers.<ResponseCallback<ArrayList<Product>>>any()));

    productListPresenter.fetch();

    InOrder inOrder = inOrder(productListViewMock);
    inOrder.verify(productListViewMock).dismissLoader();
    inOrder.verify(productListViewMock).render(new ArrayList<ProductViewModel>());
  }
}