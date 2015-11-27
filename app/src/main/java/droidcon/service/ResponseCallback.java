package droidcon.service;

import java.io.InputStream;

public interface ResponseCallback<T> {
    T deserialize(InputStream response);
    void onSuccess(T response);
    void onError(Exception exception);
}
