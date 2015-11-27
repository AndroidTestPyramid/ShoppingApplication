package droidcon.service;

import java.io.InputStream;

public interface ResponseCallback<T> {
    T parse(InputStream response);
    void onSuccess(T response);
    void onError(Exception exception);
}
