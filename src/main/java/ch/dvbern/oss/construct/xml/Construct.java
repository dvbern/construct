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

import java.lang.reflect.Constructor;

import javax.annotation.Nonnull;

import ch.dvbern.oss.construct.ConstructionException;

/**
 * This class is used for creating objects out of declarations.
 */
public class Construct {

	@Nonnull
	private Class klass;

	@Nonnull
	private Class[] argClasses;

	@Nonnull
	private Object[] argValues;

	/**
	 * Constructor.
	 *
	 * @param klass <code>Class</code> of the object to create.
	 * @param argClasses Array of <code>Class</code> instances representing
	 * the types of the parameters of the constructor for
	 * <code>klass</code>
	 * @param argValues Array of <code>Object</code> instances representing
	 * the values of the parameters of the constructor for a new instance
	 * of <code>klass</code>
	 */
	public Construct(@Nonnull Class<?> klass, @Nonnull Class<?>[] argClasses, @Nonnull Object[] argValues) {
		this.klass = klass;
		this.argClasses = argClasses.clone();
		this.argValues = argValues.clone();
	}

	/**
	 * Method returns the <code>klass</code> member
	 *
	 * @return <code>Class</code>: The type of this <code>Construct</code>
	 * @throws ConstructionException Thrown if the object cannot be created
	 *                               out of the member-values of this <code>Construct</code>
	 */
	@Nonnull
	public Class<?> getKlass() throws ConstructionException {
		return klass;
	}

	@Nonnull
	private Class<?>[] getArgClasses() throws ClassNotFoundException {
		return argClasses;
	}

	@Nonnull
	private Object[] getInitArgs() {
		return argValues;
	}

	/**
	 * This method returns the <code>Object</code> represented by this
	 * <code>Construct</code>.
	 *
	 * @return <code>Object</code> represented by this <code>Construct</code>.
	 * @throws ConstructionException Thrown if the object cannot be created
	 *                               out of the member-values of this <code>Construct</code>
	 */
	public Object getObject() throws ConstructionException {
		try {
			Constructor<?> constructor;
			constructor = getKlass()
					.getConstructor(getArgClasses());
			return constructor.newInstance(getInitArgs());
		} catch (ClassNotFoundException ex) {
			throw new ConstructionException(ex);
		} catch (NoSuchMethodException ex) {
			throw new ConstructionException(ex);
		} catch (Exception ex) {
			throw new ConstructionException(ex);
		}
	}
}
