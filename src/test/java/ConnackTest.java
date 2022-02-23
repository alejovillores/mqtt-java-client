import model.ReturnCode;
import model.packages.ConnackPackage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ConnackTest {
    @Test
    public void test_01_connack_is_accepted(){
        ConnackPackage connack = new ConnackPackage(false, ReturnCode.Accepted);
        assertTrue(connack.accepted());
    }

    @Test
    public void test_02_connack_to_bytes(){
        ArrayList<Byte> connackBytes = new ArrayList<>();
        ConnackPackage connack = new ConnackPackage(false, ReturnCode.Accepted);
        for (int l_i: Arrays.asList(0x20, 0x02, 0x00, 0x00) ){
            connackBytes.add((byte) l_i);
        }
        ArrayList<Byte> bytes = connack.toBytes();

        assertEquals(connackBytes,bytes);
    }
    
}
