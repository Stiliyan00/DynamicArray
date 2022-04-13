package lights.digital.masterclass.dynamic.array;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DynamicArrayImplementation<T> implements DynamicArray<T> {

    private final Class<T> clazz;

    private T[] dynamicArray;
    private int capacity;
    private int size;

    private static double DOUBLE_CAPACITY_COEFFICIENT = 2;

    private void resize() {
        if (DOUBLE_CAPACITY_COEFFICIENT > 1.6 && this.size () > 1000) {
            DOUBLE_CAPACITY_COEFFICIENT -= 0.1;
        }

        T[] newDynamicArrayWithDoubleCapacity =
                (T[]) Array.newInstance(clazz, (int) (DOUBLE_CAPACITY_COEFFICIENT * capacity) + 1);

        if (this.size >= 0) {
            System.arraycopy(this.dynamicArray, 0,
                    newDynamicArrayWithDoubleCapacity, 0, this.size);
        }

        this.capacity *= DOUBLE_CAPACITY_COEFFICIENT;
        this.dynamicArray = newDynamicArrayWithDoubleCapacity;
    }

    public DynamicArrayImplementation(Class<T> clazz) {
        this(clazz, 1);
    }

    public DynamicArrayImplementation(Class<T> clazz, int capacity) {
        this.clazz = clazz;
        this.dynamicArray = (T[]) Array.newInstance(clazz, capacity);
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public void push(T newElement) {
        if (newElement == null) {
            throw new IllegalArgumentException("The value of argument newElement, " +
                    "in method push, cannot be null");
        }

        if (this.size >= this.capacity) {
            resize();
        }

        this.dynamicArray[size++] = newElement;
    }

    @Override
    public T pop() {
        if (this.size == 0) {
            return null;
        }

        T lastElementInArray = dynamicArray[size - 1];

        this.dynamicArray[size - 1] = null;
        size--;

        return lastElementInArray;
    }


    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The argument index in method get" +
                    " cannot be less than 0 or more than the array size!");
        }

        return dynamicArray[index];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.dynamicArray[i] = null;
        }
        this.size = 0;
    }


    @Override
    public boolean contains(T element) {
        return Arrays.asList(dynamicArray).contains(element);
    }

    @Override
    public boolean containsAll(Collection<T> tCollection) {

        Object[] tArray = tCollection.toArray();

        for (Object o : tArray) {
            if (!contains((T) o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void remove() {
        delete(this.size - 1);
    }

    @Override
    public void remove(int index) {
        delete(index);
    }

    @Override
    public void ensureCapacity(int newSize) {
        if (newSize < this.capacity) {
            throw new IllegalArgumentException("The value of argument newSize " +
                    "cannot be less than the current capacity of the array!");
        }


        T[] localArrayCopyOfDynamicArray = (T[]) Array.newInstance(clazz, newSize);
        if (this.size >= 0) {
            System.arraycopy(this.dynamicArray, 0,
                    localArrayCopyOfDynamicArray, 0, this.size);
        }
        this.dynamicArray = localArrayCopyOfDynamicArray;
        this.capacity = newSize;
    }

    @Override
    public void trimToSize() {
        this.dynamicArray = takeN(size);
        this.capacity = this.size;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public T set(int index, T value) {
        if (value == null) {
            throw new IllegalArgumentException("The value of argument value " +
                    "in method set cannot be null!");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The value of argument index in " +
                    "method set cannot be less than 0 and more than the array size!");
        }

        T oldValueOnPositionIndex = dynamicArray[index];
        dynamicArray[index] = value;

        return oldValueOnPositionIndex;
    }


    @Override
    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The value of argument index cannot be" +
                    " less than 0 and more than array length!");
        }

        T result = null;

        for (int i = 0; i < size - 1; i++) {
            if (i == index) {
                result = dynamicArray[i];
            } else if (i > index) {
                dynamicArray[i] = dynamicArray[i + 1];
            }
        }
        dynamicArray[size--] = null;

        return result;
    }

    @Override
    public T[] takeN(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The value of argument n in method" +
                    " takeN cannot be a negative number!");
        }

        if (size == 0) {
            return dynamicArray;
        }

        if (n > size) {
            n = size;
        }

        T[] localArrayCopyOfFirstNElementsFromDynamicArray =
                (T[]) Array.newInstance(clazz, n);
        if (n >= 0) {
            System.arraycopy(dynamicArray, 0,
                    localArrayCopyOfFirstNElementsFromDynamicArray, 0, n);
        }
        return localArrayCopyOfFirstNElementsFromDynamicArray;
    }

    @Override
    public T[] dropN(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The value of argument n in method " +
                    "dropN cannot be a negative number!");
        }

        if (n > size) {
            return (T[]) Array.newInstance(clazz, 0);
        }


        T[] localArrayCopyOfFirstSizeDivNElementsFromDynamicArray =
                (T[]) Array.newInstance(clazz, size - n);

        System.arraycopy(dynamicArray, 0,
                localArrayCopyOfFirstSizeDivNElementsFromDynamicArray, 0, size - n);

        return localArrayCopyOfFirstSizeDivNElementsFromDynamicArray;
    }

    @SafeVarargs
    @Override
    public final void addAll(T... elements) {
        if (elements == null) {
            throw new IllegalArgumentException("The value of argument elements in " +
                    "method addAll cannot be null!");
        }

        for (T element : elements) {
            push(element);
        }
    }

    @Override
    public List<T> copy() {
        return Arrays.stream(takeN(size)).toList();
    }

    @Override
    public String toString() {
        return Arrays.toString(dynamicArray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicArrayImplementation<?> that = (DynamicArrayImplementation<?>) o;
        return capacity == that.capacity && size == that.size && Objects.equals(clazz, that.clazz) && Arrays.equals(dynamicArray, that.dynamicArray);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(clazz, capacity, size);
        result = 31 * result + Arrays.hashCode(dynamicArray);
        return result;
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(takeN(size), 0);
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}