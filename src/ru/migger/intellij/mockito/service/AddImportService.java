package ru.migger.intellij.mockito.service;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

public interface AddImportService {
    void addImportStatement(PsiJavaFile psiJavaFile, PsiClass toImport);
}
