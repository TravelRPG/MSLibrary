package kr.msleague.msgui.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface MSGuiPage {
    /**
     * 페이지 우선순위.
     * 숫자가 낮을 수록 앞 페이지입니다.
     *
     * @return 우선순위
     */
    int pagePriority();
}
