/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
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

import com.agapsys.mvn.scanner.ScanInfo;
import com.agapsys.mvn.scanner.ScannerDefs;
import com.agapsys.mvn.scanner.SourceDirectoryScanner;

/**
 * JPA implementation of ScannerDefs
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class JpaScannerDefs extends ScannerDefs {
	// STATIC SCOPE ============================================================
	private static final JpaScannerDefs SINGLETON = new JpaScannerDefs();
	
	public static JpaScannerDefs getInstance() {
		return SINGLETON;
	}
	
	public static final String OPTION_INCLUDE_DEPENDENCIES = "includeDependencies";
	public static final String OPTION_INCLUDE_TESTS        = "includeTests";
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	private JpaScannerDefs() {}
	
	@Override
	public SourceDirectoryScanner getSourceDirectoryScanner() {
		return JpaSourceDirectoryScanner.getInstance();
	}

	@Override
	public ScanInfo getScanInfoInstance() {
		return new JpaScanInfo();
	}

	@Override
	public String getEmbeddedScanInfoFilename() {
		return "jpa.info";
	}
	// =========================================================================	
}
