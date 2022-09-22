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

import javax.annotation.Nonnull;

/**
 * Exception indicating that a variable with the given name is not stored in a
 * given scope or in a scope of higher levels Used by ScopeParserFactory.
 *
 * @see ScopeParserFactory
 */
public class VariableNotDefinedException extends java.lang.Exception {

	private static final long serialVersionUID = 3523634640300494355L;

	/**
	 * Constructs an instance of <code>VariableNotDefinedException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public VariableNotDefinedException(@Nonnull String msg) {
		super(msg);
	}
}
