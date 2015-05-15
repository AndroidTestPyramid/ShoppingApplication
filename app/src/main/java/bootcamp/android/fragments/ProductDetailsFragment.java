package bootcamp.android.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidplugins.Callback;
import androidplugins.imagefetcher.ImageFetcher;
import bootcamp.android.R;
import bootcamp.android.models.Product;

import static bootcamp.android.constants.Constants.PRODUCT_KEY;

public class ProductDetailsFragment extends Fragment {
  @Override
  public View onCreateView(android.view.LayoutInflater inflater, @android.support.annotation.Nullable android.view.ViewGroup container, @android.support.annotation.Nullable android.os.Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return getPopulatedView(inflater, container, getArguments());
  }

  private View getPopulatedView(LayoutInflater layoutInflater, ViewGroup parent, Bundle extraArguments) {
    View productDetailsView = layoutInflater.inflate(R.layout.product_details, parent, false);
    Product product = extraArguments.getParcelable(PRODUCT_KEY);
    TextView imageTitle = (TextView) productDetailsView.findViewById(R.id.product_title);
    imageTitle.setText(product.getTitle());
    ImageView imageView = (ImageView) productDetailsView.findViewById(R.id.product_image);
    ImageFetcher imageFetcher = new ImageFetcher(bitmapCallback(imageView), getActivity());
    imageFetcher.execute(product.getImageUrl());
    TextView issueDescription = (TextView) productDetailsView.findViewById(R.id.product_description);
    issueDescription.setText(product.getDescription());
    return productDetailsView;
  }

  private Callback<Bitmap> bitmapCallback(final ImageView imageView) {
    return new Callback<Bitmap>() {
      @Override
      public void execute(Bitmap object) {
        imageView.setImageBitmap(object);
      }
    };
  }

}