import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.Utils;
import java.util.*;



public class TreePrinterListener implements ParseTreeListener {
    private final List<String> ruleNames;
    private final StringBuilder builder = new StringBuilder();

    public TreePrinterListener(Parser parser) {
        this.ruleNames = Arrays.asList(parser.getRuleNames());
    }

    public TreePrinterListener(List<String> ruleNames) {
        this.ruleNames = ruleNames;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (builder.length() > 0) {
            builder.append(' ');
        }

        System.out.println("TERMINAL:" + Trees.getNodeText(node, ruleNames));

        builder.append(Utils.escapeWhitespace(Trees.getNodeText(node, ruleNames), false));
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        if (builder.length() > 0) {
            builder.append(' ');
        }

        System.out.println("ERR:"+ Trees.getNodeText(node, ruleNames));

        builder.append(Utils.escapeWhitespace(Trees.getNodeText(node, ruleNames), false));
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if (builder.length() > 0) {
            builder.append(' ');
        }	
        
        System.out.println(ctx.getStart().getLine());
        System.out.println(ctx.getStart().getStartIndex());
        System.out.println(ctx.getStart().getStopIndex());

        if (ctx.getChildCount() > 0) {
            builder.append('(');
            //System.out.println("(");
        }

        int ruleIndex = ctx.getRuleIndex();
        String ruleName;
        if (ruleIndex >= 0 && ruleIndex < ruleNames.size()) {
            ruleName = ruleNames.get(ruleIndex);
        }
        else {
            ruleName = Integer.toString(ruleIndex);
        }
        System.out.println(ruleName);
        System.out.println("RULE:"+ ruleName);

        builder.append(ruleName);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if (ctx.getChildCount() > 0) {
            builder.append(')');
            //System.out.println(")");
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}