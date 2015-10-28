package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifierList;
import ru.migger.intellij.mockito.ClassNames;

public class CheckPsiClassHaveProperRunWithAnnotatiosServiceImpl extends ApplicationComponent.Adapter implements CheckPsiClassHaveProperRunWithAnnotatiosService {

    public static final String VALUE = "value";

    private final GetPsiAnnotationDotClassParameterService getPsiAnnotationDotClassParameterService;

    public CheckPsiClassHaveProperRunWithAnnotatiosServiceImpl(GetPsiAnnotationDotClassParameterService getPsiAnnotationDotClassParameterService) {
        this.getPsiAnnotationDotClassParameterService = getPsiAnnotationDotClassParameterService;
    }

    @Override
    public boolean isPsiClassHaveProperAnnotation(PsiClass psiClass) {
        final PsiModifierList modifierList = psiClass.getModifierList();
        if(modifierList == null)
            return false;
        for (PsiAnnotation psiAnnotation : modifierList.getAnnotations()) {
            if (ClassNames.RUN_WITH_ANNOTATION.equals(psiAnnotation.getQualifiedName())) {
                final String valueParameterClassName = getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, VALUE);
                if(ClassNames.MOCKITO_JUNIT_RUNNER.equals(valueParameterClassName))
                    return true;
            }
        }
        return false;
    }
}
