package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifierList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckPsiClassHaveProperRunWithAnnotatiosServiceImplTest {
    private CheckPsiClassHaveProperRunWithAnnotatiosService checkPsiClassHaveProperRunWithAnnotatiosService;
    private GetPsiAnnotationDotClassParameterService getPsiAnnotationDotClassParameterService;

    @Before
    public void setUp() throws Exception {
        getPsiAnnotationDotClassParameterService = mock(GetPsiAnnotationDotClassParameterService.class);
        checkPsiClassHaveProperRunWithAnnotatiosService = new CheckPsiClassHaveProperRunWithAnnotatiosServiceImpl(
                getPsiAnnotationDotClassParameterService
        );
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(checkPsiClassHaveProperRunWithAnnotatiosService instanceof ApplicationComponent);
    }

    @Test
    public void testGetModifierListReturnsNull() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        assertFalse(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass));
    }
    @Test
    public void testNoAnnotations() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiClass.getModifierList()).thenReturn(psiModifierList);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[0]);
        assertFalse(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass));
    }

    @Test
    public void testOtherAnnotation() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiClass.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(psiAnnotation.getQualifiedName()).thenReturn("org.jetbrains.annotations.NonNls");
        assertFalse(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass));
    }

    @Test
    public void testRunWithAnnotationNotRunner() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiClass.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, "value")).thenReturn("junit.textui.TestRunner");
        assertFalse(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass));
    }
    @Test
    public void testRunWithAnnotationNotValidRunner() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiClass.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(psiAnnotation.getQualifiedName()).thenReturn("org.junit.runner.RunWith");
        when(getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, "value")).thenReturn("junit.textui.TestRunner");
        assertFalse(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass));
    }

    @Test
    public void testRunWithAnnotationValidRunner() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiClass.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(psiAnnotation.getQualifiedName()).thenReturn("org.junit.runner.RunWith");
        when(getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, "value")).thenReturn("org.mockito.runners.MockitoJUnitRunner");
        assertTrue(checkPsiClassHaveProperRunWithAnnotatiosService.isPsiClassHaveProperAnnotation(psiClass));
    }

}