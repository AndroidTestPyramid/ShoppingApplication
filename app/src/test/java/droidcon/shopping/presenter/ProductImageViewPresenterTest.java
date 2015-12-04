package droidcon.shopping.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import droidcon.service.ResponseCallback;
import droidcon.shopping.service.ImageFetcher;
import droidcon.shopping.view.ProductImageView;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductImageViewPresenterTest {

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

    ProductImageViewPresenter productImageViewPresenter = new ProductImageViewPresenter(productImageView, imageFetcher);
    productImageViewPresenter.fetchImageFor(imageView, "");

    verify(productImageView).renderImageFor(imageView, bitmap);
  }

  @Test
  public void shouldInvokeRenderImageSuccessfullyAfterDeserialization(){
    final ImageFetcher imageFetcher = mock(ImageFetcher.class);
    final ProductImageView productImageView = mock(ProductImageView.class);
    final ImageView imageView = mock(ImageView.class);
    final Bitmap[] bitmap = new Bitmap[1];

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[1];
        final String fileToString = FileUtils.readFileToString(new File("src/test/resources/ic_launcher.png"));
        final InputStream byteArrayInputStream = new ByteArrayInputStream(fileToString.getBytes());
        bitmap[0] = (Bitmap) callback.deserialize(byteArrayInputStream);
        callback.onSuccess(bitmap[0]);
        return null;
      }
    }).when(imageFetcher).execute(eq(""), Matchers.<ResponseCallback<Bitmap>>any());

    ProductImageViewPresenter productImageViewPresenter = new ProductImageViewPresenter(productImageView, imageFetcher);
    productImageViewPresenter.fetchImageFor(imageView, "");

    verify(productImageView).renderImageFor(imageView, bitmap[0]);
  }
}