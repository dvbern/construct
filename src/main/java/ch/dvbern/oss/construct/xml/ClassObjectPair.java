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
import javax.annotation.Nullable;

/**
 * This class is a holder of a class-object-pair. The class-information doesn't
 * have to equal the class of the object.
 */
public class ClassObjectPair {

	@Nonnull
	private Class klass;

	@Nullable
	private Object object;

	/**
	 * Constructor creates <code>ClassObjectPair</code> with the given
	 * parameters. <code>class</code> doesn't have to equal the class of the
	 * <code>object</code>.
	 *
	 * @param klass <code>Class</code> of the pair
	 * @param object Object of the pair.
	 */
	public ClassObjectPair(@Nonnull Class klass, @Nullable Object object) {
		this.klass = klass;
		this.object = object;
	}

	/**
	 * Returns the <code>klass</code> member.
	 *
	 * @return <code>klass</code> member
	 */
	@Nonnull
	public Class getKlass() {
		return klass;
	}

	/**
	 * Returns the <code>object</code> member.
	 *
	 * @return <code>object</code> member
	 */
	@Nullable
	public Object getObject() {
		return object;
	}
}
