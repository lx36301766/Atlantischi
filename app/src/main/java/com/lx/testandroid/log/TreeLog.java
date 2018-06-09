package com.lx.testandroid.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

/**
 * Created by admin on 2017/11/19.
 */

public class TreeLog {

    public static final int V = Log.VERBOSE;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int W = Log.WARN;
    public static final int E = Log.ERROR;

    public static void d(String message, Object... args) {

    }

    public static void d(String tag, String message, Object... args) {

    }

    public static void d(Throwable throwable) {

    }

    public static void d(String tag, Throwable throwable) {

    }

    public static void d(Throwable throwable, String message, Object... args) {

    }

    public static void d(String tag, Throwable throwable, String message, Object... args) {

    }

    public static void plant(Tree... trees) {
        if (trees == null || trees.length == 0) {
            return;
        }
        synchronized(forestList) {
            Collections.addAll(forestList, trees);
            forestArray = forestList.toArray(new Tree[forestList.size()]);
        }
    }

    public static void uproot(Tree tree) {
        synchronized(forestList) {
            if (forestList.remove(tree)) {
                forestArray = forestList.toArray(new Tree[forestList.size()]);
            }
        }
    }

    private static final List<Tree> forestList = new ArrayList<>();
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static volatile Tree[] forestArray = TREE_ARRAY_EMPTY;

    public static class Tree {

    }

}
