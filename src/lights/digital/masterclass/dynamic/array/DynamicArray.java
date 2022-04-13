package lights.digital.masterclass.dynamic.array;

import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public interface DynamicArray<T> {

    /**
     * Add an element to the end of the array.
     *
     * @param newElement The element we want to add to our array
     * @throws IllegalArgumentException if {@code newElement} is null
     */
    void push(T newElement);

    /**
     * Delete the last added element in the array and return it.
     *
     * @return The last added element in the array or null if the array is empty
     */
    T pop();

    /**
     * Find the element on position index and return it.
     *
     * @param index The position of the wanted element
     * @return The element on position index
     * @throws IndexOutOfBoundsException if{@code index} is less than 0 or more than the array size
     */
    T get(int index);

    /**
     * @return The current size of the array
     */
    int size();

    /**
     * @return If the current array contains no elements return true, else false
     */
    boolean isEmpty();

    /**
     * Erases all elements from the array.
     */
    void clear();

    /**
     * @param element The element we are searching for in the array
     * @return Whether the element is in the array or not.
     */
    boolean contains(T element);

    /**
     * @param tCollection The collection of elements we are searching for in the array
     * @return Whether all the elements from {@code tCollection} are in the array or not
     */
    boolean containsAll(Collection<T> tCollection);

    /**
     * Remove an element on the last position in the array.
     */
    void remove();

    /**
     * Remove the element on position index in the array.
     *
     * @param index The position of the element we are removing
     * @throws IndexOutOfBoundsException if {@code index} is less than 0 or more than the array size
     */
    void remove(int index);

    /**
     * Enlarge the capacity of the array to {@code newSize}
     *
     * @param newSize The new capacity which should be ensured
     * @throws IllegalArgumentException if{@code newSize} is less than the current array capacity
     */
    void ensureCapacity(int newSize);

    /**
     * Decrease the capacity of the current array to the number of elements in it.
     */
    void trimToSize();

    /**
     * @return The capacity of the current array
     */
    int capacity();

    /**
     * Set the value on position {@code index} to {@code value}.
     *
     * @param index The position which should be upgraded
     * @param value The new value for position index
     * @return The old value on position index
     * @throws IllegalArgumentException  If {@code value} is null
     * @throws IndexOutOfBoundsException if {@code index} is less than 0 or more than the array size
     */
    T set(int index, T value);

    /**
     * Deletes an element on a certain index and returns it.
     *
     * @param index The position of the element we are trying to delete
     * @return The element we have deleted
     * @throws IndexOutOfBoundsException if {@code index} is less than 0 or more than the array size
     */
    T delete(int index);

    /**
     * @param n The number of elements that should be returned.
     * @return A mutable array of the first n elements
     */
    T[] takeN(int n);

    /**
     * @param n The number of element that should be removed
     * @return A mutable array of the first (size() - n ) elements
     */
    T[] dropN(int n);

    /**
     * @param elements The elements which should be added to the current array
     */
    void addAll(T... elements);

    /**
     * @return An immutable list copy of the array
     */
    List<T> copy();


    /**
     * @return A spliterator for an array of type T
     */
    Spliterator<T> spliterator();

    /**
     * @return A Stream of the element in the current array.
     */
    Stream<T> stream();
}
