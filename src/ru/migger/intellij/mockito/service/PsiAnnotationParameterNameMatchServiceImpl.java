package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiNameValuePair;
import org.jetbrains.annotations.NotNull;

public class PsiAnnotationParameterNameMatchServiceImpl extends ApplicationComponent.Adapter implements PsiAnnotationParameterNameMatchService {

    public static final String DEFAULT_NAME = "value";

    @Override
    public boolean isMatched(@NotNull String value, PsiNameValuePair psiNameValuePair) {
        final String name = psiNameValuePair.getName();
        return (name == null && DEFAULT_NAME.equals(value)) || value.equals(name);
    }
}
