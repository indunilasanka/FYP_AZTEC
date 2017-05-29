package aztec.rbir_backend.logic;

import junit.framework.TestCase;

/**
 * Created by subhahs on 29/05/2017.
 */
public class TXTMyFileReaderTest extends TestCase {

    public void testRead() throws Exception {
        TXTMyFileReader txt = new TXTMyFileReader();
        String result = txt.read("E://test.txt");
        assertEquals("retrieval of document",result.trim());
    }

}