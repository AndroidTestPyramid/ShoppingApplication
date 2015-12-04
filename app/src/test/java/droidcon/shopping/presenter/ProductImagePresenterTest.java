package droidcon.shopping.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;

import droidcon.service.ResponseCallback;
import droidcon.service.ResponseDeserializer;
import droidcon.service.ResponseDeserializerFactory;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductImageView;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductImagePresenterTest {

  private ByteArrayInputStream byteArrayInputStream;

  @Test
  public void shouldInvokeRenderImageOnSuccessfullyFetchingImage(){
    final ImageFetcher imageFetcher = mock(ImageFetcher.class);
    final Bitmap bitmap = mock(Bitmap.class);
    final ProductImageView productImageView = mock(ProductImageView.class);
    final ImageView imageView = mock(ImageView.class);

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[1];
        callback.onSuccess(bitmap);
        return null;
      }
    }).when(imageFetcher).execute(eq(""), Matchers.<ResponseCallback<Bitmap>>any());

    ProductImagePresenter productImagePresenter = new ProductImagePresenter(productImageView, imageFetcher);
    productImagePresenter.fetchImageFor(imageView, "");

    verify(productImageView).renderImageFor(imageView, bitmap);
  }

  @Ignore("Either use powermock or make the factory abstract")
  public void shouldInvokeResponseDeserializer(){
    final ImageFetcher imageFetcher = mock(ImageFetcher.class);
    final ResponseDeserializerFactory responseDeserializerFactory = mock(ResponseDeserializerFactory.class, RETURNS_DEEP_STUBS);
    final ResponseDeserializer responseDeserializer = mock(ResponseDeserializer.class);
    final ProductImageView productImageView = mock(ProductImageView.class);
    final ImageView imageView = mock(ImageView.class);

    when(responseDeserializerFactory.bitmapDeserializer()).thenReturn(responseDeserializer);

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[1];
        byteArrayInputStream = new ByteArrayInputStream("".getBytes());
        callback.deserialize(byteArrayInputStream);
        return null;
      }
    }).when(imageFetcher).execute(eq(""), Matchers.<ResponseCallback<Bitmap>>any());

    ProductImagePresenter productImagePresenter = new ProductImagePresenter(productImageView, imageFetcher);
    productImagePresenter.fetchImageFor(imageView, "");

    verify(responseDeserializer).deserialize(byteArrayInputStream);
  }
}