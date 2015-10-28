package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.*;
import com.intellij.designer.model.QuickFix;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;

public class CreateMissedRunWithAnnotationProblemDescriptorServiceImpl extends ApplicationComponent.Adapter implements CreateMissedRunWithAnnotationProblemDescriptorService {
    private final CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService;

    public CreateMissedRunWithAnnotationProblemDescriptorServiceImpl(CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService) {
        this.createMissedRunWithAnnotationLocalQuickFixService = createMissedRunWithAnnotationLocalQuickFixService;
    }

    @Override
    public ProblemDescriptor createProblemDescriptor(InspectionManager inspectionManager, PsiClass psiClass) {
        final PsiIdentifier nameIdentifier = psiClass.getNameIdentifier();
        if(nameIdentifier == null)
            return null;
        return inspectionManager.createProblemDescriptor(
                nameIdentifier,
                "Class is not annotated with @RunWith(MockitoJUnitRunner.class)", true,
                createMissedRunWithAnnotationLocalQuickFixService.createLocalQuickFixes(psiClass),
                ProblemHighlightType.GENERIC_ERROR);
    }
}
