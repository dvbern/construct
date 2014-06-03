/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 10:28:09 $ - $Author: meth $ - $Revision: 1.1 $
 */
package ch.dvbern.lib.resource.construct.xml;

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
