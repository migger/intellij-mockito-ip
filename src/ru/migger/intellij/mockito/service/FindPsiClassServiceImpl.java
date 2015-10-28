package ru.migger.intellij.mockito.service;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FindPsiClassServiceImpl extends ApplicationComponent.Adapter implements FindPsiClassService {
    @Nullable
    @Override
    public PsiClass findClassByFullName(@NotNull Project project, @NotNull String fullName) {
        final GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        return JavaPsiFacade.getInstance(project).findClass(fullName, scope);
    }
}
