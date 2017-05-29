package aztec.rbir_backend.logic;

import junit.framework.TestCase;

/**
 * Created by subhahs on 29/05/2017.
 */
public class PDFMyFileReaderTest extends TestCase {
    public void testRead() throws Exception {
        PDFMyFileReader pdf = new PDFMyFileReader();
        String result = pdf.read("E://test.pdf");
        assertEquals("retrieval of document",result.trim());
    }

}