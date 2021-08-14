package kr.msleague.bcsp.proxy;

import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.sys.PingPongPacket;

import java.util.concurrent.TimeUnit;

public class PingPongManagementThread extends Thread{
    private ChannelWrapper wrapper;
    public PingPongManagementThread(ChannelWrapper wrapper){
        this.wrapper = wrapper;
    }
    @Override
    public void run(){
        while(wrapper.getChannel().isOpen() && wrapper.getChannel().isActive()){
            wrapper.startCallBack(new PingPongPacket(-1L, System.currentTimeMillis()), PingPongPacket.class, packet->{
                long ping = System.currentTimeMillis() - packet.getRecievedTime();
                wrapper.getPingCalculator().processPing(ping);
                AbstractPacket rt = new PingPongPacket(packet.getTime(), -1L);
                rt.setCallBackResult(true);
                wrapper.sendPacket(rt);
            });
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }

    }
}
