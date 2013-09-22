package org.sadnatau.relc.compiler;

import com.google.common.io.Resources;
import org.junit.Test;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class RelcCompilerTest {

    @Test
    public void testCompile() throws Exception {

        String decompPath = Resources.getResource("org/sadnatau/relc/compiler/decomp.txt").getPath();
        String relationPath = Resources.getResource("org/sadnatau/relc/compiler/relation.txt").getPath();

        RelcCompiler relcCompiler = new RelcCompiler(relationPath, decompPath);

        relcCompiler.compile();
    }
}
