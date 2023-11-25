package com.cciocau.goose.efb.foreflight;

import com.cciocau.goose.efb.EFB;
import com.cciocau.goose.efb.EFBListener;
import com.cciocau.goose.efb.EFBRepository;
import com.cciocau.goose.efb.EFBType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.*;

public class ForeFlightListener implements EFBListener {
    private static final Logger logger = LogManager.getLogger(ForeFlightListener.class);

    private final ForeFlightConfig config;
    private final EFBRepository efbRepository;

    private boolean keepReceiving = true;

    public ForeFlightListener(EFBRepository efbRepository) {
        this(new ForeFlightConfig(), efbRepository);
    }

    public ForeFlightListener(ForeFlightConfig config, EFBRepository efbRepository) {
        this.config = config;
        this.efbRepository = efbRepository;
    }

    public void listen() {
        byte[] buf = new byte[65536];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        try {
            var socket = new DatagramSocket(config.getBroadcastPort());
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
                    // probably will be ignored
                }
            }

            socket.close();
        } catch (Exception exception) {
            logger.error("Failed to receive ForeFlight broadcast - {}", exception.getMessage());
        }
    }

    public void stop() {
        keepReceiving = false;
    }
}
