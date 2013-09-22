package org.sadnatau.relc.compiler;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class RelcCompilationResult {

    private String compiledFileName;
    private String compiledFileRoot;
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCompiledFileName() {
        return compiledFileName;
    }

    public void setCompiledFileName(String compiledFileName) {
        this.compiledFileName = compiledFileName;
    }

    public String getCompiledFileRoot() {
        return compiledFileRoot;
    }

    public void setCompiledFileRoot(String compiledFileRoot) {
        this.compiledFileRoot = compiledFileRoot;
    }
}
