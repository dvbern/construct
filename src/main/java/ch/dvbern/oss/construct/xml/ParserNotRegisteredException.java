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


import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serial;

/**
 * This exception indicates that no parser is registered for a given name.
 *
 * @see ParserFactory
 */
@NoArgsConstructor
public class ParserNotRegisteredException extends Exception {

	@Serial
	private static final long serialVersionUID = -8685141376596179827L;

	/**
	 * Constructs an instance of {@code ParserNotRegisteredException}
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ParserNotRegisteredException(@Nullable String msg) {
		super(msg);
	}
}
