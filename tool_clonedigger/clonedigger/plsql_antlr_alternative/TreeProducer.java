/*  Copyright 2008 Peter Bulychev
 *
 *  This file is part of Clone Digger.
 *
 *  Clone Digger is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Clone Digger is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Clone Digger.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.antlr.v4.runtime.*;
//import org.antlr.v4.stringtemplate.*;
import org.antlr.v4.runtime.tree.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.util.List;
import java.util.Arrays;

public class TreeProducer
{
    public TreeProducer ()
    {
	super ();
    }

    /*
     * forXml function was taken from http://www.javapractices.com/topic/TopicAction.do?Id=96
     * the license is: http://creativecommons.org/licenses/by/3.0/
     */
    public static String forXML (String aText)
    {
	final StringBuilder result = new StringBuilder ();
	final StringCharacterIterator iterator =
	    new StringCharacterIterator (aText);
	char character = iterator.current ();
	while (character != CharacterIterator.DONE)
	{
	    if (character == '<')
	    {
		result.append ("&lt;");
	    }
	    else if (character == '>')
	    {
		result.append ("&gt;");
	    }
	    else if (character == '\"')
	    {
		result.append ("&quot;");
	    }
	    else if (character == '\'')
	    {
		result.append ("&#039;");
	    }
	    else if (character == '&')
	    {
		result.append ("&amp;");
	    }
	    else
	    {
		//the char is not a special one
		//        //add it to the result as is
		result.append (character);
	    }
	    character = iterator.next ();
	}
	return result.toString ();
    }
    //                                      }

    public static void printTree (ParseTree tree, PrintWriter outputStream,	String indent, PLSQLParser parser)
{
	Object payload = tree.getPayload();
	Class<? extends Object> pay = payload.getClass();
	int line = 0;
	int start = 0;
	int stop = 0;
	if(payload instanceof Token)
    {
      line = ((Token) payload).getLine();
      start = ((Token) payload).getStartIndex();
      stop = ((Token) payload).getStopIndex();
    }

    String xml_node_name = "node"; //(tree.is_statement?"statement_node":"node");
    outputStream.println (indent + "<" + xml_node_name + " name=\"" + forXML ("" + Trees.getNodeText(tree, parser)) + "\"" + 
	    " line_number=\"" + line + " \" " +
	    "start=\"" +  start + "\" " + 
	    "stop=\"" + stop + "\">");
    for (int i = 0; i < tree.getChildCount(); i += 1)
    {
	printTree ((ParseTree )tree.getChild (i), outputStream, indent + "  ", parser);
    }
    outputStream.println (indent + "</"+xml_node_name+">");
}

public static void main (String[]args) throws Exception
{
	System.out.println("TREE PRODUCER");
    ANTLRFileStream input = new ANTLRFileStream (args[0]);
    PLSQLLexer lexer = new PLSQLLexer (input);
    CommonTokenStream tokens = new CommonTokenStream (lexer);
    PLSQLParser parser = new PLSQLParser (tokens);
    ParseTree tree = parser.file(); // parse

    //Array<String> ruleNames = Arrays.asList("SELECT", "sup2", "sup3");

    TreePrinterListener listener = new TreePrinterListener(parser);
	ParseTreeWalker.DEFAULT.walk(listener, tree);
	String formatted = listener.toString();
	System.out.println(formatted);

    //System.out.println(tree.toStringTree());

 //    Tree tree = (MyAstNode) parser.compilationUnit().getTree ();
    PrintWriter outputStream = new PrintWriter (new FileWriter (args[1], false));
    outputStream.println ("<?xml version=\"1.0\" ?>");
    printTree (tree, outputStream, "", parser);
    outputStream.close ();
}
}
