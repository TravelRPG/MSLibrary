package kr.msleague.message.api;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface MSMessage {
    /**
     * 현재 상태의 메세지를 가져옵니다.
     * @return 현재 메세지
     */
    public String getMessage();

    /**
     * 색을 전부 제거한 메세지를 가져옵니다.
     * @return unColored String
     */
    public String uncoloredMessage();

    /**
     * 색 코드가 적용 된 메세지를 가져옵니다.
     * @return Colored String
     */
    public String coloredMessage();

    /**
     * 색 코드가 적용 된 메세지를 보냅니다.
     * @param players 메세지를 보낼 플레이어
     */
    public void sendMessage(Player... players);

    /**
     * 색 코드가 적용 된 메세지를 보냅니다.
     * @param console 메세지를 보낼 콘솔
     */
    public void sendMessage(ConsoleCommandSender console);

    /**
     * 색 코드가 적용되지 않은 메세지를 보냅니다.
     * @param players 메세지를 보낼 플레이어
     */
    public void sendUnColoredMessage(Player... players);

    /**
     * 색 코드가 적용되지 않은 메세지를 보냅니다.
     * @param console 메세지를 보낼 콘솔
     */
    public void sendUnColoredMessage(ConsoleCommandSender console);

    /**
     * 현재 메세지를 reformat 합니다.
     * @param obj 전달 받을 객체
     */
    public void reformat(Object obj);
}
