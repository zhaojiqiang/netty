package com.netty;
public class Node {
    public int value;
    public Node next;
	static Node head;

    public Node(int data) {
        this.value = data;
    }
}