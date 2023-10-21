package com.company.network;

public interface IConnection {

    void send(byte[] data);

    byte[] receive();

    void initialize(int port, String ipAddress);

    void close();
}
