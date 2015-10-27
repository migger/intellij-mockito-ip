package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifierList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CheckPsiFieldHaveMockitoAnnotationServiceImpl extends ApplicationComponent.Adapter implements CheckPsiFieldHaveMockitoAnnotationService {
    public static final Set<String> MOCKITO_ANNOTATIONS_QUALIFIED_NAMES = new HashSet<>(Arrays.asList(
            "org.mockito.InjectMocks",
            "org.mockito.Mock"
    ));
    @Override
    public boolean isPsiFiledHaveMockitoAnnotation(PsiField psiField) {
        final PsiModifierList modifierList = psiField.getModifierList();
        if(modifierList == null)
            return false;
        for (PsiAnnotation psiAnnotation : modifierList.getAnnotations()) {
            if(MOCKITO_ANNOTATIONS_QUALIFIED_NAMES.contains(psiAnnotation.getQualifiedName()))
                return true;
        }
        return false;
    }
}
