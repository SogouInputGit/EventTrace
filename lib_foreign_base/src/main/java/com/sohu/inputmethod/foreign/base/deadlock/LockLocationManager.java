package com.sohu.inputmethod.foreign.base.deadlock;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongjianye on 2020/6/12
 * lock发生的时候，记录位置
 *
 */
final class LockLocationManager {

    private static final int STACK_DEPTH = 5;

    private static final LockLocationManager INSTANCE = new LockLocationManager();
    private final ArrayList<LockStackInfo> locations = new ArrayList<>(10_000);
    private final Map<LockStackInfo, Integer> locationIds = new HashMap<>();

    public static LockLocationManager getInstance() {
        return INSTANCE;
    }

    private LockLocationManager() {
    }


    public synchronized int getLocationId(StackTraceElement[] stackTraceElements) {
        LockStackInfo location = new LockStackInfo(stackTraceElements);
        Integer id = locationIds.get(location);
        if (id != null) {
            return id;
        }
        id = locations.size();
        locations.add(location);
        locationIds.put(location, id);
        return id;
    }

    public synchronized LockStackInfo getLocation(int id) {
        return locations.get(id);
    }

    static class LockStackInfo {
        final StackTraceElement[] stacks = new StackTraceElement[STACK_DEPTH];

        LockStackInfo(StackTraceElement[] curStacks) {
            if (curStacks != null) {
                for (int i = 0; i < Math.min(curStacks.length, stacks.length) - 1; ++i) {
                    final String className = curStacks[i + 1].getClassName().replaceAll("/", ".");
                    final String methodName = curStacks[i + 1].getMethodName();
                    final String fileName = curStacks[i + 1].getFileName();
                    final int line = curStacks[i + 1].getLineNumber();

                    stacks[i] = new StackTraceElement(className, methodName, fileName, line);
                }
            }
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(stacks);
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LockStackInfo)) {
                return false;
            }

            return Arrays.equals(stacks, ((LockStackInfo) obj).stacks);
        }

        @NonNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < stacks.length; ++i) {
                StackTraceElement element = stacks[i];
                if (element != null) {
                    if (i != 0) {
                        sb.append("\t");
                    }
                    sb.append(element.toString()).append("\n");

                }
            }

            return sb.toString();
        }
    }
}