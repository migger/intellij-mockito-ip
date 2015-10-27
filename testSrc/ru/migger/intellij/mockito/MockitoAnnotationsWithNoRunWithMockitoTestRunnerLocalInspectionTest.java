package ru.migger.intellij.mockito;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.junit.Before;
import org.junit.Test;
import ru.migger.intellij.mockito.service.CheckPsiClassHaveProperRunWithAnnotatiosService;
import ru.migger.intellij.mockito.service.CheckPsiFieldHaveMockitoAnnotationService;
import ru.migger.intellij.mockito.service.CreateMissedRunWithAnnotationProblemDescriptorService;
import ru.migger.intellij.mockito.service.GetPsiFieldParentClassService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspectionTest {
    private MockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection;

    private CheckPsiFieldHaveMockitoAnnotationService checkPsiFieldHaveMockitoAnnotationService;
    private GetPsiFieldParentClassService getPsiFieldParentClassService;
    private CheckPsiClassHaveProperRunWithAnnotatiosService checkPsiClassHaveProperRunWithAnnotatiosService;
    private CreateMissedRunWithAnnotationProblemDescriptorService createMissedRunWithAnnotationProblemDescriptorService;

    @Before
    public void setUp() throws Exception {
        checkPsiFieldHaveMockitoAnnotationService = mock(CheckPsiFieldHaveMockitoAnnotationService.class);
        getPsiFieldParentClassService = mock(GetPsiFieldParentClassService.class);
        checkPsiClassHaveProperRunWithAnnotatiosService = mock(CheckPsiClassHaveProperRunWithAnnotatiosService.class);
        createMissedRunWithAnnotationProblemDescriptorService = mock(CreateMissedRunWithAnnotationProblemDescriptorService.class);
        mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection = new MockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection(
                checkPsiFieldHaveMockitoAnnotationService,
                getPsiFieldParentClassService,
                checkPsiClassHaveProperRunWithAnnotatiosService,
                createMissedRunWithAnnotationProblemDescriptorService
        );
    }

    @Test
    public void testNoAnnotation() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        when(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField)).thenReturn(false);
        assertNull(mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection.checkField(psiField, inspectionManager, true));
    }

    @Test
    public void testFieldParentIsNotClass() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        when(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField)).thenReturn(true);
        when(getPsiFieldParentClassService.getParentClass(psiField)).thenReturn(null);
        assertNull(mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection.checkField(psiField, inspectionManager, true));
    }

    @Test
    public void testFieldParentClassHaveAnnotation() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        final PsiClass psiClass = mock(PsiClass.class);
        when(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField)).thenReturn(true);
        when(getPsiFieldParentClassService.getParentClass(psiField)).thenReturn(psiClass);
        when(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass)).thenReturn(true);
        assertNull(mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection.checkField(psiField, inspectionManager, true));
    }

    @Test
    public void testFieldProblemDescriptionIsNull() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        final PsiClass psiClass = mock(PsiClass.class);
        when(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField)).thenReturn(true);
        when(getPsiFieldParentClassService.getParentClass(psiField)).thenReturn(psiClass);
        when(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass)).thenReturn(false);
        assertNull(mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection.checkField(psiField, inspectionManager, true));
    }

    @Test
    public void testFieldParentClassIsNotAnnonatedProperly() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final InspectionManager inspectionManager = mock(InspectionManager.class);
        final PsiClass psiClass = mock(PsiClass.class);
        final ProblemDescriptor problemDescriptor = mock(ProblemDescriptor.class);
        when(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField)).thenReturn(true);
        when(getPsiFieldParentClassService.getParentClass(psiField)).thenReturn(psiClass);
        when(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass)).thenReturn(false);
        when(createMissedRunWithAnnotationProblemDescriptorService.createProblemDescriptor(inspectionManager, psiClass)).thenReturn(problemDescriptor);
        final ProblemDescriptor[] problemDescriptors = mockitoAnnotationsWithNoRunWithMockitoTestRunnerLocalInspection.checkField(psiField, inspectionManager, true);
        assertNotNull(problemDescriptors);
        assertEquals(1, problemDescriptors.length);
        assertEquals(problemDescriptor, problemDescriptors[0]);
    }
}