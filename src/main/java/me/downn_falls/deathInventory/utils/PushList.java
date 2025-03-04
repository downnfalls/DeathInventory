package me.downn_falls.deathInventory.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PushList<T> {
    private final List<T> list;

    public PushList(int size) {
        list = new ArrayList<>(Collections.nCopies(size, null)); // Initialize with nulls
    }

    public void add(T element) {
        int nullIndex = list.indexOf(null);
        if (nullIndex != -1) {
            list.set(nullIndex, element); // Fill first null
        } else {
            list.remove(list.size() - 1); // Remove last
            list.add(0, element); // Add at front
        }
    }

    public void remove(T element) {
        int index = list.indexOf(element);
        if (index != -1) {
            list.set(index, null); // Replace with null
        }
    }

    public void remove(int index) {
        list.set(index, null);
    }

    public List<T> getList() {
        return list;
    }

    public static <T> PushList<T> fromList(List<T> inputList) {
        PushList<T> pushList = new PushList<>(inputList.size()); // Create PushList with same size
        for (T element : inputList) {
            if (element != null) {
                pushList.add(element); // Add elements while preserving order
            }
        }
        return pushList;
    }
}
