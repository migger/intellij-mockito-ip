package ru.migger.intellij.mockito.service;

import com.intellij.psi.PsiAnnotation;

public interface GetPsiAnnotationDotClassParameterService {
    String getQualifiedClassNameOfPsiAnnotationParameter(PsiAnnotation psiAnnotation, String parameterName);
}
