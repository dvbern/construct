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

import ch.dvbern.oss.construct.ConstructionException;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Constructor;

/**
 * This class is used for creating objects out of declarations.
 */
@Getter
public class Construct {

    @NonNull
    private final Class<?> klass;

    @NonNull
    private final Class<?>[] argClasses;

    @NonNull
    private final Object[] initArgs;

	/**
	 * Constructor.
	 *
     * @param klass {@code Class} of the object to create.
     * @param argClasses Array of {@code Class} instances representing
	 * the types of the parameters of the constructor for
     * {@code klass}
     * @param argValues Array of {@code Object} instances representing
	 * the values of the parameters of the constructor for a new instance
     * of {@code klass}
	 */
    public Construct(@NonNull Class<?> klass, @NonNull Class<?>[] argClasses, @NonNull Object[] argValues) {
		this.klass = klass;
		this.argClasses = argClasses.clone();
		this.initArgs = argValues.clone();
	}


	/**
     * This method returns the {@code Object} represented by this
     * {@code Construct}.
	 *
     * @return {@code Object} represented by this {@code Construct}.
	 * @throws ConstructionException Thrown if the object cannot be created
     *                               out of the member-values of this {@code Construct}
	 */
	public Object getObject() throws ConstructionException {
		try {
			Constructor<?> constructor;
			constructor = getKlass()
					.getConstructor(getArgClasses());
			return constructor.newInstance(getInitArgs());
		} catch (Exception ex) {
			throw new ConstructionException(ex);
		}
	}
}
