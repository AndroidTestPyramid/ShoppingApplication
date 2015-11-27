package droidcon.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseParserFactory {

  public static ResponseParser<String> jsonParser(){
    return new ResponseParser<String>() {
      @Override
      public String parse(InputStream content) {
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

  public static ResponseParser<Bitmap> bitmapParser() {
    return new ResponseParser<Bitmap>() {
      @Override
      public Bitmap parse(InputStream content) {
        return BitmapFactory.decodeStream(content);
      }
    };
  }
}
