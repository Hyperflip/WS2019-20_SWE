package BIF.SWE1.unittests;

import BIF.SWE1.CustomUE1;
import BIF.SWE1.UrlFactory;
import BIF.SWE1.interfaces.Url;
import BIF.SWE1.enums.UrlType;
import org.junit.*;

import static org.junit.Assert.assertNotNull;

public class CustomUE1Test extends AbstractTestFixture<CustomUE1> {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Override
	protected CustomUE1 createInstance() {
		return new CustomUE1();
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
	public void url_factory_should_detect_url_only_path() throws Exception {
		UrlFactory factory = createInstance().getUrlFactory();
		Url obj = factory.getWebUrl("/test/folder/image.jpg");

		assertNotNull("CustomUE1.GetWebUrl returned null", obj);

		assertEquals(UrlType.ONLY_PATH, factory.urlType);
	}

	// UrlType.NO_QUERY
	@Test
	public void url_factory_should_detect_url_no_query() throws Exception {
		UrlFactory factory = createInstance().getUrlFactory();
		Url obj = factory.getWebUrl("/test/folder/image.jpg#foo");

		assertNotNull("CustomUE1.GetWebUrl returned null", obj);

		assertEquals(UrlType.NO_QUERY, factory.urlType);
	}

	// UrlType.NO_FRAGMENT
	@Test
	public void url_factory_should_detect_url_no_fragment() throws Exception {
		UrlFactory factory = createInstance().getUrlFactory();
		Url obj = factory.getWebUrl("/test/folder/image.jpg?x=1&y=2");

		assertNotNull("CustomUE1.GetWebUrl returned null", obj);

		assertEquals(UrlType.NO_FRAGMENT, factory.urlType);
	}

	// UrlType.FULL_URL
	@Test
	public void url_factory_should_detect_url_full_url() throws Exception {
		UrlFactory factory = createInstance().getUrlFactory();
		Url obj = factory.getWebUrl("/test/folder/image.jpg?x=1&y=2#foo");

		assertNotNull("CustomUE1.GetWebUrl returned null", obj);

		assertEquals(UrlType.FULL_URL, factory.urlType);
	}

	// UrlType.MAIN_PAGE
	@Test
	public void url_factory_should_detect_url_main_page() throws Exception {
		UrlFactory factory = createInstance().getUrlFactory();
		Url obj = factory.getWebUrl("/");

		assertNotNull("CustomUE1.GetWebUrl returned null", obj);

		assertEquals(UrlType.MAIN_PAGE, factory.urlType);
	}

}
