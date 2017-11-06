package com.lx.testandroid.log;

/**
 * Created on 06/11/2017.
 *
 * @author lx
 */

public class TLog {


    /** Log a verbose message with optional format args. */
    public static void v(String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    /** Log a verbose exception and a message with optional format args. */
    public static void v(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.v(t, message, args);
    }

    /** Log a verbose exception. */
    public static void v(Throwable t) {
        TREE_OF_SOULS.v(t);
    }

    /** Log a debug message with optional format args. */
    public static void d(String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public static void d(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.d(t, message, args);
    }

    /** Log a debug exception. */
    public static void d(Throwable t) {
        TREE_OF_SOULS.d(t);
    }

    /** Log an info message with optional format args. */
    public static void i(String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public static void i(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.i(t, message, args);
    }

    /** Log an info exception. */
    public static void i(Throwable t) {
        TREE_OF_SOULS.i(t);
    }

    /** Log a warning message with optional format args. */
    public static void w(String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public static void w(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.w(t, message, args);
    }

    /** Log a warning exception. */
    public static void w(Throwable t) {
        TREE_OF_SOULS.w(t);
    }

    /** Log an error message with optional format args. */
    public static void e(String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public static void e(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.e(t, message, args);
    }

    /** Log an error exception. */
    public static void e(Throwable t) {
        TREE_OF_SOULS.e(t);
    }

    /** Log an assert message with optional format args. */
    public static void wtf(String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    /** Log an assert exception and a message with optional format args. */
    public static void wtf(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.wtf(t, message, args);
    }

    /** Log an assert exception. */
    public static void wtf(Throwable t) {
        TREE_OF_SOULS.wtf(t);
    }


}
