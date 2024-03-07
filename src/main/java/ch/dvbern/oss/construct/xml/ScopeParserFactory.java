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
 * This class is responsible for maintaining a variable-scope where variables
 * (name and value) are stored (by {@code VardefParser}) and can be
 * retrieved (by {@code VarParser}). For retrieving and storing
 * ElementParsers, the ScopeParserFactory uses a ParserFactory.
 *
 * @see ParserFactory
 * @see ElementParser
 * @see ResourceLocator
 */
public class ScopeParserFactory extends ParserFactory {

	@NonNull
	private final ParserFactory factory;

	@NonNull
	private final Map<String, ClassObjectPair> varScope;

	/**
	 * Constructor.
	 *
	 * @param factory ParserFactory responsible for managing the ElementParsers
	 * @see ElementParser
	 */
	public ScopeParserFactory(@NonNull ParserFactory factory) {
		this.factory = factory;
		varScope = new HashMap<>();
	}

	/**
	 * Method returns {@code ElementParser} for a xml-tag with the
	 * {@code element-name}.
	 *
	 * @param elementName Name under which the parser is registered. Should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
	 * @return {@code ElementParser} for the given
	 * {@code element-name}. Never null.
	 * @throws ParserNotRegisteredException Thrown if there is no parser
	 *                                      registered for the given {@code element-name}.
	 */
	@Override
	@NonNull
	public ElementParser getParser(@NonNull String elementName)
			throws ParserNotRegisteredException {
		return factory.getParser(elementName);
	}

	/**
	 * Method registers implementation of {@code ElementParser} under the
	 * {@code element-name}.{@code element-name} should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
	 *
	 * @param elementName Name, under which the parser is registered. Should
	 * correspond to the element-name of the tag, for which the parser is
	 * designed.
	 * @param parser Implementation of {@code ElementParser}.
	 * @throws ParserAlreadyRegisteredException there is already a registered
	 *                                          parser
	 */
	@Override
	public void registerParser(@NonNull String elementName, @NonNull ElementParser parser)
			throws ParserAlreadyRegisteredException {
		factory.registerParser(elementName, parser);
	}

	/**
	 * Method returns a ClassObjectPair containing class and object of the
	 * variable with the name=varName If no value is found in this scope, the
	 * "higher" scopes (in factory passed to this ScopeParserFactory via
	 * constructor, etc.) are consulted.
	 *
	 * @param varName Name of the variable
	 * @return ClassObjectPair containing class and object of the variable with
	 * the name=varName; never null
	 * @throws VariableNotDefinedException Thrown if no variable-definition
	 *                                     with the varNmae is found in this scope or in a "higher" scope
	 */
	@NonNull
	public ClassObjectPair getVariableCOP(@NonNull String varName)
			throws VariableNotDefinedException {
		ClassObjectPair value = varScope.get(varName);
		if (value == null && (factory instanceof ScopeParserFactory scopeParserFactory)) {
			value = scopeParserFactory.getVariableCOP(varName);
		}
		if (value == null) {
			throw new VariableNotDefinedException("variable with name=" + varName + " not defined");
		}
		return value;
	}

	/**
	 * Method stores a variable in the scope of this ScopeParserFactory.
	 *
	 * @param varName Name under which the variable is to store
	 * @param cop ClassObjectPair containing class and object of the variable to
	 * store
	 * @throws VariableAlreadyDefinedException Thrown if a variable with
	 *                                         name=varName is already stored in the scope of this
	 *                                         ScopeParserFactory. (Variable with the same varName may be
	 *                                         stored in "higher" scopes.)
	 */
	public void setVariableCOP(@NonNull String varName, @NonNull ClassObjectPair cop)
			throws VariableAlreadyDefinedException {
		synchronized (varScope) {
			if (varScope.get(varName) != null) {
				throw new VariableAlreadyDefinedException(
						"A variable with name=" + varName
								+ " is already defined");
			}
			varScope.put(varName, cop);
		}
	}
}
