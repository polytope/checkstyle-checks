package dk.polytope.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ParameterAlignment extends AbstractCheck {
    private static final String SAME_LINE_NOT_COMPLIED = "same.line.not.complied";
    private static final String LAST_PARAM_RIGHT_PAREN = "last.param.right.paren";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
                TokenTypes.ELIST,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.ELIST && ast.getChildCount() > 0) {
            DetailAST parent = ast.getParent();
            DetailAST lparen = isParentMethodCall(ast) ? parent : parent.findFirstToken(TokenTypes.LPAREN);
            DetailAST rparen = getLastChild(ast.getParent());

            checkRule(lparen, ast, rparen);
        } else if (ast.branchContains(TokenTypes.PARAMETER_DEF)) {
            DetailAST lparen = ast.findFirstToken(TokenTypes.LPAREN);
            DetailAST rparen = getRightParenthesis(lparen);

            checkRule(lparen, ast.findFirstToken(TokenTypes.PARAMETERS), rparen);
        }
    }

    private boolean isParentMethodCall(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.METHOD_CALL;
    }

    private void checkRule(DetailAST lparen, DetailAST params, DetailAST rparen) {
        DetailAST lastParam = getLastChild(params);
        boolean sameLine = params.getLineNo() == lparen.getLineNo();

        if (sameLine && lparen.getLineNo() != rparen.getLineNo()) {
            log(params.getLineNo(), SAME_LINE_NOT_COMPLIED);
        } else if (!sameLine && lastParam.getLineNo() == rparen.getLineNo()) {
            log(params.getLineNo(), LAST_PARAM_RIGHT_PAREN);
        }
    }

    private DetailAST getLastChild(DetailAST node) {
        DetailAST lastChild = node.getFirstChild();

        while (lastChild != null && lastChild.getNextSibling() != null) {
            lastChild = lastChild.getNextSibling();
        }

        return lastChild;
    }

    private DetailAST getRightParenthesis(DetailAST lparen) {
        DetailAST rparen = lparen.getNextSibling();

        while (rparen.getType() != TokenTypes.RPAREN) {
            rparen = rparen.getNextSibling();
        }
        return rparen;
    }
}
