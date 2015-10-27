package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.Nullable;

public interface CreateMissedRunWithAnnotationProblemDescriptorService {
    @Nullable
    ProblemDescriptor createProblemDescriptor(InspectionManager inspectionManager, PsiClass psiClass);
}
