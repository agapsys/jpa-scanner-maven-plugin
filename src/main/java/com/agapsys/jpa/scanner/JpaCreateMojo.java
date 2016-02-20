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

import com.agapsys.mvn.scanner.AbstractCreateMojo;
import com.agapsys.mvn.scanner.ScannerDefs;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * JPA implementation of AbstractCreateMojo
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
@Mojo(name = "create", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class JpaCreateMojo extends AbstractCreateMojo {

	@Parameter(property = "project", readonly = true)
	private MavenProject mavenProject;
	
	@Override
	protected MavenProject getMavenProject() {
		return mavenProject;
	}
	
	@Parameter(defaultValue = "false", name = "include-dependencies")
	private boolean includeDependencies;

	@Override
	protected boolean includeDependencies() {
		return includeDependencies;
	}
	
	@Parameter(defaultValue = "false", name = "include-tests")
	private boolean includeTests;

	@Override
	protected boolean includeTests() {
		return includeTests;
	}
	
	@Override
	protected ScannerDefs getScannerDefs() {
		return JpaScannerDefs.getInstance();
	}
	
}
