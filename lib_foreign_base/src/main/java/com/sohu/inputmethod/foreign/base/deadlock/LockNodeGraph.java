package com.sohu.inputmethod.foreign.base.deadlock;

import android.util.Log;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dongjianye on 2020/6/11
 *
 * 将每个结点组成一个有像图，通过查找有向图中是否存在环来判断是否有潜在的环，一旦找到，则直接崩溃，并且显示出当前环的位置
 * 每次添加一个a -> b，都先检测 b -> a有没有回路，如果有，把这个环打印出来，大部分情况是精确的反应出死锁的位置
 */
final class LockNodeGraph {
    private final static String TAG = "LockNodeGraph";

    final boolean USE_BFS = true;

    private final HashMap<Object, LockNode> mNodes;

    private final ReentrantLock mLock = new ReentrantLock();

    private final ThreadLocal<ArrayList<LockNode>> sNewEdgesHolder = new ThreadLocal<ArrayList<LockNode>>() {
        @Nullable
        @Override
        protected ArrayList<LockNode> initialValue() {
            return new ArrayList<>();
        }
    };

    @GuardedBy("mLock")
    private final ArrayDeque<LockNode> tempBfsDeque = new ArrayDeque<>();
    @GuardedBy("mLock")
    private final ArrayMap<LockNode, LockNode> tempBfsPath = new ArrayMap<>();
    @GuardedBy("mLock")
    private final ArrayList<LockNode> tempDfsPath = new ArrayList<>();
    @GuardedBy("mLock")
    private int tempVisited = 1;

    @GuardedBy("mLock")
    private ConcurrentLinkedQueue<LockNode> tempNewLockNodes = new ConcurrentLinkedQueue<>();
    @GuardedBy("mLock")
    private final ArrayList<LockNode> orderdNewLockNodes = new ArrayList<>();

    LockNodeGraph() {
        mNodes = new HashMap<>();
    }
    /**
     * 生成结点
     * @param owner
     * @return
     */
    LockNode getLockNode(Object owner) {
        LockNode node = mNodes.get(owner);
        if (node != null) {
            return node;
        }

        node = createLockNode(owner);
        mNodes.put(owner, node);

        return node;
    }

    private LockNode createLockNode(Object owner) {
        final LockNode node = new LockNode(owner);
        tempNewLockNodes.add(node);
        return node;
    }

    List<LockNode> checkOrAddEdge(final LockNode from, final Stack<LockNode> nodeStack) {
        // 线程如果刚进入锁的状态，则不需要检查
        if (nodeStack.size() <= 0) {
            return null;
        }

        // 自身直接，同时去除重入锁，这时是不用管的
        for (LockNode to : nodeStack) {
            if (to == from) {
                return null;
            }
        }

        final LockNode to = nodeStack.peek();

        final List<LockNode> cycleList;

        mLock.lock();
        try {

            tempVisited++;
            if (USE_BFS) {
                tempBfsDeque.clear();
                tempBfsPath.clear();
                cycleList = getCyclePathBfs(tempBfsDeque, tempBfsPath, tempVisited, to, from);
            } else {
                cycleList = getCyclePathDfs(to, from, tempVisited, tempDfsPath);
            }

            if (cycleList == null) {
                from.mChildren.add(to);
                Log.d(TAG, String.format("Add new Edge : %s - > %s  thread is %s", from, to, Thread.currentThread().getName()));
            }
        } finally {
            mLock.unlock();
        }

        return cycleList;
    }

