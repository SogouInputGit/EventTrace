package com.sohu.inputmethod.foreign.base.deadlock;


import androidx.annotation.Nullable;

import java.util.List;
import java.util.Stack;

/**
 * @author dongjianye on 2020/6/11
 *
 * 检测潜在的死锁
 */
final class DeadLockChecker {

    private final static LockNodeGraph GRAPH = new LockNodeGraph();

    // 保存在每个线程里面的锁，lock的时候入栈，unlock的时候出栈，必须成对出现
    private final static ThreadLocal<Stack<LockNode>> STACK_THREAD_LOCAL = new ThreadLocal<Stack<LockNode>>() {
        @Nullable
        @Override
        protected Stack<LockNode> initialValue() {
            return new Stack<>();
        }
    };

    /**
     * owner.lock 后
     * @param owner 锁的对象
     */
    static void afterLock(Object owner, int lockLocationId) throws DeadLockException {

        final Stack<LockNode> nodeStack = STACK_THREAD_LOCAL.get();
        final LockNode node = GRAPH.getLockNode(owner);
        node.addLockLocation(lockLocationId);
        final List<LockNode> cyclePath = GRAPH.checkOrAddEdge(node, nodeStack);
        if (cyclePath != null && cyclePath.size() >= 2) {
            final Stack<LockNode> exceptionStack = new Stack<>();
            exceptionStack.addAll(nodeStack);
            exceptionStack.push(node);
            throw new DeadLockException(getCycleExceptionMessage(cyclePath, exceptionStack));
        }
        nodeStack.push(node);
    }

    static void afterUnlock(Object owner) throws DeadLockException {
        final Stack<LockNode> nodeStack = STACK_THREAD_LOCAL.get();
        final LockNode node = GRAPH.getLockNode(owner);
        if (nodeStack.peek() != node) {
            throw new DeadLockException("加锁与开锁没有成对出现");
        }
        nodeStack.pop();
    }

    static String getCycleExceptionMessage(final List<LockNode> cycleList, Stack<LockNode> stack) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n!!!!!!!!!发现潜在死锁!!!!!!!!!\n");

        sb.append("\n!!!!!!!!!死锁链上的所有锁!!!!!!!!!\n");

        for (LockNode node : cycleList) {
            sb.append("锁 " + node.mDescription + " 曾经获取过锁:\n\n");
            int i = 0;
            for (int locationId : node.mLocations) {
                sb.append(i).append("\t").append(LockLocationManager.getInstance().getLocation(locationId)).append("\n");
                i++;
            }
        }

        sb.append("!!!!!!!!!当前线程锁栈!!!!!!!!!\n");
        while (!stack.isEmpty()) {
            LockNode node = stack.pop();
            sb.append("锁 " + node.mDescription + " 曾经获取过锁:\n\n");
            int i = 0;
            for (int locationId : node.mLocations) {
                sb.append(i).append("\t").append(LockLocationManager.getInstance().getLocation(locationId)).append("\n");
                i++;
            }
        }

        return sb.toString();
    }
}