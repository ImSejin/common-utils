package io.github.imsejin.common.constant.interfaces;

/**
 * This is for that enum provides {@code key()} and {@code value()}.
 */
public interface KeyValue {

    /**
     * Gets name of the enum.
     *
     * <p> It replaces {@link Enum#name()} with itself.
     *
     * @return enum's name
     */
    String key();

    /**
     * Gets value of the enum.
     *
     * <p> This should be implemented to return the first parameter
     * of enum's constructor.
     *
     * @return the first parameter of enum's constructor
     */
    String value();

}
