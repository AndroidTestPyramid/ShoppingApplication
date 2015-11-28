package droidcon.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseDeserializerFactory {

  public static ResponseDeserializer<String> jsonParser(){
    return new ResponseDeserializer<String>() {
      @Override
      public String deserialize(InputStream content) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        StringBuilder result = new StringBuilder();
        String line;
        try {
          while ((line = reader.readLine()) != null) {
            result.append(line);
          }
        } catch (IOException readerException) {
          readerException.printStackTrace();
        }
        return result.toString();
      }
    };
  }

  public static ResponseDeserializer<Bitmap> bitmapParser() {
    return new ResponseDeserializer<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream content) {
        return BitmapFactory.decodeStream(content);
      }
    };
  }
}
