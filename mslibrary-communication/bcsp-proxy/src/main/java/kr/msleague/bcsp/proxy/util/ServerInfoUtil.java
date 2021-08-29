package kr.msleague.bcsp.proxy.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Map;

public class ServerInfoUtil {
    public static String getServerNameByPort(int port) {
        for (Map.Entry<String, ServerInfo> etr : ProxyServer.getInstance().getServers().entrySet()) {
            if (etr.getValue().getAddress().getPort() == port)
                return etr.getValue().getName();
        }
        return null;
    }
}
