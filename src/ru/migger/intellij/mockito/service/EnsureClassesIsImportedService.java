package ru.migger.intellij.mockito.service;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

public interface EnsureClassesIsImportedService {
    void ensureIsImported(PsiJavaFile javaFile, PsiClass first);
}
