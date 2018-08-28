package com.telecom.ast.sitesurvey.model;

public class BtsInfoData {

    int sNo;
    String type;
    String Name;
    String CabinetQty;
    String NoofDCDBBox;
    String NoofKroneBox;
    String NoofTransmissionRack;
    String Microwave;

    String OperatorType;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCabinetQty() {
        return CabinetQty;
    }

    public void setCabinetQty(String cabinetQty) {
        CabinetQty = cabinetQty;
    }

    public String getNoofDCDBBox() {
        return NoofDCDBBox;
    }

    public void setNoofDCDBBox(String noofDCDBBox) {
        NoofDCDBBox = noofDCDBBox;
    }

    public String getNoofKroneBox() {
        return NoofKroneBox;
    }

    public void setNoofKroneBox(String noofKroneBox) {
        NoofKroneBox = noofKroneBox;
    }

    public String getNoofTransmissionRack() {
        return NoofTransmissionRack;
    }

    public void setNoofTransmissionRack(String noofTransmissionRack) {
        NoofTransmissionRack = noofTransmissionRack;
    }

    public String getMicrowave() {
        return Microwave;
    }

    public void setMicrowave(String microwave) {
        Microwave = microwave;
    }


    public String getOperatorType() {
        return OperatorType;
    }

    public void setOperatorType(String operatorType) {
        OperatorType = operatorType;
    }
}
