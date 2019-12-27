package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class UEB3 {

	public void helloWorld() {

	}

	public Request getRequest(InputStream inputStream) {
		return new RequestFactory().getWebRequest(inputStream);
	}

	public Response getResponse() {
		return null;
	}

	public Plugin getTestPlugin() {
		return null;
	}
}
