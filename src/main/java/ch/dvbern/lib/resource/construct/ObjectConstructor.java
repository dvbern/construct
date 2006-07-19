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
package ch.dvbern.lib.resource.construct;

/**
 * Abstraction of object constuctors. Implementations create and return objects,
 * identified by the passed <code>objectId</code>. They should chache created
 * objects and return the already created instances if <code>newInstance</code>
 * is false.
 *  
 */
public interface ObjectConstructor {

	/**
	 * This method returns an object identified by <code>objectId</code>. A
	 * cached instance may be returned, if <code>newInstance</code> is false.
	 * If there is no definition for the object with the passed
	 * <code>objectId</code> or if the object can not be created, a
	 * <code>ConstructionException</code> is thrown.
	 * 
	 * @param objectId
	 *            The id of the object, which should be created/returned.
	 * @param newInstance
	 *            boolean, indicating whether a new instance should be created
	 *            (true) or if already created (and cached) instances should be
	 *            returned.
	 * @return Object according to the paramter <code>objectId</code>. The
	 *         return value is never null.
	 * @exception ConstructionException
	 *                Thrown, if there is no definition for the object with the
	 *                passed <code>objectId</code> or if the object can not be
	 *                created
	 */
	public Object construct(String objectId, boolean newInstance)
			throws ConstructionException;
}
