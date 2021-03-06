package kr.msleague.mslibrary.node.api;

/**
 * 아이템 엘리먼트
 * 아이템의 실제 저장된 데이터를 의미합니다.
 *
 * @author Arkarang
 * @since 1.0
 */
public interface MSNodeValue extends MSNodeElement {

    /**
     * boolean 값을 가져옵니다.
     *
     * @return boolean 값
     */
    boolean getAsBoolean();

    /**
     * byte 값을 가져옵니다.
     *
     * @return byte 값
     */
    byte getAsByte();

    /**
     * short 값을 가져옵니다.
     *
     * @return path 값
     */
    short getAsShort();

    /**
     * int 값을 가져옵니다.
     *
     * @return int 값
     */
    int getAsInt();

    /**
     * float 값을 가져옵니다.
     *
     * @return float 값
     */
    float getAsFloat();

    /**
     * double 값을 가져옵니다.
     *
     * @return double 값
     */
    double getAsDouble();

    /**
     * long 값을 가져옵니다.
     *
     * @return long 값
     */
    long getAsLong();

    /**
     * String 값을 가져옵니다.
     *
     * @return double 값
     */
    String getAsString();

    /**
     * Number 값을 가져옵니다.
     *
     * @return Number 값
     */
    Number getAsNumber();

    /**
     * byte 값을 설정합니다.
     *
     * @param b 값
     */
    void set(byte b);

    /**
     * short 값을 설정합니다.
     *
     * @param s 값
     */
    void set(short s);

    /**
     * int 값을 설정합니다.
     *
     * @param i 값
     */
    void set(int i);

    /**
     * float 값을 설정합니다.
     *
     * @param f 값
     */
    void set(float f);

    /**
     * double 값을 설정합니다.
     *
     * @param b 값
     */
    void set(double b);

    /**
     * long 값을 설정합니다.
     *
     * @param l 값
     */
    void set(long l);

    /**
     * 값이 Number 인지 확인합니다.
     *
     * @return Number 인지 여부
     */
    boolean isNumber();

    /**
     * 값이 Boolean 인지 확인합니다.
     *
     * @return Boolean 인지 여부
     */
    boolean isBoolean();

    /**
     * 값이 String 인지 확인합니다.
     *
     * @return String 인지 여부
     */
    boolean isString();

}
