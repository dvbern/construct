/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/20 12:14:23 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of ResourceLocator. Needs a file-path-information for locating the resources.
 * <br>As <code>ResourceLocator</code> this class acts as event source for <code>ResourceChangedEvent</code>s
 * and implements the methods <code>addResourceChangeListener</code> and <code>removeResourceChangeListener</code>.
 *
 * @see ResourceChangedEvent
 */
public class FilePathResourceLocator implements ResourceLocator {

	private static final Logger LOG = LoggerFactory.getLogger(FilePathResourceLocator.class);

	@Nonnull
	private final String path;
	@Nonnull
	@SuppressWarnings("PMD.LooseCoupling")
	private final HashSet<ResourceChangeListener> listeners;
	@Nonnull
	@SuppressWarnings("PMD.LooseCoupling")
	private final HashSet<File> files;
	@Nonnull
	private final ResourceChecker resourceChecker;

	/**
	 * Constructor.
	 * Sets as period of resource-change-check 10'000 ms.
	 *
	 * @param path Filepath where the resources are located.
	 */
	public FilePathResourceLocator(@Nonnull String path) {
		this(path, 10000);
	}

	/**
	 * Constructor.
	 *
	 * @param path File path where the resources are located.
	 * @param resChangeCheckPeriod Value (in ms) defining the period of resource-change-check
	 */
	public FilePathResourceLocator(@Nonnull String path, long resChangeCheckPeriod) {
		this.path = path;
		this.listeners = new HashSet<ResourceChangeListener>();
		this.files = new HashSet<File>();

		this.resourceChecker = new ResourceChecker(resChangeCheckPeriod);
		Thread thread = new Thread(resourceChecker);
		thread.start();
	}

	/**
	 * Method specified resource as InputStream or throws Exception.
	 *
	 * @param resourceName Name of resource to locate (name of xml-file)
	 * @return InputStream: resource as InputStream; never null.
	 * @throws ResourceNotFoundException if specified resource could not have been found
	 */
	@Nonnull
	@SuppressWarnings("PMD.PreserveStackTrace")
	public InputStream getResourceAsStream(@Nonnull String resourceName) throws ResourceNotFoundException {
		try {

			File file = new File(path, resourceName);
			synchronized (files) {
				if (!files.contains(file)) {
					files.add(file);
					//System.out.println("================FilePathResourceLocator: added to files-cache:" +file);
				}
			}
			return new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			throw new ResourceNotFoundException("resource=" + resourceName + " not found");
		}
	}

	/**
	 * Method registers listeners interested in changes or removals of resources.
	 *
	 * @param listener listener interested in changes or removals of resources; must not be null
	 */
	public void addResourceChangeListener(@Nonnull ResourceChangeListener listener) {
		synchronized (listeners) {
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	/**
	 * Method de-registers listeners.
	 *
	 * @param listener registered listener that has to be removed
	 */
	public void removeResourceChangeListener(@Nonnull ResourceChangeListener listener) {
		synchronized (listeners) {
			if (listeners.contains(listener)) {
				listeners.remove(listener);
			}
		}
	}

	/**
	 * Method notifies all registered <code>ResourceChangeListener</code>.
	 *
	 * @param resource Name of resource that has been changed
	 */
	protected void notifyResourceChange(@Nonnull String resource) {
		ResourceChangedEvent event = new ResourceChangedEvent(this, resource);
		Set<ResourceChangeListener> clone;
		synchronized (listeners) {
			@SuppressWarnings("unchecked")
			Set<ResourceChangeListener> tmp = (Set<ResourceChangeListener>) listeners.clone();
			clone = tmp;
		}
		for (ResourceChangeListener listener : clone) {
			try {
				listener.resourceChanged(event);
			} catch (RuntimeException ex) {
				LOG.error("Error while notifying listener: " + listener, ex);
			}
		}
	}

	/**
	 * Method notifies all registered <code>ResourceChangeListener</code>.
	 *
	 * @param resource Name of resource that has been removed
	 */
	protected void notifyResourceRemoved(@Nonnull String resource) {
		ResourceChangedEvent event = new ResourceChangedEvent(this, resource);
		Set<ResourceChangeListener> clone;
		synchronized (listeners) {
			@SuppressWarnings("unchecked")
			Set<ResourceChangeListener> tmp = (Set<ResourceChangeListener>) listeners.clone();
			clone = tmp;
		}
		for (ResourceChangeListener listener : clone) {
			try {
				listener.resourceRemoved(event);
			} catch (RuntimeException ex) {
				LOG.error("Error while notifying listener: " + listener, ex);
			}
		}
	}

	/**
	 * Method stops the resourceChecker which is responsible for checking changes or the resources.
	 */
	public void stopResourceChecker() {
		resourceChecker.stopChecker();
	}

	/**
	 * Responsible for checking the resources for changes. Instantiated in the constructor of FilePathResourceLocator
	 */
	private class ResourceChecker implements Runnable {
		private long lastCheck;
		private long checkPeriod;
		private boolean run;

		/**
		 * Constructor of inner class.
		 *
		 * @param checkPeriod Value (in ms) defining the checkPeriod of resource-change-check (e.g. Thread-sleep)
		 */
		public ResourceChecker(long checkPeriod) {
			lastCheck = System.currentTimeMillis();
			this.checkPeriod = checkPeriod;
			run = true;
		}

		/**
		 * Sets the run-value of the ResourceChecker-Thread to false (smooth stoping)
		 */
		public void stopChecker() {
			run = false;
		}

		/**
		 * Business of the ResourceChecker-Thread
		 */
		public void run() {
			while (run) {
				long tmpCheck = System.currentTimeMillis();
				Set<File> clone;
				synchronized (files) {
					@SuppressWarnings("unchecked")
					Set<File> tmp = (Set<File>) files.clone();
					clone = tmp;
				}

				for (File file : clone) {
					if (!file.exists()) {
						notifyResourceRemoved(file.getName());
					} else if (file.lastModified() >= lastCheck) {
						notifyResourceChange(file.getName());
					}
				}

				lastCheck = tmpCheck;
				try {
					Thread.sleep(checkPeriod);
				} catch (InterruptedException ex) {
					LOG.warn("Interrupted while waiting for checkPeriod of " + checkPeriod + "ms", ex);
				}
			}
		}

	}


}
