package model.packages;

import model.ReturnCode;

import java.util.ArrayList;

public class ConnackPackage implements MqttPackage {

    private boolean sessionPresentFlag;
    private ReturnCode returnCode;

    public ConnackPackage(boolean sessionPresentFlag, ReturnCode returnCode) {
        this.returnCode = returnCode;
        this.sessionPresentFlag = sessionPresentFlag;
    }


    @Override
    public ArrayList<Byte> toBytes() {
        byte fix_header = 0x20;
        byte remaining_header = 0x02;
        byte rc;
        byte flag;
        ArrayList<Byte> bytes = new ArrayList<>();

        if (this.sessionPresentFlag) flag = 0x01 ;else flag = 0x00;

        switch (this.returnCode){
            case Accepted: rc = 0x00;
                break;
            case CRNotAuthorized: rc = 0x05;
                break;
            case CRServerUnavailable:rc = 0x03;
                break;
            case CRIdentifierRejected:rc = 0x02;
                break;
            case CRBadCredentials:rc = 0x04;
                break;
            case CRUnacceptableProtocolVersion:rc = 0x01;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.returnCode);
        }

        bytes.add(fix_header);
        bytes.add(remaining_header);
        bytes.add(flag);
        bytes.add(rc);
        return bytes;
    }

    public boolean accepted() {
        return this.returnCode == ReturnCode.Accepted;
    }
}
