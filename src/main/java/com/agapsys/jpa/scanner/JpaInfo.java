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

import com.thoughtworks.xstream.XStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

public class JpaInfo {
	// CLASS SCOPE =============================================================
	private static final XStream XSTREAM;
	
	static {
		XSTREAM = new XStream();
		XSTREAM.alias("jpaInfo", JpaInfo.class);
		XSTREAM.alias("class",   String.class);
		XSTREAM.alias("classes", Set.class, LinkedHashSet.class);
	}
	
	public static JpaInfo fromXml(InputStream xmlInputStream) {
		return (JpaInfo) XSTREAM.fromXML(xmlInputStream);
	}
	
	public static JpaInfo fromXml(Reader reader) {
		return (JpaInfo) XSTREAM.fromXML(reader);
	}
	
	public static JpaInfo fomXml(String xmlString) {
		return (JpaInfo) XSTREAM.fromXML(xmlString);
	}
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	private final Set<String> classes = new LinkedHashSet<String>();
	
	public JpaInfo(String...classes) {
		for (String className : classes) {
			if (className == null) throw new IllegalArgumentException("Null className");
			if (!this.classes.add(className)) throw new IllegalArgumentException("Duplicate class: " + className);
		}
	}
			
	public JpaInfo(JpaInfo...others) {
		for (JpaInfo other : others) {
			for (String className : other.getClasses()) {
				if (!this.classes.add(className)) throw new IllegalArgumentException("Duplicate class: " + className);
			}
		}
	}
	
	public Set<String> getClasses() {
		return classes;
	}
	
	public String toXml() {
		return XSTREAM.toXML(this);
	}
	
	public void toXml(OutputStream outputStream) {
		XSTREAM.toXML(this, outputStream);
	}
	
	public void toXml(Writer writer) {
		XSTREAM.toXML(this, writer);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String className : getClasses()) {
			if (i > 0) sb.append(", ");
			sb.append(className);
			i++;
		}
		return String.format("[%s]", sb.toString());
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + (this.classes != null ? this.classes.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JpaInfo other = (JpaInfo) obj;
		if (this.classes != other.classes && (this.classes == null || !this.classes.equals(other.classes))) {
			return false;
		}
		return true;
	}
	// =========================================================================
}