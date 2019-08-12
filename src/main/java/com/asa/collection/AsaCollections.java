package com.asa.collection;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map 数组 队列 栈
 * Created by AsaFan on 2018-09-04.
 */
public class AsaCollections {

    public void arrayList() {
        List arrayList = new ArrayList();
        List linkedList = new LinkedList();
        List vector = new Vector();
        List stack = new Stack();
        Set hashSet = new HashSet();
        SortedSet treeSet = new TreeSet();
        Collections.synchronizedList(arrayList);

        Map hashMap = new HashMap();
        Map treeMap = new TreeMap();
        Map table = new Hashtable();
        Map concurrentHashMap = new ConcurrentHashMap();

        Queue queue = new ArrayBlockingQueue(2);
    }

    public void ArrayBlockQueue() {
        Queue queue = new ArrayBlockingQueue(2);
    }

    public static void main(String[] args) {
        String key = "Asas";
        int h = key.hashCode();
        System.out.println(h);
        System.out.println((h) ^ (h >>> 16));
        System.out.println(1 << 30);
        System.out.println(111 & h);
        System.out.println(112 & h);
    }
}
