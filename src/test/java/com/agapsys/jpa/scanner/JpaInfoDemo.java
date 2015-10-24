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

import org.junit.Test;

public class JpaInfoDemo {
	
	private void println(String msg, Object...args) {
		System.out.println(String.format(msg, args));
	}
	
	@Test
	public void test() {
		JpaInfo lib1 = new JpaInfo("com.project1.Class1", "com.project1.Class2");
		JpaInfo lib2 = new JpaInfo("com.project2.Class1", "com.project2.Class2");
		
		JpaInfo lib3 = new JpaInfo(lib1, lib2);
		
		String xmlString;
		
		xmlString = lib1.toXml();
		println("==== lib1====\n%s", xmlString);
		println(JpaInfo.fomXml(xmlString).toString());
		
		xmlString = lib2.toXml();
		println("\n==== lib2====\n%s", xmlString);
		println(JpaInfo.fomXml(xmlString).toString());
		
		xmlString = lib3.toXml();
		println("\n==== lib3====\n%s", xmlString);
		println(JpaInfo.fomXml(xmlString).toString());
	}
}