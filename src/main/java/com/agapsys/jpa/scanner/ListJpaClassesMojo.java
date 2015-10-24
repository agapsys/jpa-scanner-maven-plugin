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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "list-jpa-classes", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class ListJpaClassesMojo extends AbstractMojo {

	@Parameter(property = "project", readonly = true)
	private MavenProject mavenProject;

	@Parameter(defaultValue = "jpa-classes")
	private String filterProperty;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			// Source directory JPA information...
			JpaInfo srcDirInfo = SourceDirectory.getJpaInfo(new File(mavenProject.getBuild().getSourceDirectory()));

			// JAR files with JPA information...
			List<JpaInfo> jarInfoList = new LinkedList<JpaInfo>();

			Set<Artifact> dependencies = mavenProject.getArtifacts();
			for (Artifact artifact : dependencies) {
				JpaInfo tmpInfo = JarFile.getJpaInfo(artifact.getFile());
				if (tmpInfo != null)
					jarInfoList.add(tmpInfo);
			}
			
			// Global JPA information...
			JpaInfo[] jpaInfoArray = new JpaInfo[jarInfoList.size() + 1];
			jpaInfoArray[0] = srcDirInfo;
			for (int i = 1; i < jpaInfoArray.length; i++) {
				jpaInfoArray[i] = jarInfoList.get(i - 1);
			}
			JpaInfo globalJpaInfo = new JpaInfo(jpaInfoArray);
			
			// persistence.xml classes
			StringBuilder sb = new StringBuilder();
			for (String clazz : globalJpaInfo.getClasses()) {
				sb.append(String.format("<class>%s</class>", clazz));
			}

			mavenProject.getProperties().setProperty(filterProperty, sb.toString());
		} catch (ParsingException ex) {
			throw new MojoExecutionException(ex.getMessage());
		}
	}
	// =========================================================================
}
