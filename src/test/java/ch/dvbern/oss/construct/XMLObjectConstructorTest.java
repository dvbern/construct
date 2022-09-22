/*
 * Copyright (C) 2022 DV Bern AG, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.dvbern.oss.construct;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import ch.dvbern.oss.construct.xml.FilePathResourceLocator;
import ch.dvbern.oss.construct.xml.ParserFactory;
import ch.dvbern.oss.construct.xml.ResourceChangeListener;
import ch.dvbern.oss.construct.xml.ResourceChangedEvent;
import ch.dvbern.oss.construct.xml.ResourceLocator;
import ch.dvbern.oss.construct.xml.ResourceNotFoundException;
import ch.dvbern.oss.construct.xml.XMLObjectConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({ "ResultOfMethodCallIgnored", "LocalVariableHidesMemberVariable" })
public class XMLObjectConstructorTest {

	private static String filenameSimplSTC = null;
	private File fileSTC;
	private static String filenameRef = null;
	private File fileRef;

	private XMLObjectConstructor objConstructor;
	private FilePathResourceLocator fprl;

	@BeforeEach
	protected void setUp() throws Exception {

		//create ref.xml
		fileRef = File.createTempFile("ref.xml", null);
		FileOutputStream fout = new FileOutputStream(fileRef);
		DataOutputStream dout = new DataOutputStream(fout);
		dout.writeChars("<?xml version=\"1.0\"?>");
		dout.writeChars("<script>");
		dout.writeChars("<vardef name=\"map\">");
		dout.writeChars("<construct class=\"java.util.HashMap\">");
		dout.writeChars("<int value=\"5\" />");
		dout.writeChars("</construct>");
		dout.writeChars("</vardef>");

		dout.writeChars("<invoke methodName=\"put\">");
		dout.writeChars("<target><var name=\"map\" /></target>");
		dout.writeChars("<parameters>");
		dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"key 2\" /></cast>");
		dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"value 2\" /></cast>");
		dout.writeChars("</parameters>");
		dout.writeChars("</invoke>");
		dout.writeChars("<invoke methodName=\"put\">");
		dout.writeChars("<target><var name=\"map\" /></target>");
		dout.writeChars("<parameters>");
		dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"key 5\" /></cast>");
		dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"value 5\" /></cast>");
		dout.writeChars("</parameters>");
		dout.writeChars("</invoke>");
		dout.writeChars("<return>");
		dout.writeChars("<var name=\"map\" />");
		dout.writeChars("</return>");
		dout.writeChars("</script>");

		filenameRef = fileRef.getName();

		fout.close();
		dout.close();

		//create xml-file simpleTestConstruct.xml
		fileSTC = File.createTempFile("simpleTestConstruct.xml", null);
		originalFile();
		filenameSimplSTC = fileSTC.getName();

		//create objConstructor
		fprl = new FilePathResourceLocator(fileSTC.getParent(), 500);
		objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));

	}

	@AfterEach
	protected void tearDown() {
		fprl.stopResourceChecker();
		fileSTC.delete();
		fileRef.delete();
	}

	private void originalFile() throws Exception {
		FileOutputStream fout = new FileOutputStream(fileSTC);
		DataOutputStream dout = new DataOutputStream(fout);
		dout.writeChars("<?xml version=\"1.0\"?>");
		dout.writeChars("<construct class=\"ch.dvbern.oss.construct.SimpleTestConstruct\">");
		dout.writeChars("<short value=\"1\" />");
		dout.writeChars("<int value=\"2\" />");
		dout.writeChars("<long value=\"3\" />");
		dout.writeChars("<float value=\"4.4f\" />");
		dout.writeChars("<double value=\"5.5\" />");
		dout.writeChars("<char value=\"c\" />");
		dout.writeChars("<boolean value=\"true\" />");
		dout.writeChars("<string value=\"a string\" />");
		dout.writeChars("<cast class=\"java.lang.Object\"><ref id=\"" + filenameRef + "\" /></cast>");

		dout.writeChars("<array elementtype=\"java.lang.Integer\">");
		dout.writeChars("<construct class=\"java.lang.Integer\">");
		//here was initargs
		dout.writeChars("<string value=\"1\" />");
		//here was /initargs
		dout.writeChars("</construct>");
		dout.writeChars("<construct class=\"java.lang.Integer\">");
		//here was initargs
		dout.writeChars("<string value=\"2\" />");
		//here was /initargs
		dout.writeChars("</construct>");
		dout.writeChars("<construct class=\"java.lang.Integer\">");
		//here was initargs
		dout.writeChars("<string value=\"3\" />");
		//here was /initargs
		dout.writeChars("</construct>");
		dout.writeChars("</array>");

		dout.writeChars("<array elementtype=\"int[]\">");
		dout.writeChars("<array elementtype=\"int\">");
		dout.writeChars("<int value=\"11\" />");
		dout.writeChars("<int value=\"12\" />");
		dout.writeChars("<int value=\"13\" />");
		dout.writeChars("</array>");
		dout.writeChars("<array elementtype=\"int\">");
		dout.writeChars("<int value=\"21\" />");
		dout.writeChars("<int value=\"22\" />");
		dout.writeChars("<int value=\"23\" />");
		dout.writeChars("</array>");
		dout.writeChars("<array elementtype=\"int\">");
		dout.writeChars("<int value=\"31\" />");
		dout.writeChars("<int value=\"32\" />");
		dout.writeChars("<int value=\"33\" />");
		dout.writeChars("</array>");
		dout.writeChars("</array>");

		//here was /initargs
		/*
        dout.writeChars("<access>");
        dout.writeChars("<field name=\"aStaticString\">");
        dout.writeChars("<string value=\"new definition\" />");
        dout.writeChars("</field>");
        dout.writeChars("<method name=\"setAStaticInteger\" >");
        dout.writeChars("<construct class=\"java.lang.Integer\" >");
        //here was initargs
        dout.writeChars("<string value=\"333\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</method>");
        dout.writeChars("</access>");
         */
		dout.writeChars("</construct>");

		fout.close();
		dout.close();
	}

	private void changeFile() throws Exception {
		FileOutputStream fout = new FileOutputStream(fileSTC.getAbsolutePath());
		DataOutputStream dout = new DataOutputStream(fout);
		dout.writeChars("<?xml version=\"1.0\"?>");
		dout.writeChars("<construct class=\"ch.dvbern.oss.construct.SimpleTestConstruct\">");
		//here was initargs

		dout.writeChars("<short value=\"100\" />");
		dout.writeChars("<int value=\"2\" />");
		dout.writeChars("<long value=\"3\" />");
		dout.writeChars("<float value=\"4.4f\" />");
		dout.writeChars("<double value=\"5.5\" />");
		dout.writeChars("<char value=\"c\" />");
		dout.writeChars("<boolean value=\"true\" />");
		dout.writeChars("<string value=\"a string\" />");
		dout.writeChars("<cast class=\"java.lang.Object\"><ref id=\"" + filenameRef + "\" /></cast>");

		dout.writeChars("<array elementtype=\"java.lang.Integer\">");
		dout.writeChars("<construct class=\"java.lang.Integer\">");
		//here was initargs

		dout.writeChars("<string value=\"100\" />");
		//here was /initargs
		dout.writeChars("</construct>");
		dout.writeChars("<construct class=\"java.lang.Integer\">");
		//here was initargs
		dout.writeChars("<string value=\"200\" />");
		//here was /initargs
		dout.writeChars("</construct>");
		dout.writeChars("<construct class=\"java.lang.Integer\">");
		//here was initargs
		dout.writeChars("<string value=\"300\" />");
		//here was /initargs
		dout.writeChars("</construct>");
		dout.writeChars("</array>");

		dout.writeChars("<array elementtype=\"int[]\">");
		dout.writeChars("<array elementtype=\"int\">");
		dout.writeChars("<int value=\"11\" />");
		dout.writeChars("<int value=\"12\" />");
		dout.writeChars("<int value=\"13\" />");
		dout.writeChars("</array>");
		dout.writeChars("<array elementtype=\"int\">");
		dout.writeChars("<int value=\"21\" />");
		dout.writeChars("<int value=\"22\" />");
		dout.writeChars("<int value=\"23\" />");
		dout.writeChars("</array>");
		dout.writeChars("<array elementtype=\"int\">");
		dout.writeChars("<int value=\"31\" />");
		dout.writeChars("<int value=\"32\" />");
		dout.writeChars("<int value=\"33\" />");
		dout.writeChars("</array>");
		dout.writeChars("</array>");

		//here was /initargs
        /*
        dout.writeChars("<access>");
        dout.writeChars("<field name=\"aStaticString\">");
        dout.writeChars("<string value=\"new definition\" />");
        dout.writeChars("</field>");
        dout.writeChars("<method name=\"setAStaticInteger\" >");
        dout.writeChars("<construct class=\"java.lang.Integer\" >");
        //here was initargs

        dout.writeChars("<string value=\"999\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</method>");
        dout.writeChars("</access>");
         */
		dout.writeChars("</construct>");

		fout.close();
		dout.close();
	}

	private File constructMyClass() throws Exception {
		File file = File.createTempFile("myClass", ".xml");
		FileOutputStream fout = new FileOutputStream(file);
		DataOutputStream dout = new DataOutputStream(fout);
		dout.writeChars("<?xml version=\"1.0\"?>");
		dout.writeChars("<class name=\"java.lang.String\" />");

		dout.close();
		fout.close();
		return file;
	}

	@Test
	public void testInitValues() throws Exception {
		SimpleTestConstruct construct = (SimpleTestConstruct) objConstructor.construct(filenameSimplSTC, false);
		assertThat(construct.getAshort()).isEqualTo((short) 1);
		assertThat(construct.getAint()).isEqualTo(2);
		assertThat(construct.getAlong()).isEqualTo(3);
		assertThat(4.4f).isEqualTo(construct.getAfloat());
		assertThat(5.5).isEqualTo(construct.getAdouble());
		assertThat(construct.getAchar()).isEqualTo('c');
		assertThat(construct.isAboolean()).isEqualTo(true);
		assertThat(construct.getAstring()).isEqualTo("a string");
	}

	@Test
	public void testRefParser() throws Exception {
		SimpleTestConstruct construct = (SimpleTestConstruct) objConstructor.construct(filenameSimplSTC, false);
		Map<?, ?> map = (Map<?, ?>) construct.getAobject();
		assertThat(map.get("key 2")).isEqualTo("value 2");
	}

	@Test
	public void testArrayParser() throws Exception {
		SimpleTestConstruct construct = (SimpleTestConstruct) objConstructor.construct(filenameSimplSTC, false);
		Integer[] arrayOne = construct.getArrayOne();
		assertThat(arrayOne[1].equals(new Integer("2"))).isTrue();
		int[][] arrayTwo = construct.getArrayTwo();
		assertThat(arrayTwo[0][2] == 13).isTrue();
		assertThat(arrayTwo[1][1] == 22).isTrue();
		assertThat(arrayTwo[2][0] == 31).isTrue();
	}

	@Test
	public void testRemoveEvent() throws Exception {
		assertDoesNotThrow(() -> objConstructor.construct(filenameSimplSTC, false));

		fileSTC.delete();
		Thread.sleep(1000); //see set period in setUp

		ConstructionException ex = assertThrows(
				ConstructionException.class,
				() -> objConstructor.construct(filenameSimplSTC, false)
		);

		assertThat(ex)
				.hasMessageContaining("resource")
				.hasMessageContaining(fileSTC.getName())
				.hasMessageContaining("not found")
		;
	}

	@Test
	public void testChangeEvent() throws Exception {
		TestFilePathResourceLocator locator =
				new TestFilePathResourceLocator(fileSTC.getParent());
		XMLObjectConstructor constructor = new XMLObjectConstructor(new ParserFactory(locator));

		SimpleTestConstruct construct = (SimpleTestConstruct) constructor.construct(filenameSimplSTC, false);
		assertThat(construct.getAshort()).isEqualTo((short) 1);
		Integer[] arrayOne = construct.getArrayOne();
		assertThat(arrayOne[1].equals(new Integer("2"))).isTrue();
		//assertEquals(construct.getAStaticInteger(), new Integer("333"));

		changeFile();
		locator.notifyResourceChange(filenameSimplSTC);

		SimpleTestConstruct constructAfterChange = (SimpleTestConstruct) constructor.construct(
				filenameSimplSTC,
				false);
		assertThat(constructAfterChange.getAshort()).isEqualTo((short) 100);
		Integer[] arrayOneAfterChange = constructAfterChange.getArrayOne();
		assertThat(arrayOneAfterChange[1].equals(new Integer("200"))).isTrue();
		//assertEquals(construct.getAStaticInteger(), new Integer("999"));

	}

	@Test
	public void testClass() throws Exception {
		File file = constructMyClass();
		fprl = new FilePathResourceLocator(file.getParent(), 500);
		XMLObjectConstructor objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));
		Class<?> klass = (Class<?>) objConstructor.construct(file.getName(), false);
		assertThat(klass.getName().equals("".getClass().getName())).isTrue();

		fprl.stopResourceChecker();
		file.delete();
	}

	@Test
	public void testScript() throws Exception {
		File file1 = File.createTempFile("script", ".xml");
		FileOutputStream fout1 = new FileOutputStream(file1);
		DataOutputStream dout1 = new DataOutputStream(fout1);

		dout1.writeChars("<?xml version=\"1.0\"?>");
		dout1.writeChars("<script>");
		dout1.writeChars("<vardef name=\"a\">");
		dout1.writeChars("<construct class=\"java.lang.String\">");
		//here was initargs
		dout1.writeChars("<string value=\"a string\"/>");
		//here was /initargs
		dout1.writeChars("</construct>");
		dout1.writeChars("</vardef>");
		dout1.writeChars("<vardef name=\"b\">");
		dout1.writeChars("<construct class=\"java.lang.String\">");
		//here was initargs
		dout1.writeChars("<string value=\" and another string\"/>");
		//here was /initargs
		dout1.writeChars("</construct>");
		dout1.writeChars("</vardef>");
		dout1.writeChars("<invoke methodName=\"concat\">");
		dout1.writeChars("<target><var name=\"a\"/></target>");
		dout1.writeChars("<parameters>");
		dout1.writeChars("<var name=\"b\" />");
		dout1.writeChars("</parameters>");
		dout1.writeChars("</invoke>");
		dout1.writeChars("</script>");

		FilePathResourceLocator fprl = new FilePathResourceLocator(file1.getParent(), 500);
		XMLObjectConstructor objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));

		String obj1 = (String) objConstructor.construct(file1.getName(), false);
		assertThat(obj1).isEqualTo("a string and another string");

		file1.delete();
		dout1.close();
		fout1.close();

		File file2 = File.createTempFile("script2", ".xml");
		FileOutputStream fout2 = new FileOutputStream(file2);
		DataOutputStream dout2 = new DataOutputStream(fout2);

		dout2.writeChars("<?xml version=\"1.0\"?>");
		dout2.writeChars("<script>");
		dout2.writeChars("<vardef name=\"a\">");
		dout2.writeChars("<construct class=\"java.lang.String\">");
		//here was initargs
		dout2.writeChars("<string value=\"a string\"/>");
		//here was /initargs
		dout2.writeChars("</construct>");
		dout2.writeChars("</vardef>");
		dout2.writeChars("<vardef name=\"b\">");
		dout2.writeChars("<construct class=\"java.lang.String\">");
		//here was initargs
		dout2.writeChars("<string value=\" and another string\"/>");
		//here was /initargs
		dout2.writeChars("</construct>");
		dout2.writeChars("</vardef>");
		dout2.writeChars("<vardef name=\"c\">");
		dout2.writeChars("<script>");
		dout2.writeChars("<vardef name=\"b\">");
		dout2.writeChars("<string value=\" and a modified string\" />");
		dout2.writeChars("</vardef>");
		dout2.writeChars("<invoke methodName=\"concat\" >");
		dout2.writeChars("<target><var name=\"a\" /></target>");
		dout2.writeChars("<parameters>");
		dout2.writeChars("<var name=\"b\" />");
		dout2.writeChars("</parameters>");
		dout2.writeChars("</invoke>");
		dout2.writeChars("</script>");
		dout2.writeChars("</vardef>");
		dout2.writeChars("<return>");
		dout2.writeChars("<invoke methodName=\"concat\">");
		dout2.writeChars("<target><var name=\"c\" /></target>");
		dout2.writeChars("<parameters><string value=\" are concatenated\" /></parameters>");
		dout2.writeChars("</invoke>");
		dout2.writeChars("</return>");
		dout2.writeChars("</script>");

		String obj2 = (String) objConstructor.construct(file2.getName(), false);
		assertThat(obj2).isEqualTo("a string and a modified string are concatenated");

		file2.delete();
		dout2.close();
		fout2.close();

		fprl.stopResourceChecker();

	}

	@Test
	public void testFieldAccess() throws Exception {
		File file = File.createTempFile("field", ".xml");
		try (FileOutputStream fout = new FileOutputStream(file)) {

			String xml = ""
					+ "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
					+ "<script>\n"
					+ "  <vardef name=\"myObject\">\n"
					+ "    <construct class=\"ch.dvbern.oss.construct.SimpleFieldFixture\" />\n"
					+ "  </vardef>\n"
					+ "  <vardef name=\"field1\" >\n"
					+ "    <getfield name=\"field1\" >\n"
					+ "      <target><var name=\"myObject\" /></target>\n"
					+ "    </getfield>\n"
					+ "  </vardef>\n"
					+ "  <setfield name=\"field2\">\n"
					+ "    <target><var name=\"myObject\" /></target>\n"
					+ "    <value><var name=\"field1\" /></value>\n"
					+ "  </setfield>\n"
					+ "  <return><var name=\"myObject\" /></return>\n"
					+ "</script>\n";

			fout.write(xml.getBytes(StandardCharsets.UTF_8));
		}

		FilePathResourceLocator fprl = new FilePathResourceLocator(file.getParent());
		XMLObjectConstructor objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));

		SimpleFieldFixture test = (SimpleFieldFixture) objConstructor.construct(file.getName(), false);
		assertThat(test.field2.equals("field 1")).isTrue();

		file.delete();
		fprl.stopResourceChecker();
	}

	private static class TestFilePathResourceLocator implements ResourceLocator {
		private final String path;
		private final HashSet<ResourceChangeListener> listeners;

		/**
		 * Test resource locator.
		 *
		 * @param path file path
		 */
		public TestFilePathResourceLocator(String path) {
			this.path = path;
			listeners = new HashSet<>();
		}

		/**
		 * Method de-registers listeners.
		 *
		 * @param listener: registered listener that has to be removed
		 */
		@Override
		public void removeResourceChangeListener(@Nonnull ResourceChangeListener listener) {
			synchronized (listeners) {
				listeners.remove(listener);
			}
		}

		/**
		 * Method specified resource as InputStream or throws Exception.
		 *
		 * @param resourceName Name of resource to locate (name of xml-file)
		 * @return InputStream: resource as InputStream; never null.
		 * @throws ResourceNotFoundException : Thrown if specified resource could not have been found
		 */
		@Override
		@Nonnull
		public InputStream getResourceAsStream(@Nonnull String resourceName) throws ResourceNotFoundException {
			try {

				File file = new File(path, resourceName);
				return new FileInputStream(file);
			} catch (FileNotFoundException ex) {
				throw new ResourceNotFoundException("resource=" + resourceName + " not found, " + ex.getMessage());
			}
		}

		/**
		 * Method registers listeners interested in changes or removals of resources.
		 *
		 * @param listener: listener interested in changes or removals of resources
		 */
		@Override
		public void addResourceChangeListener(@Nonnull ResourceChangeListener listener) {
			if (listener == null) {
				throw new IllegalArgumentException("listener must not be null");
			}
			synchronized (listeners) {
				listeners.add(listener);
			}
		}

		/**
		 * resource change notification
		 */
		public void notifyResourceChange(String resourceIdentifier) {
			ResourceChangedEvent event = new ResourceChangedEvent(this, resourceIdentifier);
			Set<ResourceChangeListener> clone = null;
			synchronized (listeners) {
				//noinspection unchecked
				clone = (Set<ResourceChangeListener>) listeners.clone();
			}

			for (ResourceChangeListener listener : clone) {
				listener.resourceChanged(event);
			}
		}

	}
}

