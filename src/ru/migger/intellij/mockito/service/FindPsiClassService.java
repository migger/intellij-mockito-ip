package ru.migger.intellij.mockito.service;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public interface FindPsiClassService {
    PsiClass findClassByFullName(Project project, String fullName);
}
