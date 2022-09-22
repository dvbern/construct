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

import javax.annotation.Nonnull;

/**
 * Abstraction of object constuctors. Implementations create and return objects,
 * identified by the passed <code>objectId</code>. They should chache created
 * objects and return the already created instances if <code>newInstance</code>
 * is false.
 */
public interface ObjectConstructor {

	/**
	 * This method returns an object identified by <code>objectId</code>. A
	 * cached instance may be returned, if <code>newInstance</code> is false.
	 * If there is no definition for the object with the passed
	 * <code>objectId</code> or if the object can not be created, a
	 * <code>ConstructionException</code> is thrown.
	 *
	 * @param objectId The id of the object, which should be created/returned.
	 * @param newInstance boolean, indicating whether a new instance should be created
	 * (true) or if already created (and cached) instances should be
	 * returned.
	 * @return Object according to the paramter <code>objectId</code>. The
	 * return value is never null.
	 * @throws ConstructionException Thrown, if there is no definition for the object with the
	 *                               passed <code>objectId</code> or if the object can not be
	 *                               created
	 */
	@Nonnull
	public Object construct(@Nonnull String objectId, boolean newInstance)
			throws ConstructionException;
}
