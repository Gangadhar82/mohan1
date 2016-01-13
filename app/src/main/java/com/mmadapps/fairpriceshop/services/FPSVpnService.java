package com.mmadapps.fairpriceshop.services;

import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * Created by Baskar on 12/28/2015.
 */
public class FPSVpnService extends VpnService {

    private Thread mThread;
    private ParcelFileDescriptor mInterface;
    Builder builder = new Builder();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        //http://www.linuxsecrets.com/categories/latest-articles/android-vpn-service-explained-with-packet-bypass-example-program
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //a. Configure the TUN and get the interface.
                    mInterface = builder.setSession("MyVPNService")
                            .addAddress("10.26.50.45", 24)
                            .addDnsServer("8.8.8.8")
                            .addRoute("0.0.0.0", 0).establish();
                    //b. Packets to be sent are queued in this input stream.
                    FileInputStream in = new FileInputStream(
                            mInterface.getFileDescriptor());
                    //b. Packets received need to be written to this output stream.
                    FileOutputStream out = new FileOutputStream(mInterface.getFileDescriptor());
                    //c. The UDP channel can be used to pass/get ip package to/from server
                    DatagramChannel tunnel = DatagramChannel.open();
                    // Connect to the server, localhost is used for demonstration only.
                    tunnel.connect(new InetSocketAddress("127.0.0.1", 8087));
                    //d. Protect this socket, so package send by it will not be feedback to the vpn service.
                    protect(tunnel.socket());
                    //e. Use a loop to pass packets.
                    while (true) {
                        //get packet with in
                        //put packet to tunnel
                        //get packet form tunnel
                        //return packet with out
                        //sleep is a must
                        Thread.sleep(100);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try{
                        if(mInterface != null){
                            mInterface.close();
                            mInterface = null;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        },"FPS VPN Service");

        mThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(mThread != null){
            mThread.interrupt();
        }
        super.onDestroy();
    }
}
