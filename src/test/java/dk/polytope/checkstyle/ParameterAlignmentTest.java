package dk.polytope.checkstyle;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import dk.polytope.checkstyle.ut.AbstractModuleTestSupport;
import org.junit.jupiter.api.Test;

import static dk.polytope.checkstyle.ParameterAlignment.LAST_PARAM_RIGHT_PAREN;
import static dk.polytope.checkstyle.ParameterAlignment.SAME_LINE_NOT_COMPLIED;

class ParameterAlignmentTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "dk/polytope/checkstyle";
    }

    @Test
    void failWhen_ParameterOnSameLineAsLeftParenthesis() throws Exception {
        DefaultConfiguration checkConfig = createModuleConfig(ParameterAlignment.class);
        String[] expected = {"5: " + getCheckMessage(LAST_PARAM_RIGHT_PAREN)};
        verify(checkConfig, getPath("ParameterOnSameLineAsLeftParenthesis.java"), expected);
    }

    @Test
    void failWhen_ParameterOnSameLineAsRightParenthesis() throws Exception {
        DefaultConfiguration checkConfig = createModuleConfig(ParameterAlignment.class);
        String[] expected = {"6: " + getCheckMessage(LAST_PARAM_RIGHT_PAREN)};
        verify(checkConfig, getPath("ParameterOnSameLineAsRightParenthesis.java"), expected);
    }

    @Test
    void failWhen_ParameterOnSameLineAsLeftAndRightParenthesis() throws Exception {
        DefaultConfiguration checkConfig = createModuleConfig(ParameterAlignment.class);
        String[] expected = {"5: " + getCheckMessage(SAME_LINE_NOT_COMPLIED)};
        verify(checkConfig, getPath("ParametersOnSameLineAsLeftAndRightParenthesis.java"), expected);
    }

    @Test
    void failWhen_LastParameterIsLambdaAndOnSameLineAsRightParenthesis() throws Exception {
        DefaultConfiguration checkConfig = createModuleConfig(ParameterAlignment.class);
        String[] expected = {"8: " + getCheckMessage(LAST_PARAM_RIGHT_PAREN)};
        verify(checkConfig, getPath("LastParameterIsLambdaAndOnSameLineAsRightParenthesis.java"), expected);
    }

    @Test
    void failWhen_FirstParamOnSameLineAsLeftParenthesisAndLastParameterIsLambda() throws Exception {
        DefaultConfiguration checkConfig = createModuleConfig(ParameterAlignment.class);
        String[] expected = {"7: " + getCheckMessage(LAST_PARAM_RIGHT_PAREN)};
        verify(checkConfig, getPath("FirstParamOnSameLineAsLeftParenthesisAndLastParameterIsLambda.java"), expected);
    }

    @Test
    void succeedWhen_OnlyParamSpansMultipleLinesOnSameLineAsLeftAndRightParenthesis() throws Exception {
        DefaultConfiguration checkConfig = createModuleConfig(ParameterAlignment.class);
        String[] expected = {};
        verify(checkConfig, getPath("OnlyParamSpansMultipleLinesOnSameLineAsLeftAndRightParenthesis.java"), expected);
    }
}
