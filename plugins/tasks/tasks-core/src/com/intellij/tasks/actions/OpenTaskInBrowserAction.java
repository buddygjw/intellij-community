/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.intellij.tasks.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.tasks.TaskManager;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dmitry Avdeev
 */
public class OpenTaskInBrowserAction extends BaseTaskAction {
  
  public void actionPerformed(AnActionEvent e) {
    String url = getIssueUrl(e);
    if (url != null) {
      BrowserUtil.launchBrowser(url);
    }
  }

  @Override
  public void update(AnActionEvent event) {
    super.update(event);
    if (event.getPresentation().isEnabled()) {
      Presentation presentation = event.getPresentation();
      String url = getIssueUrl(event);
      presentation.setEnabled(url != null);
      Project project = getProject(event);
      if (project == null || !TaskManager.getManager(project).getActiveTask().isIssue()) {
        presentation.setText(getTemplatePresentation().getText());
      } else {
        presentation.setText("Open '" + TaskManager.getManager(project).getActiveTask().getPresentableName() + "' In _Browser");
      }
    }
  }

  @Nullable
  private static String getIssueUrl(AnActionEvent event) {
    Project project = CommonDataKeys.PROJECT.getData(event.getDataContext());
    return project == null ? null : TaskManager.getManager(project).getActiveTask().getIssueUrl();
  }
}
