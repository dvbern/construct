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
 * Interface for listeners, interested in changes or removals of resources.
 * Event sources: implementations of <code>ResourceLocator</code>
 *
 * @see ResourceLocator
 */
public interface ResourceChangeListener extends java.util.EventListener {
	/**
	 * Method is called, if a resource has been changed.
	 *
	 * @param event <code>ResourceChangedEvent</code>: object containing the
	 * information about changed resource.
	 */
	void resourceChanged(@Nonnull ResourceChangedEvent event);

	/**
	 * Method is called, if a resource has been removed.
	 *
	 * @param event <code>ResourceChangedEvent</code>: object containing the
	 * information about removed resource.
	 */
	void resourceRemoved(@Nonnull ResourceChangedEvent event);
}
