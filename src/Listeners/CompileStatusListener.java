package Listeners;

import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerMessage;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import log.ActionLogger;

/**
 * Created by aarjay on 16/08/16.
 */
public class CompileStatusListener implements CompilationStatusListener {
    /**
     * Invoked in a Swing dispatch thread after the compilation is finished.
     *
     * @param aborted        true if compilation has been cancelled
     * @param errors         error count
     * @param warnings       warning count
     * @param compileContext context for the finished compilation
     */

    private static CompileStatusListener compileStatusListener = null;

    public static CompileStatusListener getInstance(){
        if(compileStatusListener==null){
            compileStatusListener =  new CompileStatusListener();
        }
        return compileStatusListener;
    }

    @Override
    public void compilationFinished(boolean aborted, int errors, int warnings, CompileContext compileContext) {

//        CompilerMessage[] compilerMessagesError = compileContext.getMessages(CompilerMessageCategory.ERROR);
//        CompilerMessage[] compilerMessagesInfo = compileContext.getMessages(CompilerMessageCategory.INFORMATION);
//        CompilerMessage[] compilerMessagesWarning = compileContext.getMessages(CompilerMessageCategory.WARNING);
//        CompilerMessage[] compilerMessagesStattistics = compileContext.getMessages(CompilerMessageCategory.STATISTICS);
        System.out.println(compileContext.toString());
        ActionLogger.getInstance().logCompilationStatus(compileContext,errors,aborted,warnings);
    }

    @Override
    public void fileGenerated(String outputRoot, String relativePath) {

    }
}
