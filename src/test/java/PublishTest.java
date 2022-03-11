
import model.packages.Publish;
import model.packages.PublishQoS0;
import model.packages.PublishQoS1;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PublishTest {

    @Test
    public void test_01_publish_with_no_id_returns_minus_one(){
        Publish publish_qos0 = new PublishQoS0("topic_name","payload",false,false);

        assertEquals(-1,publish_qos0.getId());
    }

    @Test
    public void test_02_publish_qos0_is_retained(){
        Publish publish_qos0 = new PublishQoS0("topic","topic",true,false);

        assertTrue(publish_qos0.isRetained());
    }

    @Test
    public void test_03_publish_qos0_is_not_retained(){
        Publish publish_qos0 = new PublishQoS0("topic","topic",false,false);

        assertFalse(publish_qos0.isRetained());
    }

    @Test
    public void test_04_publish_qos_is_duplicated(){
        Publish publish_qos0 = new PublishQoS0("topic","topic",false,true);

        assertTrue(publish_qos0.isDuplicated());
    }

    @Test
    public void test_05_publish_qos_is_not_duplicated(){
        Publish publish_qos0 = new PublishQoS0("topic","topic",false,false);

        assertFalse(publish_qos0.isDuplicated());
    }

    @Test
    public void test_06_publish_qos_with_retain_to_bytes(){
        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList( 0x31, 0x11, 0x00, 0x0A,  0x4f, 0x50, 0x45, 0x4e, 0x4c, 0x41, 0x42, 0x50, 0x52, 0x4f, 0x48, 0x45, 0x4c, 0x4c, 0x4f)){
            expectedBytes.add((byte) i);
        }

        Publish publish_qos0 = new PublishQoS0("OPENLABPRO","HELLO",true,false);
        ArrayList<Byte> qosToBytes = publish_qos0.toBytes();

        assertEquals(expectedBytes,qosToBytes);
    }

    @Test
    public void test_07_publish_qos_with_dup_to_bytes(){
        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList( 0x38, 0x11, 0x00, 0x0A,  0x4f, 0x50, 0x45, 0x4e, 0x4c, 0x41, 0x42, 0x50, 0x52, 0x4f, 0x48, 0x45, 0x4c, 0x4c, 0x4f)){
            expectedBytes.add((byte) i);
        }

        Publish publish_qos0 = new PublishQoS0("OPENLABPRO","HELLO",false,true);
        ArrayList<Byte> qosToBytes = publish_qos0.toBytes();

        assertEquals(expectedBytes,qosToBytes);
    }


    // Publish Qos1 tests

    @Test
    public void test_10_publish_qos1_with_id_16(){
        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x10,"topic_name","payload",false,false);
        // 00000000 00001010
        assertEquals(16, publish_qos1.getId());
    }

    @Test
    public void test_11_publish_qos1_is_retained(){
        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x10,"topic_name","payload",false,true);

        assertTrue(publish_qos1.isRetained());
    }

    @Test
    public void test_12_publish_qos1_is_not_retained(){
        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x10,"topic_name","payload",false,false);

        assertFalse(publish_qos1.isRetained());
    }

    @Test
    public void test_13_publish_qos_1_is_duplicated(){
        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x10,"topic_name","payload",true,true);

        assertTrue(publish_qos1.isDuplicated());
    }

    @Test
    public void test_014_publish_qos_is_not_duplicated(){
        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x10,"topic_name","payload",false,true);

        assertFalse(publish_qos1.isDuplicated());
    }

    @Test
    public void test_15_publish_qos1_to_bytes(){
        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList( 0x32, 0x13, 0x00, 0x0A, 0x4f, 0x50, 0x45, 0x4e, 0x4c, 0x41, 0x42, 0x50, 0x52, 0x4f, 0x00, 0x08, 0x48, 0x45, 0x4c, 0x4c, 0x4f)){
            expectedBytes.add((byte) i);
        }

        Publish publish_qos1 = new PublishQoS1((byte)0x00,(byte)0x08,"OPENLABPRO","HELLO",false,false);
        ArrayList<Byte> qosToBytes = publish_qos1.toBytes();

        assertEquals(expectedBytes,qosToBytes);
    }



}
