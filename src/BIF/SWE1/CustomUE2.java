package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.plugins.ErrorPlugin;
import BIF.SWE1.plugins.StaticGetPlugin;
import BIF.SWE1.plugins.TemperaturePlugin;
import BIF.SWE1.plugins.ToLowerPlugin;

import java.io.InputStream;

public class CustomUE2 {

	public void helloWorld() {

	}

	public Request getRequest(InputStream inputStream) {
		return new RequestFactory().getWebRequest(inputStream);
	}

	public PluginManager getPluginManager() {
		return WebPluginManager.getPluginManager();
	}

	public Plugin getErrorPlugin() {
		return new ErrorPlugin();
	}

	public Plugin getStaticFilePlugin() {
		return new StaticGetPlugin();
	}

	public Plugin getToLowerPlugin() { return new ToLowerPlugin(); }

	public Plugin getTemperaturePlugin() { return new TemperaturePlugin(); }
}
