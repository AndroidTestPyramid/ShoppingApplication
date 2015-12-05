package droidcon.mockhttp;

public class MockRequest {
  String path;
  String httpMethod;
  String response;

  public MockRequest(String path, String httpMethod, String response) {
    this.path = path;
    this.httpMethod = httpMethod;
    this.response = response;
  }
}
