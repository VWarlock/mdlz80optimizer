/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser.dialects;

import cl.MDLConfig;
import code.CPUOpSpec;
import code.CodeBase;
import code.Expression;
import code.SourceConstant;
import code.SourceFile;
import code.CodeStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import parser.SourceLine;
import parser.Tokenizer;

/**
 *
 * @author santi
 */
public abstract class SjasmDerivativeDialect implements Dialect {
    
    
    public static class SjasmStruct {
        String name;
        SourceFile file;
        CodeStatement start;
        List<String> attributeNames = new ArrayList<>();
        List<Integer> attributeSizes = new ArrayList<>();
    }
        
    
    MDLConfig config;
    
    List<String> forbiddenLabelNames = new ArrayList<>();
    
    SjasmStruct struct = null;
    List<SjasmStruct> structs = new ArrayList<>();
    
    List<String> modules = new ArrayList<>();
    
    HashMap<String, Integer> reusableLabelCounts = new HashMap<>();

    Integer currentPage = null;
    HashMap<String,Integer> symbolPage = new HashMap<>();
    
    
    private SourceConstant getLastAbsoluteLabel(CodeStatement s) 
    {
        
        while(s != null) {
            // sjasm considers any label as an absolute label, even if it's associated with an equ,
            // so, no need to check if s.label.isLabel() (as in asMSX):
            if (s.label != null &&
                !s.label.originalName.startsWith(".") &&
                !Tokenizer.isInteger(s.label.originalName)) {
                return s.label;
            } else {
                s = s.source.getPreviousStatementTo(s, s.source.code);
            }
        }
        return null;        
    }
    
    
    public boolean addFakeInstruction(String in, String out)
    {
        String data[] = {in,"1","ff ff","2", "","","","", "","","","", "false"};
        CPUOpSpec fakeSpec = config.opSpecParser.parseOpSpecLine(data, config);
        if (fakeSpec == null) {
            config.error("cannot parse fake instruction " + in);
            return false;
        } 

        fakeSpec.fakeInstructionEquivalent = new ArrayList<>();
        for(String line:out.split("\n")) {
            fakeSpec.fakeInstructionEquivalent.add(Tokenizer.tokenize(line));
        }
        
        config.opParser.addOpSpec(fakeSpec);        
        return true;
    }
        
    
    public boolean parseLineStruct(List<String> tokens, List<CodeStatement> l, SourceLine sl,
            CodeStatement s, CodeStatement previous, SourceFile source, CodeBase code) {
    
        // struct definitions:
        for(SjasmStruct st:structs) {
            if (tokens.get(0).equals(st.name)) {
                tokens.remove(0);
                // it is a struct definition:
                boolean done = false;
                List<Expression> data = new ArrayList<>();
                while (!done) {
                    Expression exp = config.expressionParser.parse(tokens, s, previous, code);
                    if (exp == null) {
                        config.error("Cannot parse line " + sl);
                        return false;
                    } else {
                        data.add(exp);
                    }
                    if (!tokens.isEmpty() && tokens.get(0).equals(",")) {
                        tokens.remove(0);
                    } else {
                        done = true;
                    }
                }
                if (data.size() != st.attributeSizes.size()) {
                    config.error("Struct instantiation has the wrong number of fields ("+data.size()+" vs the expected "+st.attributeSizes.size()+") in " + sl);
                    return false;                    
                }
                l.clear();
                
                for(int i = 0;i<data.size();i++) {
                    CodeStatement s2;
                    switch(st.attributeSizes.get(i)) {
                        case 1:
                            s2 = new CodeStatement(CodeStatement.STATEMENT_DATA_BYTES, sl, source, config);
                            break;
                        case 2:
                            s2 = new CodeStatement(CodeStatement.STATEMENT_DATA_WORDS, sl, source, config);
                            break;
                        case 4:
                            s2 = new CodeStatement(CodeStatement.STATEMENT_DATA_DOUBLE_WORDS, sl, source, config);
                            break;
                        default:
                            config.error("Field " + st.attributeNames.get(i) + " of struct " + st.name + " has an unsupported size in " + sl);
                            return false;
                    }
                    if (i == 0) s2.label = s.label;
                    s2.data = new ArrayList<>();
                    s2.data.add(data.get(i));
                    l.add(s2);
                }
                return config.lineParser.parseRestofTheLine(tokens, l, sl, s, previous, source, code);
            }
        }
        
        return false;
    }
    
    
    @Override
    public Pair<String, SourceConstant> newSymbolName(String name, Expression value, CodeStatement previous) {
        SourceConstant lastAbsoluteLabel = null;
        if (name.startsWith("@")) {
            name = name.substring(1);
        } else if (name.startsWith(".")) {
            lastAbsoluteLabel = getLastAbsoluteLabel(previous);
            if (lastAbsoluteLabel != null) {
                name = lastAbsoluteLabel.name + name;
            } else {
                name = config.lineParser.getLabelPrefix() + name;
            }
        } else if (Tokenizer.isInteger(name)) {
            // it'startStatement a reusable label:
            int count = 1;
            if (reusableLabelCounts.containsKey(name)) {
                count = reusableLabelCounts.get(name);
            }
            reusableLabelCounts.put(name, count+1);
            name = config.lineParser.getLabelPrefix() + "_sjasm_reusable_" + name + "_" + count;
        } else {
            name = config.lineParser.getLabelPrefix() + name;
        }
        
        if (forbiddenLabelNames.contains(name.toLowerCase())) return null;
        
        symbolPage.put(name, currentPage);
        
        return Pair.of(name, lastAbsoluteLabel);
    }
    
    
    @Override
    public Pair<String, SourceConstant> symbolName(String name, CodeStatement previous) {
        SourceConstant lastAbsoluteLabel = null;
        if (name.startsWith(".")) {
            lastAbsoluteLabel = getLastAbsoluteLabel(previous);
            if (lastAbsoluteLabel != null) {
                return Pair.of(lastAbsoluteLabel.name + name, lastAbsoluteLabel);
            } else {
                return Pair.of(name, null);
            }
        } else if ((name.endsWith("f") || name.endsWith("F")) && Tokenizer.isInteger(name.substring(0, name.length()-1))) {
            // it'startStatement a reusable label:
            name = name.substring(0, name.length()-1);
            int count = 1;
            if (reusableLabelCounts.containsKey(name)) {
                count = reusableLabelCounts.get(name);
            }
            return Pair.of("_sjasm_reusable_" + name + "_" + count, null);
        } else if ((name.endsWith("b") || name.endsWith("B")) && Tokenizer.isInteger(name.substring(0, name.length()-1))) {
            // it'startStatement a reusable label:
            name = name.substring(0, name.length()-1);
            int count = reusableLabelCounts.get(name);
            return Pair.of("_sjasm_reusable_" + name + "_" + (count-1), null);
        } else {            
            return Pair.of(name, null);
        }
    }
    
}
