package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiIdentifier;

public class CreateMissedRunWithAnnotationProblemDescriptorServiceImpl extends ApplicationComponent.Adapter implements CreateMissedRunWithAnnotationProblemDescriptorService {
    private final GetPsiFieldParentClassService getPsiFieldParentClassService;
    private final CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService;

    public CreateMissedRunWithAnnotationProblemDescriptorServiceImpl(GetPsiFieldParentClassService getPsiFieldParentClassService, CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService) {
        this.getPsiFieldParentClassService = getPsiFieldParentClassService;
        this.createMissedRunWithAnnotationLocalQuickFixService = createMissedRunWithAnnotationLocalQuickFixService;
    }

    @Override
    public ProblemDescriptor createProblemDescriptor(InspectionManager inspectionManager, PsiField psiField) {
        final PsiIdentifier nameIdentifier = psiField.getNameIdentifier();
        return inspectionManager.createProblemDescriptor(
                nameIdentifier,
                "Class is not annotated with @RunWith(MockitoJUnitRunner.class)", true,
                createMissedRunWithAnnotationLocalQuickFixService.createLocalQuickFixes(getPsiFieldParentClassService.getParentClass(psiField)),
                ProblemHighlightType.GENERIC_ERROR);
    }
}
