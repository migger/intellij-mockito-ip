package ru.migger.intellij.mockito.service;

import com.intellij.psi.PsiNameValuePair;

public interface PsiAnnotationParameterNameMatchService {
    boolean isMatched(String value, PsiNameValuePair psiNameValuePair);
}
