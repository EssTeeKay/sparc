/* Generated By:JJTree: Do not edit this line. QASTatom.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=QAST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package querying.parsing.query;

import java.util.ArrayList;
import java.util.HashSet;

import warnings.Pair;
import warnings.StringListUtils;

public class QASTatom extends SimpleNode {
	AtomType type;

	public QASTatom(int id) {
		super(id);
		type = AtomType.nonRelational;
	}

	public QASTatom(QueryParser p, int id) {
		super(p, id);
		type = AtomType.nonRelational;
	}

	public QASTatom(QASTterm term1, QASTterm term2) {
		super(QueryParserTreeConstants.JJTATOM);
		type = AtomType.relational;
		this.jjtAddChild(term1, 0);
		this.jjtAddChild(term2, 1);
	}

	public String getName() {
		QASTpredSymbol predS = (QASTpredSymbol) this.jjtGetChild(0);
		return predS.image;
	}

	public void evaluateAllArithmetics() {
		evaluateAllArithmetics(this);
	}

	private void evaluateAllArithmetics(SimpleNode n) {
		if (n.id == QueryParserTreeConstants.JJTTERM) {
			SimpleNode child = (SimpleNode) n.jjtGetChild(0);
			if (child.id == QueryParserTreeConstants.JJTARITHMETICTERM) {
				TermEvaluator tv = new TermEvaluator((QASTarithmeticTerm) n);
				if (tv.isEvaluable()) {
					long value = tv.evaluate();
					QASTarithmeticTerm aterm = new QASTarithmeticTerm(
							QueryParserTreeConstants.JJTARITHMETICTERM, value);
					n.children[0] = aterm;
				}
			}

		}
	}

	public ArrayList<String> getArguments() {
		ArrayList<String> arguments = new ArrayList<String>();
		QASTtermList termList = (QASTtermList) this.jjtGetChild(1);
		for (int i = 0; i < termList.jjtGetNumChildren(); i++) {
			QASTterm term = (QASTterm) termList.jjtGetChild(i);
			arguments.add(term.toString());
		}
		return arguments;
	}

	public String toString() {
		if (type == AtomType.nonRelational)
			return getName() + "("
					+ StringListUtils.getSeparatedList(getArguments(), ",")
					+ ")";
		else
			return ((SimpleNode) this.jjtGetChild(0)).toString() + "="
					+ ((SimpleNode) this.jjtGetChild(1)).toString();
	}

	public boolean isGround() {
		evaluateAllArithmetics();
		return isGround(this);
	}

	private boolean isGround(SimpleNode n) {
		if (n.id == QueryParserTreeConstants.JJTVAR) {
			return true;
		}

		boolean ground = true;
		for (int i = 0; i < n.jjtGetNumChildren(); i++) {
			if (isGround((SimpleNode) n.jjtGetChild(i))) {
				ground = false;
				break;
			}
		}
		return ground;

	}

	final private static String newVarPrefix = "VAR_";
	private static int newVarId = 0;

	private String getUniqueVariable(HashSet<String> usedVariables) {
		while (usedVariables.contains(newVarPrefix + newVarId)) {
			++newVarId;
		}
		usedVariables.add(newVarPrefix + newVarId);
		return newVarPrefix + newVarId;
	}

	public Pair<QASTatom, ArrayList<QASTatom>> moveOutArithmetics() {
		HashSet<String> usedVariables = this.fetchVariables();
		ArrayList<QASTatom> movedOutAtoms = new ArrayList<QASTatom>();
		QASTtermList termList = (QASTtermList) this.jjtGetChild(1);

		for (int i = 0; i < termList.jjtGetNumChildren(); i++) {
			QASTterm term = (QASTterm) termList.jjtGetChild(i);
			String termStr = term.toString();
			if (termStr.indexOf('/') != -1 || termStr.indexOf('+') != -1
					|| termStr.indexOf('*') != -1 || termStr.indexOf('-') != -1) {
				String varName = getUniqueVariable(usedVariables);
				QASTterm var = QASTterm.createTermFromVariable(varName);
				QASTatom newAtom = new QASTatom(var, term);
				movedOutAtoms.add(newAtom);
				termList.children[i] = var;
			}
		}

		return new Pair<QASTatom, ArrayList<QASTatom>>(this, movedOutAtoms);

	}

}
/*
 * JavaCC - OriginalChecksum=5521b04e7517df086398fd8ed3d519d2 (do not edit this
 * line)
 */
