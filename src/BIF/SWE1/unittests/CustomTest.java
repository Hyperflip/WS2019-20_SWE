package BIF.SWE1.unittests;

import BIF.SWE1.CustomUE;
import BIF.SWE1.interfaces.Url;
import org.junit.*;

import static org.junit.Assert.assertNotNull;

public class CustomTest extends AbstractTestFixture<CustomUE> {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Override
	protected CustomUE createInstance() {
		return new CustomUE();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	// NOTE 2: what about empty urls? more types in UrlType?

	// UrlType.ONLY_PATH
	@Test
	public void url_should_parse_only_path() throws Exception {
		Url obj = createInstance().getUrl("/test/folder/image.jpg");
		assertNotNull("Custom.GetUrl returned null", obj);

		assertEquals("/test/folder/image.jpg", obj.getPath());
	}

	// UrlType.NO_QUERY
	@Test
	public void url_should_parse_no_query() throws Exception {
		Url obj = createInstance().getUrl("/test/folder/image.jpg#foo");
		assertNotNull("Custom.GetUrl returned null", obj);

		assertEquals("/test/folder/image.jpg", obj.getPath());
		assertEquals("foo", obj.getFragment());
	}

	// UrlType.NO_FRAGMENT
	@Test
	public void url_should_parse_no_fragment() throws Exception {
		Url obj = createInstance().getUrl("/test/folder/image.jpg?x=1&y=2");
		assertNotNull("Custom.GetUrl returned null", obj);

		assertEquals("/test/folder/image.jpg", obj.getPath());

		assertNotNull(obj.getParameter().get("x"));
		assertNotNull(obj.getParameter().get("y"));
		assertEquals("1", obj.getParameter().get("x"));
		assertEquals("2", obj.getParameter().get("y"));
	}

	// UrlType.FULL_URL
	@Test
	public void url_should_parse_full_url() throws Exception {
		Url obj = createInstance().getUrl("/test/folder/image.jpg?x=1&y=2#foo");
		assertNotNull("Custom.GetUrl returned null", obj);

		assertEquals("/test/folder/image.jpg", obj.getPath());

		assertEquals("foo", obj.getFragment());

		assertNotNull(obj.getParameter().get("x"));
		assertNotNull(obj.getParameter().get("y"));
		assertEquals("1", obj.getParameter().get("x"));
		assertEquals("2", obj.getParameter().get("y"));
	}

}
