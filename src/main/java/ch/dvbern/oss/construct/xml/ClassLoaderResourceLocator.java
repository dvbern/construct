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
 * Implementation of ResourceLocator. Uses ClassLoader for locating the
 * resources. The resources (xml-files) must therefore be in the classpath of
 * the classloader. <br>
 * As {@code ResourceLocator} this class acts as event source for
 * {@code ResourceChangedEvent} s and implements the methods
 * {@code addResourceChangeListener} and
 * {@code removeResourceChangeListener} (empy implementations).
 *
 * @see ResourceChangedEvent
 */
public class ClassLoaderResourceLocator implements ResourceLocator {

    @NonNull
    private final ClassLoader cl;

	/**
	 * Default constructor. Uses class loader of this class for locating the
	 * resources
	 */
	public ClassLoaderResourceLocator() {
		cl = ClassLoaderResourceLocator.class.getClassLoader();
	}

	/**
	 * Alternate constructor. Uses specified ClassLoader for locating the
	 * resources.
	 *
	 * @param cl ClassLoader used for locating the resources; never null.
	 */
    public ClassLoaderResourceLocator(@NonNull ClassLoader cl) {
		this.cl = cl;
	}

	/**
	 * Method returns specified resource as inputStream.
	 *
     * @param resourceName name of resource, e.g. name of xml-file
	 * @return InputStream of specified resource, never null.
	 * @throws ResourceNotFoundException if resource could not have been located
	 */
	@Override
    @NonNull
    public InputStream getResourceAsStream(@NonNull String resourceName)
			throws ResourceNotFoundException {
        InputStream ins = cl.getResourceAsStream(resourceName);
		if (ins == null) {
			throw new ResourceNotFoundException("resource with objectId="
                + resourceName + " not found");
        }
        return ins;
	}

	/**
	 * Empty implementation of ResourceLocator-method. (Method should register
	 * listeners interested in changes or removals of resources.)
	 *
	 * @param listener listener interested in changes or removals of resources
	 */
	@Override
    public void addResourceChangeListener(@NonNull ResourceChangeListener listener) {
        // NOP
	}

	/**
	 * Empty implementation of ResourceLocator-method. (Method should
	 * de-register listeners.)
	 *
	 * @param listener registered listener that has to be removed
	 */
	@Override
    public void removeResourceChangeListener(@NonNull ResourceChangeListener listener) {
        // NOP
	}

}
