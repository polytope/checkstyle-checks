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
        if (ast.getType() == TokenTypes.ELIST) {
            if (ast.getChildCount() == 0 || ast.getParent().getType() == TokenTypes.FOR_ITERATOR) {
                return;
            }

            DetailAST parent = ast.getParent();
            DetailAST lparen = isParentMethodCall(ast) ? parent : parent.findFirstToken(TokenTypes.LPAREN);
            DetailAST rparen = ast.getParent().getLastChild();

            checkRule(lparen, ast, rparen);
        } else {
            DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);

            if (params != null && params.getChildCount() == 0) {
                return;
            }

            DetailAST lparen = ast.findFirstToken(TokenTypes.LPAREN);
            DetailAST rparen = getRightParenthesis(lparen);

            checkRule(lparen, ast.findFirstToken(TokenTypes.PARAMETERS), rparen);
        }
    }

    private boolean isParentMethodCall(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.METHOD_CALL;
    }

    private void checkRule(DetailAST leftParenthesis, DetailAST params, DetailAST rightParenthesis) {
        boolean leftParenthesisOnSameLineAsFirstParam = onSameLine(leftParenthesis, params.getFirstChild());
        boolean rightParenthesisShareLineWithLastParam = shareLines(rightParenthesis, params.getLastChild());

        if (!onSameLine(leftParenthesis, rightParenthesis)) {
            if (leftParenthesisOnSameLineAsFirstParam && rightParenthesisShareLineWithLastParam) {
                log(params.getLineNo(), SAME_LINE_NOT_COMPLIED);
            } else if (leftParenthesisOnSameLineAsFirstParam ^ rightParenthesisShareLineWithLastParam) {
                log(params.getLineNo(), LAST_PARAM_RIGHT_PAREN);
            }
        }
    }

    private boolean onSameLine(DetailAST ast1, DetailAST ast2) {
        return ast1.getLineNo() == ast2.getLineNo();
    }

    private boolean shareLines(DetailAST ast1, DetailAST ast2) {
        return ast1.getLineNo() <= findLastLine(ast2) && findLastLine(ast1) >= ast2.getLineNo();
    }

    private DetailAST getRightParenthesis(DetailAST lparen) {
        DetailAST rparen = lparen.getNextSibling();

        while (rparen.getType() != TokenTypes.RPAREN) {
            rparen = rparen.getNextSibling();
        }

        return rparen;
    }

    private int findLastLine(final DetailAST astNode) {
        final int lastLine;
        if (astNode.getChildCount() == 0) {
            lastLine = astNode.getLineNo();
        } else {
            lastLine = findLastLine(astNode.getLastChild());
        }
        return lastLine;
    }
}
