package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import ru.migger.intellij.mockito.quickfix.MissedRunWithAnnotationLocalQuickFix;

public class CreateMissedRunWithAnnotationLocalQuickFixServiceImpl extends ApplicationComponent.Adapter implements CreateMissedRunWithAnnotationLocalQuickFixService {
    private final EnsureClassesIsImportedService ensureClassesIsImportedService;
    private final FindPsiClassService findPsiClassService;

    public CreateMissedRunWithAnnotationLocalQuickFixServiceImpl(
            EnsureClassesIsImportedService ensureClassesIsImportedService,
            FindPsiClassService findPsiClassService) {

        this.ensureClassesIsImportedService = ensureClassesIsImportedService;
        this.findPsiClassService = findPsiClassService;
    }

    @Override
    public LocalQuickFix[] createLocalQuickFixes(PsiClass psiClass) {
        final MissedRunWithAnnotationLocalQuickFix missedRunWithAnnotationLocalQuickFix = MissedRunWithAnnotationLocalQuickFix.create(findPsiClassService, ensureClassesIsImportedService, psiClass);
        return new LocalQuickFix[]{
                missedRunWithAnnotationLocalQuickFix
        };
    }
}
