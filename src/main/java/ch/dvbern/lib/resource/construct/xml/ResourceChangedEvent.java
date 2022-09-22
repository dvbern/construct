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
 * Event indicating that a resource has been changed or removed at all. Event object contains the name of the changed
 * resource.
 */
public class ResourceChangedEvent extends java.util.EventObject {

	private static final long serialVersionUID = -7039199533056588697L;

	@Nonnull
	private String resource;

	/**
	 * Constructor.
	 *
	 * @param source Object generating the event
	 * @param resourceName name of resource that has been changed or removed
	 */
	public ResourceChangedEvent(@Nonnull Object source, @Nonnull String resourceName) {

		super(source);
		resource = resourceName;
	}

	/**
	 * Method returns the name of the resource that has been changed or removed.
	 *
	 * @return Name of changed/removed resource
	 */
	@Nonnull
	public String getResourceName() {

		return resource;
	}

}
