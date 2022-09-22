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

import javax.annotation.Nonnull;

/**
 * This exception indicates that a parser is already registerd with a given
 * name.
 *
 * @see ParserFactory
 */
public class ParserAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 4404585566412735102L;

	/**
	 * Constructs an instance of <code>ParserAlreadyRegisteredException</code>
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ParserAlreadyRegisteredException(@Nonnull String msg) {
		super(msg);
	}
}
