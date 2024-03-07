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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of ResourceLocator. Needs a file-path-information for locating the resources.
 * <br>As {@code ResourceLocator} this class acts as event source for {@code ResourceChangedEvent}s
 * and implements the methods {@code addResourceChangeListener} and {@code removeResourceChangeListener}.
 *
 * @see ResourceChangedEvent
 */
public class FilePathResourceLocator implements ResourceLocator {

	private static final Logger LOG = LoggerFactory.getLogger(FilePathResourceLocator.class);

	@NonNull
	private final String path;
	@NonNull
	@SuppressWarnings("PMD.LooseCoupling")
	private final HashSet<ResourceChangeListener> listeners;
	@NonNull
	@SuppressWarnings("PMD.LooseCoupling")
	private final HashSet<File> files;
	@NonNull
	private final ResourceChecker resourceChecker;

	/**
	 * Constructor.
	 * Sets as period of resource-change-check 10'000 ms.
	 *
	 * @param path Filepath where the resources are located.
	 */
	public FilePathResourceLocator(@NonNull String path) {
		this(path, 10000);
	}

	/**
	 * Constructor.
	 *
	 * @param path File path where the resources are located.
	 * @param resChangeCheckPeriod Value (in ms) defining the period of resource-change-check
	 */
	public FilePathResourceLocator(@NonNull String path, long resChangeCheckPeriod) {
		this.path = path;
		listeners = new HashSet<>();
		files = new HashSet<>();

		resourceChecker = new ResourceChecker(resChangeCheckPeriod);
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
	@Override
	@NonNull
	@SuppressWarnings("PMD.PreserveStackTrace")
	public InputStream getResourceAsStream(@NonNull String resourceName) throws ResourceNotFoundException {
		try {

			File file = new File(path, resourceName);
			synchronized (files) {
				files.add(file);
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
	@Override
	public void addResourceChangeListener(@NonNull ResourceChangeListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	/**
	 * Method de-registers listeners.
	 *
	 * @param listener registered listener that has to be removed
	 */
	@Override
	public void removeResourceChangeListener(@NonNull ResourceChangeListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Method notifies all registered {@code ResourceChangeListener}.
	 *
	 * @param resource Name of resource that has been changed
	 */
	protected void notifyResourceChange(@NonNull String resource) {
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
				LOG.error("Error while notifying listener: {}", listener, ex);
			}
		}
	}

	/**
	 * Method notifies all registered {@code ResourceChangeListener}.
	 *
	 * @param resource Name of resource that has been removed
	 */
	protected void notifyResourceRemoved(@NonNull String resource) {
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
				LOG.error("Error while notifying listener: {}", listener, ex);
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
		private final long checkPeriod;
		private volatile boolean run;

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
		@Override
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
					LOG.warn("Interrupted while waiting for checkPeriod of {}ms", checkPeriod, ex);
				}
			}
		}

	}

}
