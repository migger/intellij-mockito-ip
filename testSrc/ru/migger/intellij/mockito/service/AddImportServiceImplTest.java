package ru.migger.intellij.mockito.service;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PsiElementFactory.SERVICE.class, ApplicationManager.class})
public class AddImportServiceImplTest {
    private AddImportService addImportService;
    private Project project;
    private PsiElementFactory psiElementFactory;
    private Application application;

    public RunnableHolder getWriteActionHolder() {
        final RunnableHolder runnableHolder = new RunnableHolder();
        doAnswer(invocation -> {
            runnableHolder.setRunnable((Runnable) invocation.getArguments()[0]);
            return null;
        }).when(application).runWriteAction(any(Runnable.class));
        return runnableHolder;
    }

    @Before
    public void setUp() throws Exception {
        project = mock(Project.class);
        psiElementFactory = mock(PsiElementFactory.class);
        application = mock(Application.class);
        mockStatic(PsiElementFactory.SERVICE.class, ApplicationManager.class);
        when(PsiElementFactory.SERVICE.getInstance(project)).thenReturn(psiElementFactory);
        when(ApplicationManager.getApplication()).thenReturn(application);

        addImportService = new AddImportServiceImpl();
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(addImportService instanceof ApplicationComponent);
    }

    @Test
    public void testImportListPresent() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);

        final PsiImportStatement psiImportStatement = mock(PsiImportStatement.class);
        final PsiImportList psiImportList = mock(PsiImportList.class);
        final RunnableHolder writeActionHolder = getWriteActionHolder();

        when(psiJavaFile.getProject()).thenReturn(project);
        when(psiJavaFile.getImportList()).thenReturn(psiImportList);
        when(psiElementFactory.createImportStatement(toImport)).thenReturn(psiImportStatement);
        addImportService.addImportStatement(psiJavaFile, toImport);
        assertNotNull(writeActionHolder.getRunnable());
        verify(psiImportList, never()).add(any());
        writeActionHolder.getRunnable().run();
        verify(psiImportList).add(psiImportStatement);
    }

    @Test
    public void testJavaFileIsEmpty() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);

        final PsiImportStatement psiImportStatement = mock(PsiImportStatement.class);
        final PsiImportList psiImportList = mock(PsiImportList.class);
        final RunnableHolder writeActionHolder = getWriteActionHolder();

        when(psiJavaFile.getProject()).thenReturn(project);
        when(psiImportStatement.getParent()).thenReturn(psiImportList);
        when(psiElementFactory.createImportStatement(toImport)).thenReturn(psiImportStatement);
        addImportService.addImportStatement(psiJavaFile, toImport);
        assertNotNull(writeActionHolder.getRunnable());
        verify(psiJavaFile, never()).add(any());
        writeActionHolder.getRunnable().run();
        verify(psiJavaFile).add(psiImportList);
    }

    @Test
    public void testFirstChildIsPackage() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);

        final PsiImportStatement psiImportStatement = mock(PsiImportStatement.class);
        final PsiImportList psiImportList = mock(PsiImportList.class);
        final RunnableHolder writeActionHolder = getWriteActionHolder();
        final PsiPackageStatement psiPackageStatement = mock(PsiPackageStatement.class);

        when(psiJavaFile.getProject()).thenReturn(project);
        when(psiImportStatement.getParent()).thenReturn(psiImportList);
        when(psiJavaFile.getPackageStatement()).thenReturn(psiPackageStatement);
        when(psiJavaFile.getFirstChild()).thenReturn(psiPackageStatement);
        when(psiElementFactory.createImportStatement(toImport)).thenReturn(psiImportStatement);
        addImportService.addImportStatement(psiJavaFile, toImport);
        assertNotNull(writeActionHolder.getRunnable());
        verify(psiJavaFile, never()).addAfter(any(), any());
        writeActionHolder.getRunnable().run();
        verify(psiJavaFile).addAfter(psiPackageStatement, psiImportList);
    }

    @Test
    public void testFirstChildIsNotPackage() throws Exception {
        final PsiJavaFile psiJavaFile = mock(PsiJavaFile.class);
        final PsiClass toImport = mock(PsiClass.class);

        final PsiImportStatement psiImportStatement = mock(PsiImportStatement.class);
        final PsiImportList psiImportList = mock(PsiImportList.class);
        final RunnableHolder writeActionHolder = getWriteActionHolder();
        final PsiElement firstChild = mock(PsiElement.class);

        when(psiJavaFile.getProject()).thenReturn(project);
        when(psiImportStatement.getParent()).thenReturn(psiImportList);
        when(psiJavaFile.getFirstChild()).thenReturn(firstChild);

        when(psiElementFactory.createImportStatement(toImport)).thenReturn(psiImportStatement);
        addImportService.addImportStatement(psiJavaFile, toImport);
        assertNotNull(writeActionHolder.getRunnable());
        verify(psiJavaFile, never()).addBefore(any(), any());
        writeActionHolder.getRunnable().run();
        verify(psiJavaFile).addBefore(firstChild, psiImportList);
    }
}


class RunnableHolder {
    private Runnable runnable;

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
