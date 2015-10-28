package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaFile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class EnsureClassesIsImportedServiceImplTest {
    private EnsureClassesIsImportedService ensureClassesIsImportedService;
    private AddImportService addImportService;

    @Before
    public void setUp() throws Exception {
        addImportService = mock(AddImportService.class);
        ensureClassesIsImportedService = new EnsureClassesIsImportedServiceImpl(addImportService);
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(ensureClassesIsImportedService instanceof ApplicationComponent);
    }

    @Test
    public void testNoImportList() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);
        ensureClassesIsImportedService.ensureIsImported(psiJavaFile, toImport);
        verify(addImportService).addImportStatement(psiJavaFile, toImport);
    }

    @Test
    public void testImportListHaveNoClassImport() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);
        final PsiImportList psiImportList = mock(PsiImportList.class);
        final PsiImportStatement importStatement = mock(PsiImportStatement.class);
        when(psiJavaFile.getImportList()).thenReturn(psiImportList);
        when(importStatement.getQualifiedName()).thenReturn("QualifiedName");
        when(toImport.getQualifiedName()).thenReturn("AnotherQualifiedName");
        when(psiImportList.getImportStatements()).thenReturn(new PsiImportStatement[]{
                importStatement
        });
        ensureClassesIsImportedService.ensureIsImported(psiJavaFile, toImport);
        verify(addImportService).addImportStatement(psiJavaFile, toImport);
    }


    @Test
    public void testAlreadyImported() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);
        final PsiImportList psiImportList = mock(PsiImportList.class);
        final PsiImportStatement importStatement = mock(PsiImportStatement.class);
        when(psiJavaFile.getImportList()).thenReturn(psiImportList);
        when(importStatement.getQualifiedName()).thenReturn("QualifiedName");
        when(toImport.getQualifiedName()).thenReturn("QualifiedName");
        when(psiImportList.getImportStatements()).thenReturn(new PsiImportStatement[]{
                mock(PsiImportStatement.class),
                importStatement
        });
        ensureClassesIsImportedService.ensureIsImported(psiJavaFile, toImport);
        verify(addImportService, never()).addImportStatement(any(), any());
    }
}
