package ppg.test.multi;

public class Expr
{
	int lNum, rNum;
	Expr lExpr, rExpr;
	int op;
	int exprType;
	
	public Expr(Integer num, int oper, Integer num2) {
		lNum = num.intValue();
		rNum = num2.intValue();
		op = oper;
		lExpr = rExpr = null;
	}
	
	/*
	public Expr(Expr exp, int oper, Expr expr2) {
		lExpr = exp;
		rExpr = expr2;
		op = oper;
	}*/
	
	public String toString() {
		if (lExpr == null) {
			return String.valueOf(lNum) + " " + Token.toString(op) + " " + String.valueOf(rNum);
		} else {
			return lExpr.toString() + Token.toString(op) + rExpr.toString();
		}
	}
}
