package ru.migger.intellij.mockito.service;

import com.intellij.psi.PsiField;

public interface CheckPsiFieldHaveMockitoAnnotationService {
    boolean isPsiFiledHaveMockitoAnnotation(PsiField psiField);
}
