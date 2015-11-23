package androidplugins.contentfetcher;

import android.os.AsyncTask;
import android.util.Log;
import androidplugins.Callback;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContentFetcher extends AsyncTask<String, Void, String> {

	private Callback<String> callback;
	private String requestType;

	public ContentFetcher(Callback<String> callback, String RequestType) {
		this.callback = callback;
		requestType = RequestType;
	}


	@Override
	protected String doInBackground(String... url) {
		return response(url[0]);
	}

	public String response(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpRequestBase httpRequest = httpRequest(url);
		String result = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode >= 200 && statusCode <= 210) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream content = httpEntity.getContent();
				result = toString(content);
			}
		} catch (IOException httpResponseError) {
			Log.e("HTTP Response", "IO error");
			return "404 error";
		}
		return result;
	}

	private HttpRequestBase httpRequest(String url) {
		if (requestType.equals("GET")) return new HttpGet(url);
		else if (requestType.equals("POST")) return new HttpPost(url);
		else return null;
	}


	@Override
	protected void onPostExecute(String resultString) {
		super.onPostExecute(resultString);
		Log.d(this.getClass().getName(), "Content Data: " + resultString);
		callback.execute(resultString);
	}

	private String toString(InputStream content) {
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
}
