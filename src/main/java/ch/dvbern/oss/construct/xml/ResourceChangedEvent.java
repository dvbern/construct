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

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serial;
import java.util.EventObject;

/**
 * Event indicating that a resource has been changed or removed at all. Event object contains the name of the changed
 * resource.
 */
@Getter
public class ResourceChangedEvent extends EventObject {

	@Serial
	private static final long serialVersionUID = -7039199533056588697L;

	@NonNull
	private final String resourceName;

	/**
	 * Constructor.
	 *
	 * @param source Object generating the event
	 * @param resourceName name of resource that has been changed or removed
	 */
	public ResourceChangedEvent(@NonNull Object source, @NonNull String resourceName) {

		super(source);
		this.resourceName = resourceName;
	}

}
