import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/*
 *  Cleaned version of Expression.java
 */

/**
 * A class representing an abstract arithmetic expression
 */
public abstract class ExpressionCleaned
{
    /**
     * Creates a tree from an expression in postfix notation
     * @param postfix an array of Strings representing a postfix arithmetic expression
     * @return a new Expression that represents postfix
     */
    public static ExpressionCleaned expressionFromPostfix(String[] postfix)
    {
        Stack<ExpressionCleaned> stack = new Stack<>();

        for (String token : postfix)
        {
            // integer
            if (Character.isDigit(token.charAt(0)) ||
                (token.charAt(0) == '-' && token.length() > 1))
            {
                stack.push(new IntegerOperand(Integer.parseInt(token)));
            }

            // variable
            else if (Character.isLetter(token.charAt(0)))
            {
                stack.push(new VariableOperand(token));
            }

            // operator
            else
            {
                ExpressionCleaned right = stack.pop();
                ExpressionCleaned left = stack.pop();

                if (token.equals("+"))
                    stack.push(new SumExpression(left, right));
                else if (token.equals("-"))
                    stack.push(new DifferenceExpression(left, right));
                else if (token.equals("*"))
                    stack.push(new ProductExpression(left, right));
                else if (token.equals("/"))
                    stack.push(new QuotientExpression(left, right));
            }
        }

        return stack.pop();
    }

    public abstract String toPrefix();
    public abstract String toInfix();
    public abstract String toPostfix();
    public abstract ExpressionCleaned simplify();
    public abstract int evaluate(HashMap<String, Integer> assignments);
    public abstract Set<String> getVariables();
    @Override public abstract boolean equals(Object obj);

    @Override
    public String toString()
    {
        return toInfix();
    }

    /**
     * Prints the expression as a tree in DOT format for visualization
     * @param filename the name of the output file
     */
    public void drawExpression(String filename) throws IOException
    {
        BufferedWriter bw;
        FileWriter fw = new FileWriter(filename);
        bw = new BufferedWriter(fw);

        bw.write("graph Expression {\n");
        drawExprHelper(bw);
        bw.write("}\n");

        bw.close();
        fw.close();
    }

    protected abstract void drawExprHelper(BufferedWriter bw) throws IOException;
}

/**
 * A class representing an abstract operand
 */
abstract class Operand extends ExpressionCleaned { }

/**
 * A class representing an expression containing only a single integer value
 */
class IntegerOperand extends Operand
{
    protected int operand;

    public IntegerOperand(int operand)
    {
        this.operand = operand;
    }

    public String toPrefix() { return Integer.toString(operand); }
    public String toPostfix() { return Integer.toString(operand); }
    public String toInfix() { return Integer.toString(operand); }
    public ExpressionCleaned simplify() { return this; }
    public int evaluate(HashMap<String, Integer> assignments) { return operand; }

    public Set<String> getVariables()
    {
        return new TreeSet<String>();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof IntegerOperand)) return false;
        IntegerOperand other = (IntegerOperand) obj;
        return this.operand == other.operand;
    }

    protected void drawExprHelper(BufferedWriter bw) throws IOException
    {
        bw.write("\tnode" + hashCode() + "[label=" + operand + "];\n");
    }
}

/**
 * A class representing an expression containing only a single variable
 */
class VariableOperand extends Operand
{
    protected String variable;

    public VariableOperand(String variable)
    {
        this.variable = variable;
    }

    public String toPrefix() { return variable; }
    public String toPostfix() { return variable; }
    public String toInfix() { return variable; }
    public ExpressionCleaned simplify() { return this; }

    public int evaluate(HashMap<String, Integer> assignments)
    {
        return assignments.get(variable);
    }

    public Set<String> getVariables()
    {
        TreeSet<String> vars = new TreeSet<>();
        vars.add(variable);
        return vars;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof VariableOperand)) return false;
        VariableOperand other = (VariableOperand) obj;
        return this.variable.equals(other.variable);
    }

    protected void drawExprHelper(BufferedWriter bw) throws IOException
    {
        bw.write("\tnode" + hashCode() + "[label=" + variable + "];\n");
    }
}

