/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.tools.intellijideaplugin.jdk;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectExtension;
import com.intellij.openapi.roots.ProjectRootManager;

public class MicroejProjectExtension extends ProjectExtension {

	private Project project;

	public MicroejProjectExtension(Project project) {
		this.project = project;
	}

	@Override
	public void projectSdkChanged(@Nullable Sdk sdk) {
		Logger log = Logger.getInstance("microej");
		log.info("Project SDK has been changed, new sdk: " + sdk);
		if (sdk != null) {
			log.info("Project SDK has been changed, removing it: " + sdk);
			ProjectRootManager.getInstance(project).setProjectSdk(null);
		}
	}

	@Override
	public void readExternal(@NotNull Element element) {
	}

	@Override
	public void writeExternal(@NotNull Element element) {
	}
}
