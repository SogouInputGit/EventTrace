package com.sohu.inputmethod.foreign.base.deadlock;

import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.ArrayMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author dongjianye on 2020/6/12
 */
@RunWith(AndroidJUnit4.class)
public class LockNodeGraphTest {

    @Test
    public void testBfs() {

        final LockNode node1 = new LockNode(new Object());
        final LockNode node2 = new LockNode(new Object());
        final LockNode node3 = new LockNode(new Object());
        final LockNode node4 = new LockNode(new Object());

        node2.mChildren.add(node1);
        node4.mChildren.add(node2);
        node3.mChildren.add(node1);
        node4.mChildren.add(node3);

        final ArrayDeque<LockNode> deque = new ArrayDeque<>();
        final Map<LockNode, LockNode> path = new ArrayMap<>();
        final int visited = 10;
        List<LockNode> result = LockNodeGraph.getCyclePathBfs(deque, path, visited, node4, node2);
        assertEquals(result.size(), 2);

        result = LockNodeGraph.getCyclePathBfs(deque, path, visited, node2, node2);
        assertEquals(result.size(), 1);

    }

    @Test
    public void testDfs() {
        final LockNode node1 = new LockNode(new Object());
        final LockNode node2 = new LockNode(new Object());
        final LockNode node3 = new LockNode(new Object());
        final LockNode node4 = new LockNode(new Object());

        node2.mChildren.add(node1);
        node3.mChildren.add(node1);
        node4.mChildren.add(node3);
        node4.mChildren.add(node2);
        node3.mChildren.add(node2);

        final ArrayList<LockNode> cycleList = new ArrayList<>();
        final int visited = 10;

        List<LockNode> result = LockNodeGraph.getCyclePathDfs(node4, node1, visited, cycleList);
        assertEquals(result.size(), 3);

        cycleList.clear();
        result = LockNodeGraph.getCyclePathDfs(node1, node1, visited, cycleList);
        assertEquals(result.size(), 1);

        cycleList.clear();
        result = LockNodeGraph.getCyclePathDfs(node3, node2, visited, cycleList);
        assertEquals(result.size(), 2);

        cycleList.clear();
        result = LockNodeGraph.getCyclePathDfs(node2, node3, visited, cycleList);
        assertEquals(result, null);
    }
}