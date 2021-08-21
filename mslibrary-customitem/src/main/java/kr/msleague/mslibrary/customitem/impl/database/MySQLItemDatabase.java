package kr.msleague.mslibrary.customitem.impl.database;

import kr.msleague.mslibrary.customitem.api.ItemDatabase;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;
import kr.msleague.mslibrary.customitem.impl.serializers.MappingSerializer;
import kr.msleague.mslibrary.customitem.impl.serializers.YamlSerializer;
import kr.msleague.mslibrary.database.impl.internal.MySQLDatabase;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class MySQLItemDatabase implements ItemDatabase {

    YamlSerializer yamlSerializer = new YamlSerializer();
    MappingSerializer remapper = new MappingSerializer();
    MySQLDatabase database;
    String table;

    public MySQLItemDatabase(MySQLDatabase database, String table) {
        this.database = database;
        this.table = table;
        database.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+table+
                    " ( " +
                    "`column_id` INT AUTO_INCREMENT UNIQUE, " +
                    "`item_id` INT, " +
                    "`key` VARCHAR(128), " +
                    "`value` VARCHAR(128), " +
                    "PRIMARY KEY(`item_id`, `key`)) " +
                    "charset=utf8mb4");
            ps.execute();
        });
    }

    @Override
    public Future<List<MSItemData>> loadAll() {
        return database.executeAsync(connection->{
            List<MSItemData> list = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT `item_id`, `key`, `value` FROM "+table);
            ResultSet rs = ps.executeQuery();
            HashMap<Integer, ItemNode> map = new HashMap<>();
            while (rs.next()){
                int id = rs.getInt(1);
                if(!map.containsKey(id)){
                    map.put(id, new HashItemNode(null, ""));
                }
                ItemNode node = map.get(id);
                node.setPrimitive(rs.getString(2), rs.getString(3));
            }
            for (Map.Entry<Integer, ItemNode> entry : map.entrySet()) {
                MSItemData mapped = new MSLItemData(entry.getValue());
                list.add(remapper.deserialize(mapped));
            }
            return list;
        });
    }

    @Override
    public Future<Boolean> newItem(int id, @Nonnull MSItemData item) {
        return database.executeAsync(connection->{
            PreparedStatement ps = connection.prepareStatement("SELECT `item_id` FROM "+table+" WHERE `item_id`=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return false;
            }else {
                insertItem(id, item, connection);
                return true;
            }
        });
    }

    @Override
    public Future<MSItemData> load(int itemID) throws IllegalStateException {
        return database.executeAsync(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT `item_id`, `key`, `value` FROM "+table+" WHERE `item_id`=?");
            ps.setInt(1, itemID);
            ResultSet rs = ps.executeQuery();
            ItemNode node = new HashItemNode(null, "");
            while (rs.next()){
                node.setPrimitive(rs.getString(2), rs.getString(3));
            }
            MSItemData mapped = new MSLItemData(node);
            return remapper.deserialize(mapped);
        });
    }

    @Override
    public Future<Void> insertItem(int itemID, @Nonnull MSItemData item) throws IllegalArgumentException {
        return database.executeAsync(connection -> {
            insertItem(itemID, item, connection);
            return null;
        });
    }

    private void insertItem(int itemID, @Nonnull MSItemData item, Connection connection) throws SQLException {
        MSItemData mapped = remapper.serialize(item);
        YamlConfiguration config = yamlSerializer.serialize(mapped);
        Map<String, String> map = new HashMap<>();
        for(String path : config.getKeys(true)){
            if(config.isBoolean(path)){
                map.put(path, Boolean.toString(config.getBoolean(path)));
            }else if(config.isDouble(path)){
                map.put(path, Double.toString(config.getDouble(path)));
            }else if(config.isInt(path)){
                map.put(path, Integer.toString(config.getInt(path)));
            }else if(config.isLong(path)){
                map.put(path, Long.toString(config.getLong(path)));
            }else if(config.isString(path)){
                map.put(path, config.getString(path));
            }
        }
        for(String key : map.keySet()){
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO "+table+" (`item_id`, `key`, `value`) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE `key`=?, `value`=?");
            ps2.setInt(1, itemID);
            ps2.setString(2, key);
            ps2.setString(3, map.get(key));
            ps2.setString(4, key);
            ps2.setString(5, map.get(key));
            ps2.execute();
        }
    }

    @Override
    public Future<Void> deleteItem(int itemID) {
        return database.executeAsync(connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM "+table+" WHERE `item_id`=?");
            ps.setInt(1, itemID);
            ps.execute();
            return null;
        });
    }

    @Override
    public Future<List<Integer>> search(String path, String value) {
        return database.executeAsync(connection -> {
            List<Integer> list = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT `item_id` FROM "+table+" WHERE `key`=? AND `value`=?");
            ps.setString(1, path);
            ps.setString(2, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            return list;
        });
    }

    @Override
    public Future<Void> modify(int itemID, @Nonnull String node, @Nullable String value) {
        return database.executeAsync(connection -> {
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO "+table+" (`item_id`, `key`, `value`) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE `key`=?, `value`=?");
            ps2.setInt(1, itemID);
            ps2.setString(2, node);
            ps2.setString(3, value);
            ps2.setString(4, node);
            ps2.setString(5, value);
            ps2.execute();
            return null;
        });
    }

    @Override
    public Future<Boolean> has(int itemID) {
        return database.executeAsync(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM "+table+" WHERE `item_id`=?");
            ps.setInt(1, itemID);
            return ps.executeQuery().next();
        });
    }

    @Override
    public Future<Integer> size() {
        return database.executeAsync(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS `cnt` FROM "+table+" WHERE `key`=?");
            ps.setString(1, "id");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0;
        });
    }
}
