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

import java.io.Serial;

/**
 * Exception indicating that a specific resource could not have been found. Used
 * by implementation of ResourceLocator
 *
 * @see ResourceLocator
 */
public class ResourceNotFoundException extends Exception {

	@Serial
	private static final long serialVersionUID = -1502396802449310508L;

	/**
	 * Constructs an instance of {@code ResourceNotFoundException} with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ResourceNotFoundException(@NonNull String msg) {
		super(msg);
	}
}
