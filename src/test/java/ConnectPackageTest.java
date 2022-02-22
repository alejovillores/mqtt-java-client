import model.packages.ConnectPackage;
import org.junit.jupiter.api.Test;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ConnectPackageTest {
    @Test
    public void test_01_connect_package_to_bytes_header(){
        ConnectPackage connectPackage = new ConnectPackage("avillores1",true,false);

        ArrayList<Byte> bytes = new ArrayList<Byte>();
        bytes.add(connectPackage.headerBytes());
        ArrayList<Byte> real  = new ArrayList<Byte>();
        real.add((byte) 0x10);
        assertEquals(real,bytes);
    }

    @Test
    public void test_02_connect_fix_variable_header_to_bytes(){
        ConnectPackage connectPackage = new ConnectPackage("avillores1",true,false);
        connectPackage.setAuth("alejovillores","ale123");
        connectPackage.setWillData("boca","gano boca 3-2");
        connectPackage.setQos(0);

        ArrayList<Byte> bytes = new ArrayList<Byte>();
        bytes.add(connectPackage.headerBytes());
        bytes.addAll(connectPackage.remainingLength());
        bytes.addAll(connectPackage.mqtt_bytes());
        bytes.add((byte)0x04);
        bytes.add((byte)connectPackage.flags());
        bytes.add((byte)0x00);
        bytes.add((byte)0x0a);

        ArrayList<Byte> real  = new ArrayList<Byte>();
        List<Integer> l = Arrays.asList(0x10, 0x42, 0x00, 0x04, 0x4d, 0x51, 0x54, 0x54, 0x04, 0xc6, 0x00, 0x0a);
        for (int l_i: l ){
            real.add((byte) l_i);
        }
        assertEquals(real,bytes);
    }
}
