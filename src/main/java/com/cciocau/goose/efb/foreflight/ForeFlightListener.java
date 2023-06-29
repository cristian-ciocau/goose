package com.cciocau.goose.efb.foreflight;

import com.cciocau.goose.efb.EFB;
import com.cciocau.goose.efb.EFBListener;
import com.cciocau.goose.efb.EFBRepository;
import com.cciocau.goose.efb.EFBType;
import com.google.gson.Gson;

import java.net.*;

public class ForeFlightListener implements EFBListener {
    private static final int FF_BROADCAST_PORT = 63093;
    private boolean keepReceiving = true;

    private final EFBRepository efbRepository;

    public ForeFlightListener(EFBRepository efbRepository) {
        this.efbRepository = efbRepository;
    }

    public void listen() {
        byte[] buf = new byte[65536];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        try {
            var socket = new DatagramSocket(FF_BROADCAST_PORT);
            socket.setSoTimeout(5000);

            while (keepReceiving) {
                try {
                    socket.receive(dp);

                    var response = new String(dp.getData(), 0, dp.getLength());

                    Gson gson = new Gson();
                    var broadcast = gson.fromJson(response, ForeFlightBroadcast.class);

                    var name = broadcast.getApplicationName();
                    var host = dp.getAddress().getHostAddress();
                    int port = broadcast.getGdl90Configuration().getPort();

                    var address = new InetSocketAddress(host, port);

                    var efb = new EFB(name, address, EFBType.FOREFLIGHT);

                    efbRepository.add(efb);

                } catch (SocketTimeoutException exception) {
                    // TODO
                }
            }

            socket.close();
        } catch (Exception exception) {
            // TODO
        }
    }

    public void stop() {
        keepReceiving = false;
    }
}
