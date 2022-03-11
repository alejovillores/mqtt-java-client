
import model.packages.Publish;
import model.packages.PublishQoS0;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PublishTest {

    @Test
    public void test_01_publish_with_no_id_returs_minus_one(){
        Publish publish_qos0 = new PublishQoS0("topic_name",0,"payload",false,false);

        assertEquals(-1,publish_qos0.getId());
    }

    @Test
    public void test_02_publish_qos0_is_retained(){
        Publish publish_qos0 = new PublishQoS0("topic",0,"topic",true,false);

        assertTrue(publish_qos0.isRetained());
    }

    @Test
    public void test_03_publish_qos0_is_not_retained(){
        Publish publish_qos0 = new PublishQoS0("topic",0,"topic",false,false);

        assertFalse(publish_qos0.isRetained());
    }

    @Test
    public void test_04_publish_qos_is_duplicated(){
        Publish publish_qos0 = new PublishQoS0("topic",0,"topic",false,true);

        assertTrue(publish_qos0.isDuplicated());
    }

    @Test
    public void test_05_publish_qos_is_not_duplicated(){
        Publish publish_qos0 = new PublishQoS0("topic",0,"topic",false,false);

        assertFalse(publish_qos0.isDuplicated());
    }

    @Test
    public void test_06_publish_qos_to_bytes(){
        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList( 0x31, 0x11, 0x00, 0x0A,  0x4f, 0x50, 0x45, 0x4e, 0x4c, 0x41, 0x42, 0x50, 0x52, 0x4f, 0x48, 0x45, 0x4c, 0x4c, 0x4f)){
            expectedBytes.add((byte) i);
        }

        Publish publish_qos0 = new PublishQoS0("OPENLABPRO",0,"HELLO",true,false);
        ArrayList<Byte> qosToBytes = publish_qos0.toBytes();

        assertEquals(expectedBytes,qosToBytes);
    }

}
