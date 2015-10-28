package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiClass;

public interface CreateMissedRunWithAnnotationLocalQuickFixService {
    LocalQuickFix[] createLocalQuickFixes(PsiClass psiClass);
}
