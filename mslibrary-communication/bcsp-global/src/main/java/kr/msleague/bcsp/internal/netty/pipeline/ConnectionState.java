package kr.msleague.bcsp.internal.netty.pipeline;

public enum ConnectionState {
    IDLE(0), HANDSHAKING(1), CONNECTED(2);
    private int id;

    ConnectionState(int n) {
        this.id = n;
    }

    public static ConnectionState getById(int id) {
        for (ConnectionState st : values())
            if (id == st.id)
                return st;
        return null;
    }
}
