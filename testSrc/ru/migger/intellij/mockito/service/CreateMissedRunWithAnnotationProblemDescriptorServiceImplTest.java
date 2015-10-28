package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateMissedRunWithAnnotationProblemDescriptorServiceImplTest {
    private CreateMissedRunWithAnnotationProblemDescriptorService createMissedRunWithAnnotationProblemDescriptorService;
    private CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService;

    @Before
    public void setUp() throws Exception {
        createMissedRunWithAnnotationLocalQuickFixService = mock(CreateMissedRunWithAnnotationLocalQuickFixService.class);
        createMissedRunWithAnnotationProblemDescriptorService = new CreateMissedRunWithAnnotationProblemDescriptorServiceImpl(createMissedRunWithAnnotationLocalQuickFixService);
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(createMissedRunWithAnnotationProblemDescriptorService instanceof ApplicationComponent);
    }

    @Test
    public void testClassNameIsNull() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        assertNull(createMissedRunWithAnnotationProblemDescriptorService.createProblemDescriptor(inspectionManager, psiClass));
    }

    @Test
    public void testValidDescriptor() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        final ProblemDescriptor problemDescriptor = mock(ProblemDescriptor.class);
        final PsiIdentifier nameIdentifier = mock(PsiIdentifier.class);
        final LocalQuickFix[] localQuickFixes = new LocalQuickFix[] {mock(LocalQuickFix.class)};
        when(psiClass.getNameIdentifier()).thenReturn(nameIdentifier);
        when(createMissedRunWithAnnotationLocalQuickFixService.createLocalQuickFixes(psiClass)).thenReturn(localQuickFixes);
        when(inspectionManager.createProblemDescriptor(
                nameIdentifier,
                "Class is not annotated with @RunWith(MockitoJUnitRunner.class)",
                true,
                localQuickFixes,
                ProblemHighlightType.GENERIC_ERROR)).thenReturn(problemDescriptor);
        assertEquals(problemDescriptor, createMissedRunWithAnnotationProblemDescriptorService.createProblemDescriptor(inspectionManager, psiClass));
    }
}