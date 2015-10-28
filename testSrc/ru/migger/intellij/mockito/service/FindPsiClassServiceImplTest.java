package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GlobalSearchScope.class, JavaPsiFacade.class})
public class FindPsiClassServiceImplTest {
    private FindPsiClassService findPsiClassService;

    @Before
    public void setUp() throws Exception {
        findPsiClassService = new FindPsiClassServiceImpl();
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(findPsiClassService instanceof ApplicationComponent);
    }

    @Test
    public void testFindClass() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final Project project = mock(Project.class);
        final String className = "java.lang.String";
        final GlobalSearchScope globalSearchScope = mock(GlobalSearchScope.class);
        final JavaPsiFacade javaPsiFacade = mock(JavaPsiFacade.class);
        PowerMockito.mockStatic(GlobalSearchScope.class);
        PowerMockito.mockStatic(JavaPsiFacade.class);
        when(GlobalSearchScope.allScope(project)).thenReturn(globalSearchScope);
        when(JavaPsiFacade.getInstance(project)).thenReturn(javaPsiFacade);
        when(javaPsiFacade.findClass(className, globalSearchScope)).thenReturn(psiClass);
        assertEquals(psiClass, findPsiClassService.findClassByFullName(project, className));
    }
}