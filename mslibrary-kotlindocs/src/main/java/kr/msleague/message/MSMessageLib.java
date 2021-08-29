package kr.msleague.message;

import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.message.api.MSMessage;
import kr.msleague.message.api.MSMessageAdapter;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MSMessageLib extends MSPlugin {

    /**
     * MSMessageAdapter 를 등록합니다.
     * 기본 제공 Adapters
     * PlayerAdapter, ItemStackAdapter
     *
     * @param adapters MSMessageAdapter
     */
    public static void registerAdapters(MSMessageAdapter<?>... adapters) {
    }

    /**
     * 메세지를 reformat 하여 MSMessage 로 리턴합니다.
     *
     * @param origin 원본 메세지
     * @param objs   객체
     * @return MSMessage
     */
    public static MSMessage reformat(String origin, Object... objs) {
        return new MSMessage() {
            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public String uncoloredMessage() {
                return null;
            }

            @Override
            public String coloredMessage() {
                return null;
            }

            @Override
            public void sendMessage(Player... players) {
            }

            @Override
            public void sendMessage(ConsoleCommandSender console) {
            }

            @Override
            public void sendUnColoredMessage(Player... players) {
            }

            @Override
            public void sendUnColoredMessage(ConsoleCommandSender console) {
            }

            @Override
            public void reformat(Object obj) {
            }
        };
    }

}
