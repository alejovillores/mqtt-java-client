import model.packages.Puback;
import model.packages.Publish;
import model.packages.PublishQoS1;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class PubackTest {

    @Test
    public void test_01_puback_has_same_id_with_publish(){
        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x08,"OPENLABPRO","HELLO",false,false);
        Puback puback = new Puback(publish_qos1.getId());

        assertTrue(puback.fromPackage(publish_qos1.getId()));
    }


    @Test
    public void test_02_puback_to_bytes(){
        Puback puback = new Puback(8);

        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList(0x40, 0x02, 0x00, 0x08)){
            expectedBytes.add((byte) i);
        }

        ArrayList<Byte> pubackBytes = puback.toBytes();
        assertEquals(expectedBytes,pubackBytes);
    }
}
