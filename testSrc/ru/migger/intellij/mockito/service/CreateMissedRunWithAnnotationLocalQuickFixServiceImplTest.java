package ru.migger.intellij.mockito.service;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.PsiClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.migger.intellij.mockito.quickfix.MissedRunWithAnnotationLocalQuickFix;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MissedRunWithAnnotationLocalQuickFix.class)
public class CreateMissedRunWithAnnotationLocalQuickFixServiceImplTest {
    private CreateMissedRunWithAnnotationLocalQuickFixService createMissedRunWithAnnotationLocalQuickFixService;
    private EnsureClassesIsImportedService ensureClassesIsImportedService;
    private FindPsiClassService findPsiClassService;

    @Before
    public void setUp() throws Exception {
        ensureClassesIsImportedService = mock(EnsureClassesIsImportedService.class);
        findPsiClassService = mock(FindPsiClassService.class);
        createMissedRunWithAnnotationLocalQuickFixService = new CreateMissedRunWithAnnotationLocalQuickFixServiceImpl(
                ensureClassesIsImportedService, findPsiClassService
        );
    }

    @Test
    public void testIsApplicationComponent() throws Exception {
        assertTrue(createMissedRunWithAnnotationLocalQuickFixService instanceof ApplicationComponent);
    }

    @Test
    public void testCreated() throws Exception {
        final PsiClass psiClass = mock(PsiClass.class);
        final MissedRunWithAnnotationLocalQuickFix localQuickFix = mock(MissedRunWithAnnotationLocalQuickFix.class);
        mockStatic(MissedRunWithAnnotationLocalQuickFix.class);
        when(MissedRunWithAnnotationLocalQuickFix.create(findPsiClassService, ensureClassesIsImportedService, psiClass)).thenReturn(localQuickFix);
        final LocalQuickFix[] localQuickFixes = createMissedRunWithAnnotationLocalQuickFixService.createLocalQuickFixes(psiClass);
        assertArrayEquals(new LocalQuickFix[]{localQuickFix}, localQuickFixes);
    }
}