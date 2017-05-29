package aztec.rbir_backend.logic;

import junit.framework.TestCase;

/**
 * Created by subhahs on 29/05/2017.
 */
public class XLSXMyFileReaderTest extends TestCase {
    public void testRead() throws Exception {
        XLSXMyFileReader xlsx = new XLSXMyFileReader();
        String result = xlsx.read("E://test.xlsx");
        assertEquals("retrieval of document",result.trim());
    }

}