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


import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serial;

/**
 * This exception indicates that an {@code ObjectConstructor} was not
 * able to create/return an object. A detail message and/or a nested exception
 * may be passed to the constructor of this exception, which are included in the
 * stack trace of this exception.
 *
 * @see ObjectConstructor
 */
@NoArgsConstructor
public class ConstructionException extends Exception {

	@Serial
	private static final long serialVersionUID = -5362684009744647564L;

	/**
	 * Constructs an instance of {@code ConstructionException} with the
	 * passed nested exception.
	 *
	 * @param cause Throwable, which is beeing wrapped by this exception and
	 * included in the stack trace.
	 */
	public ConstructionException(@Nullable Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an instance of {@code ConstructionException} with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ConstructionException(@Nullable String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance of {@code ConstructionException} with the
	 * specified detail message and the passed nested exception.
	 *
	 * @param msg the detail message.
	 * @param cause Throwable, which is beeing wrapped by this exception and
	 */
	public ConstructionException(@Nullable String msg, @Nullable Throwable cause) {
		super(msg, cause);
	}
}
