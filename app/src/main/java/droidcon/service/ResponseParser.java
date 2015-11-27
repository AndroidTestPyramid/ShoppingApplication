package droidcon.service;

import java.io.InputStream;

public interface ResponseParser<T> {
  T parse(InputStream content);
}
