package aztec.rbir_backend.logic;

import junit.framework.TestCase;

/**
 * Created by subhahs on 29/05/2017.
 */
public class DOCXMyFileReaderTest extends TestCase {
    public void testRead() throws Exception {
        DOCXMyFileReader doc = new DOCXMyFileReader();
        String result = doc.read("E://test.docx");
        assertEquals("retrieval of document",result.trim());
    }

}