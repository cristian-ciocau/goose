package com.cciocau.goose.protocol.gdl90;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class GDL90Client {
    private final InetSocketAddress address;

    public GDL90Client(InetSocketAddress address) {
        this.address = address;
    }

    public void send(byte[] data) {
        try (var socket = new DatagramSocket(address.getPort())) {
            socket.setSoTimeout(1000);

            byte[] dataWithCrc = new Crc().prepareMessage(data);
            DatagramPacket packet = new DatagramPacket(dataWithCrc, dataWithCrc.length, address.getAddress(), address.getPort());
            socket.send(packet);

        } catch (Exception exception) {
            // TODO
        }
    }
}
