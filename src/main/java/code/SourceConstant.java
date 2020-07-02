/*
 * author: Santiago Ontañón Villar (Brain Games)
 */
package code;

public class SourceConstant {
    public String name;
    public Expression exp;
    Integer valueCache;  // null if not yet evaluated
    
    SourceStatement s;  // the statement where it was defined
    
    public SourceConstant(String a_name, Integer a_value, Expression a_exp, SourceStatement a_s)
    {
        name = a_name;
        valueCache = a_value;
        exp = a_exp;
        s = a_s;
    }
    
    
    public Integer getValue(CodeBase code, boolean silent)
    {
        if (valueCache != null) {
            return valueCache;
        } else {
            valueCache = exp.evaluate(s, code, silent);
            return valueCache;
        }
    }
    
    
    public boolean isLabel()
    {
        return exp.type == Expression.EXPRESSION_SYMBOL && 
               exp.symbolName.equalsIgnoreCase(CodeBase.CURRENT_ADDRESS);
    }
}