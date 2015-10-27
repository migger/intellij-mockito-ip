package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.Nullable;

public class GetPsiFieldParentClassServiceImpl extends ApplicationComponent.Adapter implements GetPsiFieldParentClassService {
    @Nullable
    @Override
    public PsiClass getParentClass(PsiField psiField) {
        final PsiElement parent = psiField.getParent();
        if(parent instanceof PsiClass)
            return (PsiClass) parent;
        return null;
    }
}
