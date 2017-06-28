import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.Utils;
import java.util.*;



public class TreePrinterListener implements ParseTreeListener {
    private final List<String> ruleNames;
    private final StringBuilder builder = new StringBuilder();

    public String xmlEscapeText(String t) {
       StringBuilder sb = new StringBuilder();
       for(int i = 0; i < t.length(); i++){
          char c = t.charAt(i);
          switch(c){
          case '<': sb.append("&lt;"); break;
          case '>': sb.append("&gt;"); break;
          case '\"': sb.append("&quot;"); break;
          case '&': sb.append("&amp;"); break;
          case '\'': sb.append("&apos;"); break;
          default:
             if(c>0x7e) {
                sb.append("&#"+((int)c)+";");
             }else
                sb.append(c);
          }
       }
       return sb.toString();
    }

    public TreePrinterListener(Parser parser) {
        this.ruleNames = Arrays.asList(parser.getRuleNames());
    }

    public TreePrinterListener(List<String> ruleNames) {
        this.ruleNames = ruleNames;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // if (builder.length() > 0) {
        //     builder.append(' ');
        // }

        Object payload = node.getPayload();
        int line_number = 0;
        int start = 0;
        int stop = 0;
        if(payload instanceof Token)
        {
          line_number = ((Token) payload).getLine();
          start = ((Token) payload).getStartIndex();
          stop = ((Token) payload).getStopIndex();
        }
        //System.out.println("<node name=\""+Trees.getNodeText(node, ruleNames)+"\" terminal=\"true\" line_number=\""+line_number+"\" start=\""+start+"\" stop=\""+stop+"\"></node>");
        //Trees.getNodeText(node, ruleNames)

        //builder.append(Utils.escapeWhitespace(Trees.getNodeText(node, ruleNames), false));
        builder.append("<node name=\""+xmlEscapeText(Trees.getNodeText(node, ruleNames))+"\" terminal=\"true\" line_number=\""+line_number+"\" start=\""+start+"\" stop=\""+stop+"\"></node>\n");
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        // if (builder.length() > 0) {
        //     builder.append(' ');
        // }

        System.out.println("ERR:"+ Trees.getNodeText(node, ruleNames));

        //builder.append(Utils.escapeWhitespace(Trees.getNodeText(node, ruleNames), false));
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        // if (builder.length() > 0) {
        //     builder.append(' ');
        // }	
        
        

        // if (ctx.getChildCount() > 0) {
        //     builder.append('(');
        //     //System.out.println("(");
        // }

        int ruleIndex = ctx.getRuleIndex();
        String ruleName;
        if (ruleIndex >= 0 && ruleIndex < ruleNames.size()) {
            ruleName = ruleNames.get(ruleIndex);
        }
        else {
            ruleName = Integer.toString(ruleIndex);
        }

        int line_number = ctx.getStart().getLine();
        int start = ctx.getStart().getStartIndex();
        int stop = ctx.getStop().getStopIndex();

        //System.out.println("<node name=\""+ruleName+"\" line_number=\""+line_number+"\" start=\""+start+"\" stop=\""+stop+"\">");

        builder.append("<node name=\""+ruleName+"\" line_number=\""+line_number+"\" start=\""+start+"\" stop=\""+stop+"\">\n");
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if (ctx.getChildCount() > 0) {
            //builder.append(')');
            builder.append("</node>\n");
            //System.out.println("</node>");
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}