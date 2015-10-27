package ru.migger.intellij.mockito.service;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.Nullable;

public interface GetPsiFieldParentClassService {
    @Nullable
    PsiClass getParentClass(PsiField psiField);
}
