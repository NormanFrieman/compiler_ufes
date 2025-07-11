package checker.utils;

public enum ArraySize {
    NO_ARRAY(-1),
    NO_EXPLICIT_SIZE(-2);

    public final int value;

    ArraySize(int value) {
        this.value = value;
    }
}