    @GuardedBy("mLock")
    static List<LockNode> getCyclePathBfs(final Deque<LockNode> deque,
                                           final Map<LockNode, LockNode> path,
                                           final int newVisited,
                                           final LockNode from, final LockNode to) {
        if (from == to) {
            ArrayList<LockNode> result = new ArrayList<>();
            result.add(from);
            return result;
        }

        deque.offer(from);
        from.setVisited(newVisited);
        while (!deque.isEmpty()) {
            LockNode topNode = deque.poll();
            for (LockNode child : topNode.mChildren) {

                if (child.setVisited(newVisited)) {
                    deque.offer(child);
                    path.put(child, topNode);
                }

                if (child == to) {
                    List<LockNode> cyclePath = new ArrayList<>();
                    cyclePath.add(to);
                    LockNode cur = to;
                    while (path.get(cur) != null) {
                        cur = path.get(cur);
                        cyclePath.add(cur);
                    }
                    Collections.reverse(cyclePath);
                    return cyclePath;
                }
            }
        }
        return null;
    }

    @GuardedBy("mLock")
    static List<LockNode> getCyclePathDfs(final LockNode from, final LockNode to, final int visited,
                                   final List<LockNode> cycleList) {
        if (from == to) {
            final List<LockNode> result = new ArrayList<>();
            result.addAll(cycleList);
            result.add(to);
            return result;
        }

        List<LockNode> result = null;
        cycleList.add(from);
        from.setVisited(visited);
        for (LockNode child : from.mChildren) {
            if (!child.isVisited(visited)) {
                result = getCyclePathDfs(child, to, visited, cycleList);
                if (result != null) {
                    break;
                }
            }
        }

        cycleList.remove(cycleList.size() - 1).setVisited(0);

        return result;
    }

    @Deprecated
    void addLockEdge(final LockNode from, final Stack<LockNode> nodeStack) {
        // 线程如果刚进入锁的状态，则不需要检查
        if (nodeStack.size() <= 0) {
            return;
        }

        // 自身直接，同时去除重入锁，这时是不用管的
        for (LockNode to : nodeStack) {
            if (to == from) {
                return;
            }
        }

        mLock.lock();

        final ArrayList<LockNode> newEdges = sNewEdgesHolder.get();
        newEdges.clear();

        // 添加从from到栈顶的edge
        try {
            // 每次准备增加edge的时候，需要初始化未分配的order结点
            for (LockNode to : nodeStack) {
                // 如果order还未分配，需要重新分配
                if (to.getOrder() == LockNode.INITIAL_ORDER) {
                    flushNewLockNodeBuffer();
                }

                if (!from.mChildren.contains(to)) {
                    newEdges.add(to);
                }
            }

            if (newEdges.size() <= 0) {
                return;
            }

            // 找到最大顺序Order结点，
            LockNode maxOrderNode = null;
            for (int i = 0; i < newEdges.size(); i++) {
                LockNode to = newEdges.get(i);
                if (maxOrderNode == null || to.getOrder() > maxOrderNode.getOrder()) {
                    maxOrderNode = to;
                }
            }

            if (from.getOrder() > maxOrderNode.getOrder()) {
                for (LockNode to : nodeStack) {
                    from.mChildren.add(to);
                }
                newEdges.clear();
                return;
            }

            final List<LockNode> cyclePath = null;
            if (cyclePath != null) {
                return;
            }

            // 没有环路径，需要更新每个结点的order，因为order已经改变了，此时orderdNewLockNodes已经保存了所有顺序，
            // [a fromNode b maxVisitedC d maxOrderNode e f g ] - > [a maxVisitedC maxOrderNode fromNode b d e f g]，这样才能保证顺序是对的
            final int maxOrder = maxOrderNode.getOrder();
            final int fromOrder = from.getOrder();

            // fromOrder < maxOrder，需要调整



            for (int i = 0; i < newEdges.size(); i++) {
                LockNode to = newEdges.get(i);
                from.mChildren.add(to);
            }

        } finally {
            mLock.unlock();
        }
    }

    @Deprecated
    private void flushNewLockNodeBuffer() {
        LockNode lockNode;
        while ((lockNode = tempNewLockNodes.poll()) != null) {
            lockNode.setOrder(orderdNewLockNodes.size());
            orderdNewLockNodes.add(lockNode);
        }
    }
}