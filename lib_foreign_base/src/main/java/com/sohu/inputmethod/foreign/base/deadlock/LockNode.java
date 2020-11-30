package com.sohu.inputmethod.foreign.base.deadlock;

import androidx.collection.ArraySet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dongjianye on 2020/6/11
 */
class LockNode {
    static final int INITIAL_ORDER = Integer.MAX_VALUE;

    final String mDescription;
    final Set<LockNode> mChildren;
    private int order = INITIAL_ORDER;
    private int visited;    //给BFS查找回路用的，减少点内存
    final ArraySet<Integer> mLocations = new ArraySet<>();

    LockNode(Object object) {
        mDescription = object.getClass().getSimpleName() + "@" + Integer.toString(System.identityHashCode(object), 16);
        mChildren = new HashSet<>();
    }

    void setOrder(int order) {
        this.order = order;
    }

    int getOrder() {
        return order;
    }

    boolean isVisited(int visited) {
        return this.visited == visited;
    }

    boolean setVisited(int visited) {
        if (this.visited != visited) {
            this.visited = visited;
            return true;
        } else {
            return false;
        }
    }

    void addLockLocation(int id) {
        mLocations.add(id);
    }
}