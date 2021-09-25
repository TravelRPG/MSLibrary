package kr.msleague.msgui.example;

//import kr.msleague.msgui.api.annotations.MSGuiPage;
//import kr.msleague.msgui.gui.MSGui;
//import kr.msleague.msgui.gui.button.builder.MSGuiButtonBuilder;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
///**
// * Java GUI 예제
// */
//public class ExampleJavaGUI extends MSGui<Player> {
//    public ExampleJavaGUI(@NotNull Player who) {
//        //size 는 9의 배수로 넣어야 합니다.
//        super(who, 27, "테스트 GUI");
//    }
//
//    @Override
//    public void init() {
//        //Player 를 넣으면 해당 플레이어의 뚝배기가 나옵니다.
//        MSGuiButtonBuilder headButton = getPlayer() == null ? new MSGuiButtonBuilder(Material.SKULL, 2) : new MSGuiButtonBuilder(getPlayer());
//        //MSGuiButtonBuilder(Material, integer) 를 넣으면 데이터 값이 적용된 아이템이 나옵니다.
//        MSGuiButtonBuilder redWool = new MSGuiButtonBuilder(Material.WOOL, 14);
//
//        //메서드 체인, 람다 지원
//        headButton.setCancellable(true).setCleanable(false).setAction((inventoryClickEvent)->{
//            Player damnFuckingPlayer = (Player)inventoryClickEvent.getWhoClicked();
//            damnFuckingPlayer.sendMessage("모든 빨간 양털을 날립니다.");
//            this.refresh();
//        });
//
//        // refresh 메서드 호출 시, 빨간 양털은 초기화 됩니다. false 일 경우 초기화 안함.
//        redWool.setCleanable(true);
//        headButton.build().setSlot(this, 0);
//        int[] array = new int[26];
//        for(int i = 0; i < getSize(); i++){
//            array[i] = i+1;
//        }
//        redWool.build().setSlot(this, array);
//    }
//
//    /**
//     * 페이징 기능. pagePriority 가 낮을 수록 앞선 페이지.
//     */
//    @MSGuiPage(pagePriority = 1)
//    public void firstPage(){
//        //init()과 동일하게 구현하면 됩니다.
//    }
//
//    /**
//     * 페이징 기능. pagePriority 가 낮을 수록 앞선 페이지.
//     */
//    @MSGuiPage(pagePriority = 2)
//    public void secondPage(){
//        //init()과 동일하게 구현하면 됩니다.
//    }
//}
