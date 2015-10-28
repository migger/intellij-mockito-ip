package ru.migger.intellij.mockito.quickfix;

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiModifierList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import ru.migger.intellij.mockito.ClassNames;
import ru.migger.intellij.mockito.service.EnsureClassesIsImportedService;
import ru.migger.intellij.mockito.service.FindPsiClassService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MissedRunWithAnnotationLocalQuickFixTest {
    private MissedRunWithAnnotationLocalQuickFix missedXRunWithAnnotationLocalQuickFix;
    private EnsureClassesIsImportedService ensureClassesIsImportedService;
    private FindPsiClassService findPsiClassService;
    private PsiClass psiClass;

    @Before
    public void setUp() throws Exception {
        ensureClassesIsImportedService = mock(EnsureClassesIsImportedService.class);
        findPsiClassService = mock(FindPsiClassService.class);
        psiClass = mock(PsiClass.class);
        missedXRunWithAnnotationLocalQuickFix = MissedRunWithAnnotationLocalQuickFix.create(
                findPsiClassService,
                ensureClassesIsImportedService,
                psiClass);
    }

    @Test
    public void testIsLocalQuickFix() throws Exception {
        assertEquals("Add @RunWith(MockitoJUnitRunner.class)", missedXRunWithAnnotationLocalQuickFix.getName());
        assertEquals("MockitoTTD", missedXRunWithAnnotationLocalQuickFix.getFamilyName());
    }

    @Test
    public void testDoFix() throws Exception {
        final Project project = mock(Project.class);
        final ProblemDescriptor problemDescriptor = mock(ProblemDescriptor.class);
        final PsiClass runWith = mock(PsiClass.class);
        final PsiClass mockitoTestRunner = mock(PsiClass.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        when(findPsiClassService.findClassByFullName(project, ClassNames.RUN_WITH_ANNOTATION)).thenReturn(runWith);
        when(findPsiClassService.findClassByFullName(project, ClassNames.MOCKITO_JUNIT_RUNNER)).thenReturn(mockitoTestRunner);
        when(runWith.getName()).thenReturn("RunWith");
        when(mockitoTestRunner.getName()).thenReturn("MockitoJUnitRunner");
        when(psiClass.getModifierList()).thenReturn(psiModifierList);
        when(psiClass.getContainingFile()).thenReturn(psiJavaFile);
        missedXRunWithAnnotationLocalQuickFix.applyFix(project, problemDescriptor);
        final InOrder order = inOrder(ensureClassesIsImportedService, psiModifierList);
        order.verify(ensureClassesIsImportedService).ensureIsImported(psiJavaFile, runWith);
        order.verify(ensureClassesIsImportedService).ensureIsImported(psiJavaFile, mockitoTestRunner);
        order.verify(psiModifierList).addAnnotation("RunWith(MockitoJUnitRunner.class)");
    }

    @Test
    public void testDoFixModifyerListIsNull() throws Exception {
        final Project project = mock(Project.class);
        final ProblemDescriptor problemDescriptor = mock(ProblemDescriptor.class);
        missedXRunWithAnnotationLocalQuickFix.applyFix(project, problemDescriptor);
        verify(ensureClassesIsImportedService, never()).ensureIsImported(any(), any());
    }
}