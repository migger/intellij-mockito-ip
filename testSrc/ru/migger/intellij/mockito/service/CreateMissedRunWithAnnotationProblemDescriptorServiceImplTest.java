package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiIdentifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateMissedRunWithAnnotationProblemDescriptorServiceImplTest {
    private CreateMissedRunWithAnnotationProblemDescriptorService createMissedRunWithAnnotationProblemDescriptorService;
    private CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService;
    private GetPsiFieldParentClassService getPsiFieldParentClassService;

    @Before
    public void setUp() throws Exception {
        getPsiFieldParentClassService = mock(GetPsiFieldParentClassService.class);
        createMissedRunWithAnnotationLocalQuickFixService = mock(CreateMissedRunWithAnnotationLocalQuickFixService.class);
        createMissedRunWithAnnotationProblemDescriptorService = new CreateMissedRunWithAnnotationProblemDescriptorServiceImpl(
                getPsiFieldParentClassService,
                createMissedRunWithAnnotationLocalQuickFixService);
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(createMissedRunWithAnnotationProblemDescriptorService instanceof ApplicationComponent);
    }

    @Test
    public void testValidDescriptor() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final PsiField psiField = mock(PsiField.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        final ProblemDescriptor problemDescriptor = mock(ProblemDescriptor.class);
        final PsiIdentifier nameIdentifier = mock(PsiIdentifier.class);
        final LocalQuickFix[] localQuickFixes = new LocalQuickFix[] {mock(LocalQuickFix.class)};
        when(psiField.getNameIdentifier()).thenReturn(nameIdentifier);
        when(getPsiFieldParentClassService.getParentClass(psiField)).thenReturn(psiClass);
        when(createMissedRunWithAnnotationLocalQuickFixService.createLocalQuickFixes(psiClass)).thenReturn(localQuickFixes);
        when(inspectionManager.createProblemDescriptor(
                nameIdentifier,
                "Class is not annotated with @RunWith(MockitoJUnitRunner.class)",
                true,
                localQuickFixes,
                ProblemHighlightType.GENERIC_ERROR)).thenReturn(problemDescriptor);
        assertEquals(problemDescriptor, createMissedRunWithAnnotationProblemDescriptorService.createProblemDescriptor(inspectionManager, psiField));
    }
}