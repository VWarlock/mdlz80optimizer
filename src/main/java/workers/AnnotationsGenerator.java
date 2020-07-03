/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import cl.MDLConfig;
import code.CodeBase;
import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author santi
 */
public class AnnotationsGenerator implements MDLWorker {
    MDLConfig config = null;
    String outputFileName = null;

    public AnnotationsGenerator(MDLConfig a_config)
    {
        config = a_config;
    }
    
    
    @Override
    public String docString() {
        return "  -a <output file>: generates an 'annotations' file containing filename/linenumber tagged messags generated by the optimizer. This is supposed to be used to be loaded by text editor and provide in-editor optimization hints.\n";
    }

    @Override
    public boolean parseFlag(List<String> flags) {
        if (flags.get(0).equals("-a") && flags.size()>=2) {
            flags.remove(0);
            outputFileName = flags.remove(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean work(CodeBase code) {
        if (outputFileName != null) {
            config.debug("Executing "+this.getClass().getSimpleName()+" worker...");

            try (FileWriter fw = new FileWriter(outputFileName)) {
                for(String annotation:config.logger.getAnnotations()) {
                    fw.write(annotation + "\n");
                }
                fw.flush();
            } catch (Exception e) {
                config.error("Cannot write to file " + outputFileName);
                return false;
            }
        }
        return true;
        
    }
    
}