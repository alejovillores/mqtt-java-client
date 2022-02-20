import model.packages.ConnectPackage;
import org.junit.jupiter.api.Test;

import static model.Qos.Qos1;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectPackageTest {
    @Test
    public void test_01_connect_package_to_bytes(){
        ConnectPackage connectPackage = new ConnectPackage("alejo_1",true,false);
        connectPackage.setAuth("alejovillores","alejo123");
        connectPackage.setWillData("error","tuve un error");
        connectPackage.setQos(Qos1);

    }
}
