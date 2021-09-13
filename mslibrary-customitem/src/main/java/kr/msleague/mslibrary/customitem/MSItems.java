package kr.msleague.mslibrary.customitem;


import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemFactory;
import kr.msleague.mslibrary.customitem.impl.MinecraftItemFactory;
import kr.msleague.mslibrary.customitem.impl.MinecraftItemHub;
import kr.msleague.mslibrary.customitem.impl.database.JsonFileItemDatabase;
import kr.msleague.mslibrary.customitem.impl.database.MySQLItemDatabase;
import kr.msleague.mslibrary.customitem.impl.database.YamlFileItemDatabase;
import kr.msleague.mslibrary.database.impl.internal.MySQLDatabase;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.concurrent.ExecutorService;

/**
 * Custom Item Convenience class.
 */
public class MSItems {

    /**
     * Yaml File 기반 아이템 데이터베이스를 가져옵니다.
     *
     * @param service IO worker
     * @param file    파일 디렉토리
     * @return 새로운 아이템 데이터베이스
     */
    public static YamlFileItemDatabase newYamlDatabase(ExecutorService service, File file) {
        return new YamlFileItemDatabase(service, file);
    }

    /**
     * Json File 기반 아이템 데이터베이스를 가져옵니다.
     *
     * @param service IO worker
     * @param file    파일 디렉토리
     * @return 새로운 아이템 데이터베이스
     */
    public static JsonFileItemDatabase newJsonDatabase(ExecutorService service, File file) {
        return new JsonFileItemDatabase(service, file);
    }

    /**
     * MySQL 기반 아이템 데이터베이스를 가져옵니다.
     *
     * @param database 데이터베이스
     * @param table    테이블 이름
     * @return 새로운 아이템 데이터베이스
     */
    public static MySQLItemDatabase newMySQLDatabase(MySQLDatabase database, String table) {
        return new MySQLItemDatabase(database, table);
    }

    /**
     * 1.12 마인크래프트 아이템 허브 인스턴스를 생성합니다.
     *
     * @param database 아이템 데이터베이스
     * @return 새로운 아이템 허브
     */
    public static MinecraftItemHub buildMinecraftHub(ItemDatabase database) {
        return new MinecraftItemHub(database);
    }

    public static MinecraftItemHub v1_12MinecraftHub(ItemDatabase database) {
        return new MinecraftItemHub(database);
    }

    /**
     * 1.16 마인크래프트 아이템 허브 인스턴스를 생성합니다.
     *
     * @param database 아이템 데이터베이스
     * @return 새로운 아이템 허브
     */
    public static MinecraftItemHub v1_16MinecraftHub(ItemDatabase database) {
        return new MinecraftItemHub(database, MinecraftItemFactory.v1_16());
    }
}
