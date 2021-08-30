package kr.msleague.mslibrary.customitem;

public class MySQLTest {

    /*
    public static MySQLItemDatabase database;
    public static MSItemData data;

    @BeforeClass
    public static void prepare() throws IOException {
        MySQLDatabase database = new MySQLDatabase(Executors.newSingleThreadExecutor());
        DatabaseConfig conf = MSLDatabases.HIKARI.copy();
        conf.setAddress("localhost");
        conf.setPort(3306);
        conf.setDatabase("test");
        conf.setUser("root");
        conf.setPassword("test");
        database.connect(conf);
        MySQLTest.database = new MySQLItemDatabase(database, "msl_item_test");
        YamlFileItemDatabase.YamlFileSerializer serializer = new YamlFileItemDatabase.YamlFileSerializer();
        YamlConfiguration yaml = serializer.read(new File("src/test/resources/item.yml"));
        YamlSerializer yamls = new YamlSerializer();
        data = yamls.deserialize(yaml);
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        database.deleteItem(1).get();
        System.out.println(database.newItem(1, data).get());
        MSItemData data = database.load(1).get();
        for (ItemElement content : data.getNodes().get("minecraft.lore").asArray().contents()) {
            System.out.println(content.asValue().getAsString());
        }
        database.modify(1, "가나다", "라마바사");
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        System.out.println(database.search("minecraft.lore.$3", "가나다").get().size());
    }
     */
}
