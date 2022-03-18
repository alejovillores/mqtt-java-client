import model.packages.Unsuback;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
public class UnsubackTest {

    @Test
    public void test_01_suback_to_bytes(){
        ArrayList<Byte> expectedBytes = new ArrayList<>();

        for (int n : Arrays.asList(0xB0, 0x02, 0x00, 0x0a)){
            expectedBytes.add((byte) n);
        }
        Unsuback unsuback = new Unsuback(0x0a);


        assertEquals(expectedBytes,unsuback.toBytes());
    }
}
