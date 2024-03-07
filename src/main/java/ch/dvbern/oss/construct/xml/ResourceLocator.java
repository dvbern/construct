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

import java.io.InputStream;


/**
 * Abstraction of resource locator. Used by XMLObjectConstructor resp. ParserFactory. Event source for
 * {@code ResourceChangedEvent}s
 *
 * @see ClassLoaderResourceLocator
 * @see ParserFactory
 * @see XMLObjectConstructor
 * @see ResourceChangedEvent
 */
public interface ResourceLocator {

	/**
	 * Method returns resource as InputStream or throws Exception.
	 *
	 * @param resourceName Name of resource to locate (name of xml-file)
	 * @return InputStream: resource as InputStream; never null.
	 * @throws ResourceNotFoundException if specified resource could not have been found
	 */
	@NonNull
	InputStream getResourceAsStream(@NonNull String resourceName) throws ResourceNotFoundException;

	/**
	 * Method registers listeners interested in changes or removals of resources.
	 *
	 * @param listener listener interested in changes or removals of resources
	 */
	void addResourceChangeListener(@NonNull ResourceChangeListener listener);

	/**
	 * Method de-registers listeners.
	 *
	 * @param listener registered listener that has to be removed
	 */
	void removeResourceChangeListener(@NonNull ResourceChangeListener listener);
}
