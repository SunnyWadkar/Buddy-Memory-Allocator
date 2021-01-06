import student.TestCase;

// -------------------------------------------------------------------------
/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class HashTest extends TestCase {

    /**
     * Test the all the methods of hash class
     */
    public void testHash() {

        final String data = "DBZ";
        Hash myHash = new Hash(256, 101, 0.5);
        assertEquals(myHash.getHashKey(data), 38);
        int flag = myHash.insert(data);
        assertEquals(0, flag);
        assertEquals(-1, myHash.insert(data));
        assertEquals(0, myHash.insert("Death Note"));
        assertEquals(0, myHash.delete(data));
        assertEquals(-1, myHash.delete(data));
        String recordName1 = "Format<SEP>Series";
        assertEquals(-1, myHash.addRecordFields(data, recordName1));
        myHash.insert(data);
        assertEquals(0, myHash.addRecordFields(data, recordName1));
        String recordName2 = "Genre<SEP>Thriller";
        assertEquals(-2, myHash.deleteRecordFields(data, recordName2));
        assertEquals(0, myHash.deleteRecordFields(data, recordName1));
        myHash.delete(data);
        assertEquals(-1, myHash.deleteRecordFields(data, recordName1));

    }

}
