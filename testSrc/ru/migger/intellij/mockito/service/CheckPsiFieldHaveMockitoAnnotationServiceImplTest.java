package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifierList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckPsiFieldHaveMockitoAnnotationServiceImplTest {
    private CheckPsiFieldHaveMockitoAnnotationService checkPsiFieldHaveMockitoAnnotationService;

    @Before
    public void setUp() throws Exception {
        checkPsiFieldHaveMockitoAnnotationService = new CheckPsiFieldHaveMockitoAnnotationServiceImpl();
    }
    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(checkPsiFieldHaveMockitoAnnotationService instanceof ApplicationComponent);
    }


    @Test
    public void testGetModifierListReturnsNull() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        assertFalse(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField));
    }

    @Test
    public void testNoAnnotations() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiField.getModifierList()).thenReturn(psiModifierList);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[0]);
        assertFalse(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField));
    }

    @Test
    public void testOtherAnnotation() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiField.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(psiAnnotation.getQualifiedName()).thenReturn("org.jetbrains.annotations.NotNull");
        assertFalse(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField));
    }

    @Test
    public void testMockAnnotation() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiField.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(psiAnnotation.getQualifiedName()).thenReturn("org.mockito.Mock");
        assertTrue(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField));
    }
    @Test
    public void testInjectMocksAnnotation() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final PsiModifierList psiModifierList = mock(PsiModifierList.class);
        when(psiField.getModifierList()).thenReturn(psiModifierList);
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        when(psiModifierList.getAnnotations()).thenReturn(new PsiAnnotation[] {
                psiAnnotation
        });
        when(psiAnnotation.getQualifiedName()).thenReturn("org.mockito.InjectMocks");
        assertTrue(checkPsiFieldHaveMockitoAnnotationService.isPsiFiledHaveMockitoAnnotation(psiField));
    }
}
