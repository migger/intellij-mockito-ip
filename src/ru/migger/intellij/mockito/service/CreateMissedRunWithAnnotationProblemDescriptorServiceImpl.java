package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.*;
import com.intellij.designer.model.QuickFix;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;

public class CreateMissedRunWithAnnotationProblemDescriptorServiceImpl extends ApplicationComponent.Adapter implements CreateMissedRunWithAnnotationProblemDescriptorService {
    @Override
    public ProblemDescriptor createProblemDescriptor(InspectionManager inspectionManager, PsiClass psiClass) {
        final PsiIdentifier nameIdentifier = psiClass.getNameIdentifier();
        if(nameIdentifier == null)
            return null;
        return inspectionManager.createProblemDescriptor(
                nameIdentifier,
                "Class is not annotated with @RunWith(MockitoJUnitRunner.class)", true, new LocalQuickFix[0],
                ProblemHighlightType.GENERIC_ERROR);
    }
}
