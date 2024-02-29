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
package ch.dvbern.oss.construct.xml;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is responsible for managing {@code ElementParser}. Basic
 * parsers are added in {@code init()}, additional ones may be added via
 * {@code registerParser()}. The factory stores a
 * {@code ResourceLocator} responsible for locating resources
 * (xml-files).
 *
 * @see ElementParser
 * @see ResourceLocator
 */
public class ParserFactory {

    @NonNull
	private final Map<String, ElementParser> parsers = new HashMap<>();

    @NonNull
	private final ResourceLocator locator;

	/**
     * Default constructor. Sets {@code ClassLoaderResourceLocator} as
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
     * @param locator {@code ResourceLocator} for locating resources;
	 * never null
	 * @see ResourceLocator
	 */
    public ParserFactory(@NonNull ResourceLocator locator) {
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
     * Method returns {@code ElementParser} for a xml-tag with the
     * {@code elementName}.
	 *
	 * @param elementName identifier of the element
     * @return {@code ElementParser} for the given
     * {@code element-name}. Never null.
	 * @throws ParserNotRegisteredException Thrown if there is no parser
     *                                      registered for the given {@code elementName}.
	 */
    @NonNull
    public ElementParser getParser(@NonNull String elementName)
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
     * Method registers an implementation of {@code ElementParser} under
     * the {@code elementName}.{@code elementName} should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
	 *
	 * @param elementName Name, under which the parser is registered. Should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
     * @param parser Implementation of {@code ElementParser}.
	 * @throws ParserAlreadyRegisteredException Thrown, if there is already a
     *                                          parser registered with {@code elementName}
	 */
    public void registerParser(@NonNull String elementName, @NonNull ElementParser parser)
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
    @NonNull
	public ResourceLocator getResourceLocator() {
		return locator;
	}
}
