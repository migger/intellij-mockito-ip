package ru.migger.intellij.mockito;

import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.migger.intellij.mockito.service.CheckPsiClassHaveProperRunWithAnnotatiosService;
import ru.migger.intellij.mockito.service.CheckPsiFieldHaveMockitoAnnotationService;
import ru.migger.intellij.mockito.service.CreateMissedRunWithAnnotationProblemDescriptorService;
import ru.migger.intellij.mockito.service.GetPsiFieldParentClassService;

public class MockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#ru.migger.intellij.mockito.MockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection");

    private final CheckPsiFieldHaveMockitoAnnotationService checkPsiFieldHaveMockitoAnnotationService;
    private final GetPsiFieldParentClassService getPsiFieldParentClassService;
    private final CheckPsiClassHaveProperRunWithAnnotatiosService checkPsiClassHaveProperRunWithAnnotatiosService;
    private final CreateMissedRunWithAnnotationProblemDescriptorService createMissedRunWithAnnotationProblemDescriptorService;

    public MockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection(
            CheckPsiFieldHaveMockitoAnnotationService checkPsiFieldHaveMockitoAnnotationService,
            GetPsiFieldParentClassService getPsiFieldParentClassService,
            CheckPsiClassHaveProperRunWithAnnotatiosService checkPsiClassHaveProperRunWithAnnotatiosService,
            CreateMissedRunWithAnnotationProblemDescriptorService createMissedRunWithAnnotationProblemDescriptorService) {
        this.checkPsiFieldHaveMockitoAnnotationService = checkPsiFieldHaveMockitoAnnotationService;
        this.getPsiFieldParentClassService = getPsiFieldParentClassService;
        this.checkPsiClassHaveProperRunWithAnnotatiosService = checkPsiClassHaveProperRunWithAnnotatiosService;
        this.createMissedRunWithAnnotationProblemDescriptorService = createMissedRunWithAnnotationProblemDescriptorService;
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkField(@NotNull PsiField field, @NotNull InspectionManager manager, boolean isOnTheFly) {
       if(!checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(field))
           return null;
        final PsiClass parentClass = getPsiFieldParentClassService.getParentClass(field);
        if(parentClass == null)
            return null;
        if(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(parentClass)) {
            return null;
        }
        final ProblemDescriptor problemDescriptor = createMissedRunWithAnnotationProblemDescriptorService.createProblemDescriptor(manager, field);
        if(problemDescriptor == null)
            return null;
        return new ProblemDescriptor[]{problemDescriptor};
    }
}
