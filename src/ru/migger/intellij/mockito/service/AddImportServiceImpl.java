package ru.migger.intellij.mockito.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.*;

public class AddImportServiceImpl extends ApplicationComponent.Adapter implements AddImportService {
    @Override
    public void addImportStatement(PsiJavaFile psiJavaFile, PsiClass toImport) {
        final PsiImportStatement importStatement = PsiElementFactory.SERVICE.getInstance(psiJavaFile.getProject()).createImportStatement(toImport);
        final PsiImportList importList = psiJavaFile.getImportList();
        if (importList != null) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                importList.add(importStatement);
            });
        } else if (psiJavaFile.getFirstChild() == null) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                psiJavaFile.add(importStatement.getParent());
            });
        } else if (psiJavaFile.getPackageStatement() != null) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                psiJavaFile.addAfter(psiJavaFile.getPackageStatement(), importStatement.getParent());
            });
        } else {
            ApplicationManager.getApplication().runWriteAction(() -> {
                psiJavaFile.addBefore(psiJavaFile.getFirstChild(), importStatement.getParent());
            });
        }
    }
}
