package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetPsiAnnotationDotClassParameterServiceImplTest {
    private GetPsiAnnotationDotClassParameterService getPsiAnnotationDotClassParameterService;
    private PsiAnnotationParameterNameMatchService psiAnnotationParameterNameMatchService;

    @Before
    public void setUp() throws Exception {
        psiAnnotationParameterNameMatchService = mock(PsiAnnotationParameterNameMatchService.class);
        getPsiAnnotationDotClassParameterService = new GetPsiAnnotationDotClassParameterServiceImpl(psiAnnotationParameterNameMatchService);
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(getPsiAnnotationDotClassParameterService instanceof ApplicationComponent);
    }

    @Test
    public void testNoParameters() throws Exception {
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        final PsiAnnotationParameterList psiAnnotationParameterList = mock(PsiAnnotationParameterList.class);
        when(psiAnnotation.getParameterList()).thenReturn(psiAnnotationParameterList);
        when(psiAnnotationParameterList.getAttributes()).thenReturn(new PsiNameValuePair[0]);
        assertNull(getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, "value"));
    }

    @Test
    public void testNoValueParameter() throws Exception {
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        final PsiAnnotationParameterList psiAnnotationParameterList = mock(PsiAnnotationParameterList.class);
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        final PsiAnnotationMemberValue psiAnnotationMemberValue = mock(PsiAnnotationMemberValue.class);
        when(psiAnnotation.getParameterList()).thenReturn(psiAnnotationParameterList);
        when(psiAnnotationParameterNameMatchService.isMatched("value", psiNameValuePair)).thenReturn(false);
        when(psiNameValuePair.getValue()).thenReturn(psiAnnotationMemberValue);
        when(psiAnnotationParameterList.getAttributes()).thenReturn(new PsiNameValuePair[] {
                psiNameValuePair
        });
        assertNull(getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, "value"));
    }
    @Test
    public void testValueParameter() throws Exception {
        final PsiAnnotation psiAnnotation = mock(PsiAnnotation.class);
        final PsiAnnotationParameterList psiAnnotationParameterList = mock(PsiAnnotationParameterList.class);
        final PsiNameValuePair psiNameValuePair = mock(PsiNameValuePair.class);
        final PsiClassObjectAccessExpression psiAnnotationMemberValue = mock(PsiClassObjectAccessExpression.class);
        final PsiTypeElement psiTypeElement = mock(PsiTypeElement.class);
        final PsiType psiType = mock(PsiType.class);
        when(psiAnnotation.getParameterList()).thenReturn(psiAnnotationParameterList);
        when(psiAnnotationParameterNameMatchService.isMatched("value", psiNameValuePair)).thenReturn(true);
        when(psiNameValuePair.getValue()).thenReturn(psiAnnotationMemberValue);
        when(psiType.getCanonicalText()).thenReturn("org.mockito.runners.MockitoJUnitRunner");
        when(psiTypeElement.getType()).thenReturn(psiType);
        when(psiAnnotationMemberValue.getOperand()).thenReturn(psiTypeElement);
        when(psiAnnotationParameterList.getAttributes()).thenReturn(new PsiNameValuePair[] {
                psiNameValuePair
        });
        assertEquals("org.mockito.runners.MockitoJUnitRunner", getPsiAnnotationDotClassParameterService.getQualifiedClassNameOfPsiAnnotationParameter(psiAnnotation, "value"));
    }
}