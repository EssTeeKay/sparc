/* Generated By:JJTree: Do not edit this line. QASTterm.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=QAST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package querying.parsing.query;

public
class QASTterm extends SimpleNode {
  public QASTterm(int id) {
    super(id);
  }
  
  public QASTterm() {
	  this(QueryParserTreeConstants.JJTTERM);
  }
  
  public QASTterm(QueryParser p, int id) {
    super(p, id);
  }
  
  public String toString()
  {
	  return ((SimpleNode)this.jjtGetChild(0)).toString();
  }
  
  public static QASTterm createTermFromVariable(String variableName) {
	  QASTterm term=new QASTterm();
	  QASTvar var= new QASTvar();
	  term.jjtAddChild(var, 0);
	  return term;
  }

}
/* JavaCC - OriginalChecksum=7f60b93e52ae5079407838efb9dd14dd (do not edit this line) */
