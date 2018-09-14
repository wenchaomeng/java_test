package javabasic.collection.agrona.mymap;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 10, 2018
 */
public interface Node {
    long getKey();

    Node getNext();

    void setNext(Node next);
}
