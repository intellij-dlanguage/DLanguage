package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageEqualExpression extends PsiElement {
    @NotNull
    List<DLanguageShiftExpression> getShiftExpressions();

    @Nullable
    PsiElement getOP_EQ_EQ();

    @Nullable
    PsiElement getOP_NOT_EQ();

}
