package BIF.SWE1.unittests;

import BIF.SWE1.CustomUE2;
import BIF.SWE1.UEB5;
import BIF.SWE1.WebPluginManager;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.plugins.ErrorPlugin;
import BIF.SWE1.plugins.StaticGetPlugin;
import BIF.SWE1.plugins.TemperaturePlugin;
import BIF.SWE1.plugins.ToLowerPlugin;
import org.junit.*;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/* Placeholder */
public class CustomUE2Test extends AbstractTestFixture<CustomUE2> {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Override
	protected CustomUE2 createInstance() {
		return new CustomUE2();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/********************* ErrorPlugin **********************/

	@Test
	public void ErrorPlugin_should_always_score_0_point_01() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/memory/index.html"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getErrorPlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0.01f);
	}

	/********************* StaticGetPlugin **********************/

	@Test
	public void StaticGetPlugin_should_score_0_point_9_on_suitable_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/memory/index.html"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getStaticFilePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0.9f);
	}

	@Test
	public void StaticGetPlugin_should_score_0_point_5_on_url_with_fragment() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/memory/index.html#foo"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getStaticFilePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0.5f);
	}

	@Test
	public void StaticGetPlugin_should_score_0_point_1_on_url_with_query() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/memory/index.html?x=1&y=2#foo"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getStaticFilePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0.1f);
	}

	@Test
	public void StaticGetPlugin_should_score_0_on_invalid_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getInvalidRequestStream());
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getStaticFilePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0f);
	}

	/********************* ToLowerPlugin **********************/

	@Test
	public void ToLowerPlugin_should_score_1_on_tolower_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/tolower.html", "POST", "tolower=TOLOWER"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getToLowerPlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 1f);
	}

	@Test
	public void ToLowerPlugin_should_score_0_on_GET_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/tolower.html", "GET", "tolower=TOLOWER"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getToLowerPlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0f);
	}

	/********************* TemperaturePlugin **********************/

	@Test
	public void TemperaturePlugin_should_score_1_on_suitable_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/GetTemperature", "GET"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getTemperaturePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 1f);
	}

	@Test
	public void TemperaturePlugin_should_score_0_on_unsuitable_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/somethingElse", "GET"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getTemperaturePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 0f);
	}

	@Test
	public void TemperaturePlugin_should_return_html_on_without_REST_parameters() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/GetTemperature", "GET"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getTemperaturePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals("text/html", plugin.handle(req).getContentType());
	}

	@Test
	public void TemperaturePlugin_should_return_xml_on_REST_parameters() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/GetTemperature/2010/08/01", "GET"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getTemperaturePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals("text/xml", plugin.handle(req).getContentType());
	}

	/********************* WebPluginManager **********************/

	@Test
	public void WebPluginManager_should_return_ErrorPlugin() throws Exception {
		CustomUE2 ueb = createInstance();

		Request req = ueb.getRequest(RequestHelper.getInvalidRequestStream());
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = WebPluginManager.getSuitablePluginForRequest(req);
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertTrue(plugin instanceof ErrorPlugin);
	}

	@Test
	public void WebPluginManager_should_return_StaticGetPlugin() throws Exception {
		CustomUE2 ueb = createInstance();

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/memory/index.html"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = WebPluginManager.getSuitablePluginForRequest(req);
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertTrue(plugin instanceof StaticGetPlugin);
	}

	@Test
	public void WebPluginManager_should_return_ToLowerPlugin() throws Exception {
		CustomUE2 ueb = createInstance();

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/tolower.html", "POST", "tolower=TEST"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = WebPluginManager.getSuitablePluginForRequest(req);
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertTrue(plugin instanceof ToLowerPlugin);
	}

	@Test
	public void WebPluginManager_should_return_TemperaturePlugin() throws Exception {
		CustomUE2 ueb = createInstance();

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/GetTemperature", "GET"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = WebPluginManager.getSuitablePluginForRequest(req);
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertTrue(plugin instanceof TemperaturePlugin);
	}

}
