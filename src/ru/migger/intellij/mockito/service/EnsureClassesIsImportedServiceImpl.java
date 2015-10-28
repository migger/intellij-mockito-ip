package ru.migger.intellij.mockito.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.*;

public class EnsureClassesIsImportedServiceImpl extends ApplicationComponent.Adapter implements EnsureClassesIsImportedService {
    private final AddImportService addImportService;

    public EnsureClassesIsImportedServiceImpl(AddImportService addImportService) {
        this.addImportService = addImportService;
    }

    @Override
    public void ensureIsImported(PsiJavaFile javaFile, PsiClass toImport) {
        if (javaFile.getImportList() != null) {
            for (PsiImportStatement psiImportStatement : javaFile.getImportList().getImportStatements()) {
                final String importQualifiedName = psiImportStatement.getQualifiedName();
                if(importQualifiedName != null && importQualifiedName.equals(toImport.getQualifiedName()))
                    return;
            }
        }
        addImportService.addImportStatement(javaFile, toImport);
    }
}
