package droidcon.shopping.util;

import android.content.Context;

public class StringResolver {

  private final Context context;
  public StringResolver(Context context) {
    this.context = context;
  }
  public String getString(int resourceId) {
    return context.getString(resourceId);
  }
}
