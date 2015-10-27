package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiNameValuePair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PsiAnnotationParameterNameMatchServiceTest {
    private PsiAnnotationParameterNameMatchService psiAnnotationParameterNameMatchService;

    @Before
    public void setUp() throws Exception {
        psiAnnotationParameterNameMatchService = new PsiAnnotationParameterNameMatchServiceImpl();
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(psiAnnotationParameterNameMatchService instanceof ApplicationComponent);
    }

    @Test
    public void testValueValue() throws Exception {
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        when(psiNameValuePair.getName()).thenReturn("value");
        assertTrue(psiAnnotationParameterNameMatchService.isMatched("value", psiNameValuePair));
    }

    @Test
    public void testValueNull() throws Exception {
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        when(psiNameValuePair.getName()).thenReturn(null);
        assertTrue(psiAnnotationParameterNameMatchService.isMatched("value", psiNameValuePair));
    }

    @Test
    public void testValueNotValue() throws Exception {
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        when(psiNameValuePair.getName()).thenReturn("notValue");
        assertFalse(psiAnnotationParameterNameMatchService.isMatched("value", psiNameValuePair));
    }

    @Test
    public void testNotValueNotValue() throws Exception {
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        when(psiNameValuePair.getName()).thenReturn("x");
        assertTrue(psiAnnotationParameterNameMatchService.isMatched("x", psiNameValuePair));
    }

    @Test
    public void testNotValueNull() throws Exception {
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        when(psiNameValuePair.getName()).thenReturn(null);
        assertFalse(psiAnnotationParameterNameMatchService.isMatched("x", psiNameValuePair));
    }

    @Test
    public void testNotValueNotValue2() throws Exception {
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        when(psiNameValuePair.getName()).thenReturn("y");
        assertFalse(psiAnnotationParameterNameMatchService.isMatched("x", psiNameValuePair));
    }
}