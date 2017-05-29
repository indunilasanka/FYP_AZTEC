package aztec.rbir_backend.logic;

import junit.framework.TestCase;

/**
 * Created by subhahs on 29/05/2017.
 */
public class XLSMyFileReaderTest extends TestCase {
    public void testRead() throws Exception {
        XLSMyFileReader xls = new XLSMyFileReader();
        String result = xls.read("E://test.xls");
        assertEquals("retrieval of document",result.trim());
    }

}