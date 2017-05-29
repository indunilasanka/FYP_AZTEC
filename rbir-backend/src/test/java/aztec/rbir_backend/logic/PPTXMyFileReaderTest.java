package aztec.rbir_backend.logic;

import junit.framework.TestCase;

/**
 * Created by subhahs on 29/05/2017.
 */
public class PPTXMyFileReaderTest extends TestCase {
    public void testRead() throws Exception {
        PPTXMyFileReader pptx = new PPTXMyFileReader();
        String result = pptx.read("E://test.pptx");
        assertEquals("retrieval of document",result.trim());
    }

}