/**
 * A class representing an expression involving an operator
 */
abstract class OperatorExpression extends ExpressionCleaned
{
    protected ExpressionCleaned left;
    protected ExpressionCleaned right;

    public OperatorExpression(ExpressionCleaned left, ExpressionCleaned right)
    {
        this.left = left;
        this.right = right;
    }

    /**
     * @return the operator symbol
     */
    protected abstract String getOperator();

    /* 
     * Helper methods
     */

    /** Builds prefix notation */
    public String buildPrefix()
    {
        return getOperator() + " " + left.toPrefix() + " " + right.toPrefix();
    }

    /** Builds postfix notation */
    public String buildPostfix()
    {
        return left.toPostfix() + " " + right.toPostfix() + " " + getOperator();
    }

    /** Builds infix notation */
    public String buildInfix()
    {
        return "(" + left.toInfix() + " " + getOperator() + " " + right.toInfix() + ")";
    }

    /** Merges variable sets */
    public Set<String> mergeVars()
    {
        TreeSet<String> vars = new TreeSet<>();
        vars.addAll(left.getVariables());
        vars.addAll(right.getVariables());
        return vars;
    }

    protected void drawExprHelper(BufferedWriter bw) throws IOException
    {
        String rootID = "\tnode" + hashCode();
        bw.write(rootID + "[label=\"" + getOperator() + "\"];\n");

        bw.write(rootID + " -- node" + left.hashCode() + ";\n");
        bw.write(rootID + " -- node" + right.hashCode() + ";\n");
        left.drawExprHelper(bw);
        right.drawExprHelper(bw);
    }
}

/*
* Operator classes
*/

class SumExpression extends OperatorExpression
{
    public SumExpression(ExpressionCleaned left, ExpressionCleaned right)
    {
        super(left, right);
    }

    protected String getOperator() { return "+"; }

    public String toPrefix() { return buildPrefix(); }
    public String toPostfix() { return buildPostfix(); }
    public String toInfix() { return buildInfix(); }

    public ExpressionCleaned simplify()
    {
        ExpressionCleaned sLeft = left.simplify();
        ExpressionCleaned sRight = right.simplify();

        if (sLeft instanceof IntegerOperand && sRight instanceof IntegerOperand)
            return new IntegerOperand(((IntegerOperand)sLeft).operand +
                                      ((IntegerOperand)sRight).operand);

        if (sRight instanceof IntegerOperand && ((IntegerOperand)sRight).operand == 0)
            return sLeft;

        if (sLeft instanceof IntegerOperand && ((IntegerOperand)sLeft).operand == 0)
            return sRight;

        return new SumExpression(sLeft, sRight);
    }

    public int evaluate(HashMap<String, Integer> assignments)
    {
        return left.evaluate(assignments) + right.evaluate(assignments);
    }

    public Set<String> getVariables() { return mergeVars(); }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof SumExpression)) return false;
        SumExpression other = (SumExpression) obj;
        return (left.equals(other.left) && right.equals(other.right)) ||
               (left.equals(other.right) && right.equals(other.left));
    }
}

class DifferenceExpression extends OperatorExpression
{
    public DifferenceExpression(ExpressionCleaned left, ExpressionCleaned right)
    {
        super(left, right);
    }

    protected String getOperator() { return "-"; }

    public String toPrefix() { return buildPrefix(); }
    public String toPostfix() { return buildPostfix(); }
    public String toInfix() { return buildInfix(); }

