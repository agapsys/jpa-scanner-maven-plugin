/*
 * Copyright 2015 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.agapsys.jpa.scanner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Represents a JAR file containing JPA information. 
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
class JarFile {
	// CLASS SCOPE =============================================================
	/** Embedded resource inside a JAR file. */
	public static final String EMBEDDED_XML = "META-INF/" + CreateMojo.OUTPUT_FILENAME;
	
	/**
	 * Returns JPA information contained in jar file
	 * @param jarFile JAR file
	 * @return JPA information contained in jar file or null if this jar file does not contain JPA information
	 * @throws ParsingException if there was an error while processing the file
	 */
	public static JpaInfo getJpaInfo(File jarFile) throws ParsingException {
		return new JarFile(jarFile).getJpaInfo();
	}
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	private final JpaInfo jpaInfo;
	
	/**
	 * Constructor.
	 * @param jarFile JAR file
	 * @throws ParsingException if there was an error while processing the file
	 */
	private JarFile(File jarFile) throws ParsingException {
		InputStream is = null;
		try {
			URL[] urls = {jarFile.toURI().toURL()};
			ClassLoader cl = new URLClassLoader(urls);
			
			is = cl.getResourceAsStream(EMBEDDED_XML);
			
			if (is == null) {
				jpaInfo = null;
				return;
			}
			
			jpaInfo = JpaInfo.fromXml(is);
						
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (is != null)
				try {
					is.close();
			} catch (IOException ex) {
				throw new ParsingException(ex);
			}
		}
	}
	
	/**
	 * Returns JPA information contained in jar file
	 * @return JPA information contained in jar file or null if this jar file does not contain JPA information
	 */
	private JpaInfo getJpaInfo() {
		return jpaInfo;
	}
	// =========================================================================
}