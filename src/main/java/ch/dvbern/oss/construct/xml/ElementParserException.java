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
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serial;

/**
 * This exception indicates that an {@code ElementParser} was not able to
 * parse a xml-element an create class/object instances.
 */
@NoArgsConstructor
public class ElementParserException extends Exception {

    @Serial
	private static final long serialVersionUID = -7590476829224567457L;

	/**
     * Creates a new instance of {@code ElementParserException} with the
	 * passed nested exception.
	 *
	 * @param cause Throwable, which is beeing wrapped by this exception and
	 * included in the stack trace.
	 */
    public ElementParserException(@NonNull Throwable cause) {
		super(cause);
	}

	/**
     * Constructs an instance of {@code ElementParserException} with the
	 * specified detail message
	 *
	 * @param msg the detail message.
	 */
    public ElementParserException(@NonNull String msg) {
		super(msg);
	}

	/**
     * Constructs an instance of {@code ElementParserException} with the
	 * specified detail message and the passed nested exception.
	 *
	 * @param msg the detail message.
	 * @param cause Throwable, which is beeing wrapped by this exception and
	 * included in the stack trace.
	 */
    public ElementParserException(@NonNull String msg, @NonNull Throwable cause) {
		super(msg, cause);
	}
}
