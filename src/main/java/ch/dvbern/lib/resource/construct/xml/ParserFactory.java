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
package ch.dvbern.lib.resource.construct.xml;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * This class is responsible for managing <code>ElementParser</code>. Basic
 * parsers are added in <code>init()</code>, additional ones may be added via
 * <code>registerParser()</code>. The factory stores a
 * <code>ResourceLocator</code> responsible for locating resources
 * (xml-files).
 *
 * @see ElementParser
 * @see ResourceLocator
 */
public class ParserFactory {

	@Nonnull
	private final Map<String, ElementParser> parsers = new HashMap<>();

	@Nonnull
	private final ResourceLocator locator;

	/**
	 * Default constructor. Sets <code>ClassLoaderResourceLocator</code> as
	 * resource locator.
	 *
	 * @see ClassLoaderResourceLocator
	 */
	public ParserFactory() {
		this(new ClassLoaderResourceLocator());
	}

	/**
	 * Constructor.
	 *
	 * @param locator <code>ResourceLocator</code> for locating resources;
	 * never null
	 * @see ResourceLocator
	 */
	public ParserFactory(@Nonnull ResourceLocator locator) {
		this.locator = locator;
		init();
	}

	private void init() {
		parsers.put("ref", new RefParser());
		parsers.put("construct", new ConstructParser());
		parsers.put("array", new ArrayParser());
		parsers.put("short", new PrimParser());
		parsers.put("null", new NullParser());
		parsers.put("cast", new CastParser());
		parsers.put("class", new ClassParser());
		parsers.put("invoke", new InvokeParser());
		parsers.put("setfield", new SetFieldParser());
		parsers.put("getfield", new GetFieldParser());
		parsers.put("script", new ScriptParser());
		parsers.put("vardef", new VardefParser());
		parsers.put("var", new VarParser());
		PrimParser prim = new PrimParser();
		parsers.put("int", prim);
		parsers.put("short", prim);
		parsers.put("long", prim);
		parsers.put("float", prim);
		parsers.put("double", prim);
		parsers.put("char", prim);
		parsers.put("boolean", prim);
		parsers.put("string", prim);
	}

	/**
	 * Method returns <code>ElementParser</code> for a xml-tag with the
	 * <code>elementName</code>.
	 *
	 * @param elementName identifier of the element
	 * @return <code>ElementParser</code> for the given
	 * <code>element-name</code>. Never null.
	 * @throws ParserNotRegisteredException Thrown if there is no parser
	 *                                      registered for the given <code>elementName</code>.
	 */
	@Nonnull
	public ElementParser getParser(@Nonnull String elementName)
			throws ParserNotRegisteredException {
		String lowerCaseName = elementName.toLowerCase();
		ElementParser parser = parsers.get(lowerCaseName);
		if (parser == null) {
			throw new ParserNotRegisteredException(
					"there is no parser registered for elementName="
							+ lowerCaseName);
		}
		return parser;
	}

	/**
	 * Method registers an implementation of <code>ElementParser</code> under
	 * the <code>elementName</code>.<code>elementName</code> should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
	 *
	 * @param elementName Name, under which the parser is registered. Should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
	 * @param parser Implementation of <code>ElementParser</code>.
	 * @throws ParserAlreadyRegisteredException Thrown, if there is already a
	 *                                          parser registered with <code>elementName</code>
	 */
	public void registerParser(@Nonnull String elementName, @Nonnull ElementParser parser)
			throws ParserAlreadyRegisteredException {
		String lowerCaseName = elementName.toLowerCase();
		synchronized (parsers) {
			if (parsers.containsKey(lowerCaseName)) {
				throw new ParserAlreadyRegisteredException(
						"there is already a parser registered for elementName="
								+ lowerCaseName);
			}
			parsers.put(lowerCaseName, parser);
		}
	}

	/**
	 * Method returns the ResourceLocator used by this factory.
	 *
	 * @return ResourceLocator used by this factory
	 */
	@Nonnull
	public ResourceLocator getResourceLocator() {
		return locator;
	}
}
