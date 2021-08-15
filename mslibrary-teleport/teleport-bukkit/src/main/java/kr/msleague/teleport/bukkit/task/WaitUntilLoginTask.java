package kr.msleague.teleport.bukkit.task;

import kr.msleague.teleport.bukkit.TeleporterBukkitBootstrap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.function.Consumer;

public class WaitUntilLoginTask extends BukkitRunnable {
    private UUID player;
    private Consumer<Player> cons;
    private int counter = 0;
    public WaitUntilLoginTask(UUID player, Consumer<Player> cons){
        this.player = player;
        this.cons = cons;
        this.runTaskTimer(TeleporterBukkitBootstrap.getPlugin(), 2L, 2L);
    }
    @Override
    public void run() {
        if(this.counter++ >= 50){
            cancel();
            return;
        }
        Player p = Bukkit.getPlayer(player);
        if(p != null && p.isOnline()){
            cancel();
            cons.accept(p);
        }
    }
}
