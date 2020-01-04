package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

public class CustomUE1 {

	public UrlFactory getUrlFactory() {
		return new UrlFactory();
	}

	public Url getUrl(String path) {
		return new UrlFactory().getWebUrl(path);
	}

	public void helloWorld() {
	}
}