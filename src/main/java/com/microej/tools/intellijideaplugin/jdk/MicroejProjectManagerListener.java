/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.tools.intellijideaplugin.jdk;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;

public class MicroejProjectManagerListener implements ProjectManagerListener {
	@Override
	public void projectOpened(@NotNull Project project) {
		Logger log = Logger.getInstance("microej");

		Runnable r = () -> {
			Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();
			if (projectSdk != null) {
				log.warn("Project has been opened, removing the SDK: " + projectSdk.getName());
				ProjectRootManager.getInstance(project).setProjectSdk(null);
			} else {
				log.warn("Project has been opened, no SDK");
			}
		};
		WriteCommandAction.runWriteCommandAction(project, r);
	}
}