    public ExpressionCleaned simplify()
    {
        ExpressionCleaned sLeft = left.simplify();
        ExpressionCleaned sRight = right.simplify();

        if (sLeft instanceof IntegerOperand && sRight instanceof IntegerOperand)
            return new IntegerOperand(((IntegerOperand)sLeft).operand -
                                      ((IntegerOperand)sRight).operand);

        if (sRight instanceof IntegerOperand && ((IntegerOperand)sRight).operand == 0)
            return sLeft;

        if (sLeft.equals(sRight))
            return new IntegerOperand(0);

        return new DifferenceExpression(sLeft, sRight);
    }

    public int evaluate(HashMap<String, Integer> assignments)
    {
        return left.evaluate(assignments) - right.evaluate(assignments);
    }

    public Set<String> getVariables() { return mergeVars(); }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof DifferenceExpression)) return false;
        DifferenceExpression other = (DifferenceExpression) obj;
        return left.equals(other.left) && right.equals(other.right);
    }
}

class ProductExpression extends OperatorExpression
{
    public ProductExpression(ExpressionCleaned left, ExpressionCleaned right)
    {
        super(left, right);
    }

    protected String getOperator() { return "*"; }

    public String toPrefix() { return buildPrefix(); }
    public String toPostfix() { return buildPostfix(); }
    public String toInfix() { return buildInfix(); }

    public ExpressionCleaned simplify()
    {
        ExpressionCleaned sLeft = left.simplify();
        ExpressionCleaned sRight = right.simplify();

        if (sLeft instanceof IntegerOperand && sRight instanceof IntegerOperand)
            return new IntegerOperand(((IntegerOperand)sLeft).operand *
                                      ((IntegerOperand)sRight).operand);

        if (sLeft instanceof IntegerOperand && ((IntegerOperand)sLeft).operand == 0)
            return new IntegerOperand(0);

        if (sRight instanceof IntegerOperand && ((IntegerOperand)sRight).operand == 0)
            return new IntegerOperand(0);

        if (sRight instanceof IntegerOperand && ((IntegerOperand)sRight).operand == 1)
            return sLeft;

        if (sLeft instanceof IntegerOperand && ((IntegerOperand)sLeft).operand == 1)
            return sRight;

        return new ProductExpression(sLeft, sRight);
    }

    public int evaluate(HashMap<String, Integer> assignments)
    {
        return left.evaluate(assignments) * right.evaluate(assignments);
    }

    public Set<String> getVariables() { return mergeVars(); }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof ProductExpression)) return false;

        ProductExpression other = (ProductExpression) obj;

        return (left.equals(other.left) && right.equals(other.right)) ||
               (left.equals(other.right) && right.equals(other.left));
    }
}

class QuotientExpression extends OperatorExpression
{
    public QuotientExpression(ExpressionCleaned left, ExpressionCleaned right)
    {
        super(left, right);
    }

    protected String getOperator() { return "/"; }

    public String toPrefix() { return buildPrefix(); }
    public String toPostfix() { return buildPostfix(); }
    public String toInfix() { return buildInfix(); }

    public ExpressionCleaned simplify()
    {
        ExpressionCleaned sLeft = left.simplify();
        ExpressionCleaned sRight = right.simplify();

        if (sLeft instanceof IntegerOperand && sRight instanceof IntegerOperand)
            return new IntegerOperand(((IntegerOperand)sLeft).operand /
                                      ((IntegerOperand)sRight).operand);

        if (sLeft instanceof IntegerOperand && ((IntegerOperand)sLeft).operand == 0)
            return new IntegerOperand(0);

        if (sRight instanceof IntegerOperand && ((IntegerOperand)sRight).operand == 1)
            return sLeft;

        if (sLeft.equals(sRight))
            return new IntegerOperand(1);

        return new QuotientExpression(sLeft, sRight);
    }

    public int evaluate(HashMap<String, Integer> assignments)
    {
        return left.evaluate(assignments) / right.evaluate(assignments);
    }

    public Set<String> getVariables() { return mergeVars(); }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof QuotientExpression)) return false;
        QuotientExpression other = (QuotientExpression) obj;
        return left.equals(other.left) && right.equals(other.right);
    }
}
