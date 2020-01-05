package BIF.SWE1.unittests;

import BIF.SWE1.CustomUE2;
import BIF.SWE1.UEB5;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.plugins.StaticGetPlugin;
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
	public void StaticGetPlugin_should_score_1_on_suitable_request() throws Exception {
		CustomUE2 ueb = createInstance();

		PluginManager obj = createInstance().getPluginManager();
		assertNotNull("CustomUE2.GetPluginManager returned null", obj);

		Request req = ueb.getRequest(RequestHelper.getValidRequestStream("/memory/index.html"));
		assertNotNull("CustomUE2.GetRequest returned null", req);

		Plugin plugin = ueb.getStaticFilePlugin();
		assertNotNull("CustomUE2.GetRequest returned null", plugin);

		assertEquals(plugin.canHandle(req), 1f);
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

}
