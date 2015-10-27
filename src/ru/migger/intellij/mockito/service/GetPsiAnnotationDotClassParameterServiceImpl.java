package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClassObjectAccessExpression;
import com.intellij.psi.PsiNameValuePair;

public class GetPsiAnnotationDotClassParameterServiceImpl extends ApplicationComponent.Adapter implements GetPsiAnnotationDotClassParameterService {
    private final PsiAnnotationParameterNameMatchService psiAnnotationParameterNameMatchService;

    public GetPsiAnnotationDotClassParameterServiceImpl(PsiAnnotationParameterNameMatchService psiAnnotationParameterNameMatchService) {
        this.psiAnnotationParameterNameMatchService = psiAnnotationParameterNameMatchService;
    }

    @Override
    public String getQualifiedClassNameOfPsiAnnotationParameter(PsiAnnotation psiAnnotation, String parameterName) {
        for (PsiNameValuePair nameValuePair : psiAnnotation.getParameterList().getAttributes()) {
            if(psiAnnotationParameterNameMatchService.isMatched(parameterName, nameValuePair)) {
                final PsiAnnotationMemberValue value = nameValuePair.getValue();
                if(value instanceof PsiClassObjectAccessExpression)
                    return ((PsiClassObjectAccessExpression) value).getOperand().getType().getCanonicalText();
            }
        }
        return null;
    }
}
