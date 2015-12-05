package droidcon.rule;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import droidcon.mockhttp.MockHTTPDispatcher;
import droidcon.mockhttp.MockRequest;


public class MockWebServerRule implements TestRule {

  private MockHTTPDispatcher mockHTTPDispatcher;
  private MockWebServer mockWebServer;

  public MockWebServerRule() {
    mockWebServer = new MockWebServer();
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new MockHTTPServerStatement(statement);
  }

  public MockRequest mockResponse(String path, String httpMethod, String response) {
    MockRequest mockRequest = new MockRequest(path, httpMethod, response);
    mockHTTPDispatcher.mock(mockRequest);
    return mockRequest;
  }

  private class MockHTTPServerStatement extends Statement {

    private Statement base;

    public MockHTTPServerStatement(Statement base) {
      this.base = base;
    }

    @Override
    public void evaluate() throws Throwable {
      mockHTTPDispatcher = new MockHTTPDispatcher();
      mockWebServer.setDispatcher(mockHTTPDispatcher);
      mockWebServer.start();
      try {
        this.base.evaluate();
      } finally {
        mockWebServer.shutdown();
      }
    }
  }
}
