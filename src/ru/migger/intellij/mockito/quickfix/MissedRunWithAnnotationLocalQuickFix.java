package ru.migger.intellij.mockito.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import ru.migger.intellij.mockito.ClassNames;
import ru.migger.intellij.mockito.service.EnsureClassesIsImportedService;
import ru.migger.intellij.mockito.service.FindPsiClassService;

public class MissedRunWithAnnotationLocalQuickFix implements LocalQuickFix{
    private final FindPsiClassService findPsiClassService;
    private final EnsureClassesIsImportedService ensureClassesIsImportedService;
    private final PsiClass psiClass;

    public static MissedRunWithAnnotationLocalQuickFix create(
            FindPsiClassService findPsiClassService,
            EnsureClassesIsImportedService ensureClassesIsImportedService, PsiClass psiClass) {
        return new MissedRunWithAnnotationLocalQuickFix(findPsiClassService, ensureClassesIsImportedService, psiClass);
    }

    private MissedRunWithAnnotationLocalQuickFix(
            FindPsiClassService findPsiClassService,
            EnsureClassesIsImportedService ensureClassesIsImportedService, PsiClass psiClass) {
        this.findPsiClassService = findPsiClassService;
        this.ensureClassesIsImportedService = ensureClassesIsImportedService;
        this.psiClass = psiClass;
    }

    @Nls
    @NotNull
    @Override
    public String getName() {
        return "Add @RunWith(MockitoJUnitRunner.class)";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "MockitoTTD";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        final PsiModifierList modifierList = psiClass.getModifierList();
        if(modifierList != null) {
            final PsiClass runWith = findPsiClassService.findClassByFullName(project, ClassNames.RUN_WITH_ANNOTATION);
            final PsiClass testRunner = findPsiClassService.findClassByFullName(project, ClassNames.MOCKITO_JUNIT_RUNNER);
            final PsiJavaFile psiJavaFile = (PsiJavaFile) psiClass.getContainingFile();
            ensureClassesIsImportedService.ensureIsImported(psiJavaFile, runWith);
            ensureClassesIsImportedService.ensureIsImported(psiJavaFile, testRunner);
            modifierList.addAnnotation(String.format("%s(%s.class)", runWith.getName(), testRunner.getName()));
        }
    }
}
