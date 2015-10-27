package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetPsiFieldParentClassServiceImplTest {
    private GetPsiFieldParentClassService getPsiFieldParentClassService;

    @Before
    public void setUp() throws Exception {
        getPsiFieldParentClassService = new GetPsiFieldParentClassServiceImpl();
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(getPsiFieldParentClassService instanceof ApplicationComponent);
    }


    @Test
    public void testParentIsNotClass() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        when(psiField.getParent()).thenReturn(mock(PsiElement.class));
        assertNull(getPsiFieldParentClassService.getParentClass(psiField));
    }
    @Test
    public void testParentIsClass() throws Exception {
        final PsiField psiField = mock(PsiField.class);
        final PsiClass psiClass = mock(PsiClass.class);
        when(psiField.getParent()).thenReturn(psiClass);
        assertEquals(psiClass, getPsiFieldParentClassService.getParentClass(psiField));
    }
}