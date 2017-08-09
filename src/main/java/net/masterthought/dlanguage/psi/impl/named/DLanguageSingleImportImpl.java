package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.resolve.processors.parameters.DAttributesFinder;
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.fest.util.Sets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_EQ;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageSingleImportImpl extends DNamedStubbedPsiElementBase<DLanguageSingleImportStub> implements DLanguageSingleImport {

    public DLanguageSingleImportImpl(@NotNull final DLanguageSingleImportStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageSingleImportImpl(final ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Nullable
    @Override
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @NotNull
    @Override
    public Set<DLanguageImportBind> getApplicableImportBinds() {
        if (((DLanguageImportDeclaration) getParent()).getImportBindings() != null) {
            return Sets.newHashSet(((DLanguageImportDeclaration) getParent()).getImportBindings().getImportBinds());
        }
        return new HashSet<>();
    }

    @NotNull
    @Override
    public String getName() {
        if (getStub() != null) {
            if(getStub().getName() == null){
                throw new NullPointerException();
            }
            return getStub().getName();
        }
        if (getIdentifierChain() == null) {
            return DReference.Companion.getNAME_NOT_FOUND_STRING();
        }
        return getIdentifierChain().getText();
    }

    @Override
    public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
        return null;
    }

    @Nullable
    @Override
    public DLanguageIdentifier getNameIdentifier() {
        return DUtil.getEndOfIdentifierList(getIdentifierChain());
    }

    public boolean isPublic() {
        if (getStub() != null)
            return getStub().isPublic();
        final DAttributesFinder finder = new DAttributesFinder(getIdentifierChain().getIdentifiers().get(0));
        finder.recurseUp();
        return finder.isPublic();
    }
}
