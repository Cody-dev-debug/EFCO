package com.example.efco.ui.disposal;

public class ClientData {
    private String clientName;
    private String phone;
    private String address;
    private String id;


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientData(String clientName, String phone, String address) {
        this.clientName = clientName;
        this.phone = phone;
        this.address = address;

    }


}

