package lights.digital.masterclass.dynamic.array;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    private final DynamicArray<Integer> dynamicArray = new DynamicArrayImplementation<>(Integer.class);

    @BeforeEach
    void setUp() {
        dynamicArray.push(1);
        dynamicArray.push(2);
        dynamicArray.push(3);
    }

    @AfterEach
    void tearDown() {
        dynamicArray.clear();
    }

    @Nested
    @DisplayName("Tests for method push()")
    public class PushTest {
        @Test
        void testPushWithNull() {
            assertThrows(IllegalArgumentException.class,
                    () -> dynamicArray.push(null),
                    "The method push should throw an IllegalArgumentException " +
                            "if the value of newElement is null!");
        }

        @Test
        void testPushWithSingleValidValue() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            localDynamicArrayTest.push(1);

            assertEquals(localDynamicArrayTest.size(), 1);
            assertTrue(localDynamicArrayTest.contains(1));
        }

        @Test
        void testPushWithMultipleValidValues() {
            List<Integer> copyListOfDynamicArray = new LinkedList<>();

            copyListOfDynamicArray.add(1);
            copyListOfDynamicArray.add(2);
            copyListOfDynamicArray.add(3);

            assertEquals(dynamicArray.size(), copyListOfDynamicArray.size());
            assertTrue(dynamicArray.containsAll(copyListOfDynamicArray));
        }
    }


    @Nested
    @DisplayName("Tests for method pop()")
    public class PopTest {

        @Test
        void testPopWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertNull(localDynamicArrayTest.pop());
        }

        @Test
        void testPopWithNonEmptyArray() {
            Integer lastElementFromArrayAfterPop = dynamicArray.pop();

            assertEquals(dynamicArray.size(), 2);
            assertEquals(lastElementFromArrayAfterPop, 3);
        }

    }


    @Nested
    @DisplayName("Tests for method get()")
    public class GetTest {

        @Test
        void testGetWithNegativeInputValue() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.get(-1),
                    "The method get should throw an IllegalArgumentException for " +
                            "index value being a negative number!");
        }

        @Test
        void testGetWithNMoreThanArrayLength() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.get(10),
                    "The method get should throw an IllegalArgumentException for " +
                            "index being more than the array length!");
        }

        @Test
        void testGetWithValidIndexValue() {

            assertEquals(dynamicArray.get(0), 1);
            assertEquals(dynamicArray.get(1), 2);
        }
    }


    @Nested
    @DisplayName("Tests for method size()")
    public class SizeTest {

        @Test
        void testSizeWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertEquals(localDynamicArrayTest.size(), 0);
        }

        @Test
        void testSizeWithNonEmptyArray() {

            dynamicArray.push(4);
            dynamicArray.push(5);

            assertEquals(dynamicArray.size(), 5);
            dynamicArray.pop();
            dynamicArray.pop();

            assertEquals(dynamicArray.size(), 3);
            dynamicArray.clear();

            assertEquals(dynamicArray.size(), 0);
        }
    }


    @Nested
    @DisplayName("Tests for method isEmpty()")
    public class IsEmptyTest {

        @Test
        void testIsEmptyWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertTrue(localDynamicArrayTest.isEmpty());
        }

        @Test
        void testIsEmptyWithNonEmptyArray() {
            assertFalse(dynamicArray.isEmpty());
        }
    }

    @Nested
    @DisplayName("Tests for method clear()")
    public class ClearTest {
        @Test
        void testClearWithNonEmptyArray() {

            assertEquals(dynamicArray.size(), 3);

            dynamicArray.clear();
            assertEquals(dynamicArray.size(), 0);
        }
    }

    @Nested
    @DisplayName("Tests for method takeN()")
    public class TakeNTest {

        @Test
        void testTakeNWithNegativeValue() {
            assertThrows(IllegalArgumentException.class,
                    () -> dynamicArray.takeN(-1));
        }

        @Test
        void testTakeNWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertEquals(localDynamicArrayTest.takeN(3).length, 1);
            assertNull(localDynamicArrayTest.takeN(3)[0]);
        }

        @Test
        void testTakeNWithZero() {
            Integer[] mutableArrayCopyOfFirstNElements = dynamicArray.takeN(0);

            assertEquals(mutableArrayCopyOfFirstNElements.length, 0);
        }

        @Test
        void testTakeNWithMoreThanArrayLength() {
            Integer[] mutableArrayCopyOfFirstNElements = dynamicArray.takeN(5);


            assertEquals(mutableArrayCopyOfFirstNElements.length, dynamicArray.size());
            assertTrue(dynamicArray.containsAll(Arrays.stream(mutableArrayCopyOfFirstNElements).collect(Collectors.toList())));
        }

        @Test
        void testTakeNWithLessThanArrayLength() {
            Integer[] mutableArrayCopyOfFirstNElements = dynamicArray.takeN(2);

            assertEquals(mutableArrayCopyOfFirstNElements[0], 1);
            assertEquals(mutableArrayCopyOfFirstNElements[1], 2);
        }
    }


    @Nested
    @DisplayName("Tests for method dropN()")
    public class DropNTest {

        @Test
        void testDropNWithNegativeValue() {
            assertThrows(IllegalArgumentException.class,
                    () -> dynamicArray.dropN(-1),
                    "The method dropN should throw an IllegalArgumentException" +
                            " if n is a negative number!");
        }

        @Test
        void testDropNWithMoreThanArrayLength() {
            Integer[] mutableArrayCopyOfFirstSizeDivNElements = dynamicArray.dropN(10);

            assertEquals(mutableArrayCopyOfFirstSizeDivNElements.length, 0);
        }

        @Test
        void testDropNWithLessThanArrayLength() {
            Integer[] mutableArrayCopyOfFirstSizeDivNElements = dynamicArray.dropN(2);

            assertEquals(mutableArrayCopyOfFirstSizeDivNElements.length, 1);
            assertEquals(mutableArrayCopyOfFirstSizeDivNElements[0], 1);
        }
    }

    @Nested
    @DisplayName("Tests for method delete()")
    public class DeleteTest {

        @Test
        void testDeleteWithNegativeNumber() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.delete(-1),
                    "The method delete should throw an IndexOutOfBoundsException with" +
                            " a negative number!");
        }

        @Test
        void testDeleteWithIndexMoreThanLength() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.delete(10),
                    "The method delete should throw an IndexOutOfBoundsException for " +
                            "index more than array length!");
        }

        @Test
        void testDeleteWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertThrows(IndexOutOfBoundsException.class,
                    () -> localDynamicArrayTest.delete(0),
                    "The method delete should throw an IndexOutOfBoundsException for " +
                            "index more than array length!");
        }

        @Test
        void testDeleteWithNonEmptyArray() {
            dynamicArray.delete(1);

            assertEquals(dynamicArray.size(), 2);
            assertTrue(dynamicArray.contains(1));
            assertTrue(dynamicArray.contains(3));
        }
    }


    @Nested
    @DisplayName("Tests for method addAll()")
    public class AddAllTest {

        @Test
        void testAddAllWithNullValue() {
            assertThrows(IllegalArgumentException.class,
                    () -> dynamicArray.addAll(null));
        }

        @Test
        void testAddAllWithLessThanCapacity() {
            dynamicArray.addAll(1);

            List<Integer> listCopyOfArray = new LinkedList<>();

            listCopyOfArray.add(1);
            listCopyOfArray.add(2);
            listCopyOfArray.add(3);
            listCopyOfArray.add(1);


            assertEquals(dynamicArray.size(), listCopyOfArray.size());
            assertTrue(dynamicArray.containsAll(listCopyOfArray));
        }

        @Test
        void testAddAllWithMoreThanCapacity() {
            dynamicArray.addAll(4, 5, 6, 7);

            List<Integer> listCopyOfArray = new LinkedList<>();

            listCopyOfArray.add(1);
            listCopyOfArray.add(2);
            listCopyOfArray.add(3);
            listCopyOfArray.add(4);
            listCopyOfArray.add(5);
            listCopyOfArray.add(6);
            listCopyOfArray.add(7);

            assertEquals(dynamicArray.size(), listCopyOfArray.size());
            assertTrue(dynamicArray.containsAll(listCopyOfArray));
        }
    }


    @Nested
    @DisplayName("Tests for method copy()")
    public class CopyTest {

        @Test
        void testCopyWithThreeElementArray() {

            assertTrue(dynamicArray.containsAll(dynamicArray.copy()));
            assertEquals(dynamicArray.size(), dynamicArray.copy().size());

            assertThrows(UnsupportedOperationException.class,
                    () -> dynamicArray.copy().add(12),
                    "The method copy should throw an UnsupportedOperationException" +
                            " when trying to make a change to the immutable return value!");
        }
    }

    @Nested
    @DisplayName("Tests for method stream()")
    public class StreamTest {

        @Test
        void testStream() {
            List<Integer> resultAfterMultiplyingByTwo = dynamicArray.stream()
                    .map(el -> el * 2).toList();

            List<Integer> copyOfResultAfterMultiplyingByTwo = new LinkedList<>();

            copyOfResultAfterMultiplyingByTwo.add(2);
            copyOfResultAfterMultiplyingByTwo.add(4);
            copyOfResultAfterMultiplyingByTwo.add(6);

            assertEquals(resultAfterMultiplyingByTwo.size(), copyOfResultAfterMultiplyingByTwo.size());
            assertTrue(resultAfterMultiplyingByTwo.containsAll(copyOfResultAfterMultiplyingByTwo));
        }
    }

    @Nested
    @DisplayName("Tests for method remove()")
    public class RemoveTest {

        @Test
        void testRemoveWithNegativeNumber() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.remove(-1),
                    "The method delete should throw an IndexOutOfBoundsException with" +
                            " a negative number!");
        }

        @Test
        void testRemoveWithIndexMoreThanLength() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.remove(10),
                    "The method delete should throw an IndexOutOfBoundsException for " +
                            "index more than array length!");
        }

        @Test
        void testRemoveWithNonEmptyArray() {
            dynamicArray.remove(1);

            assertEquals(dynamicArray.size(), 2);
            assertTrue(dynamicArray.contains(1));
            assertTrue(dynamicArray.contains(3));
        }

        @Test
        void testRemoveWithoutArgumentsWithNonEmptyArray() {
            dynamicArray.remove();
            assertEquals(dynamicArray.size(), 2);
            assertTrue(dynamicArray.contains(1));
            assertTrue(dynamicArray.contains(2));
        }
    }

    @Nested
    @DisplayName("Tests for method ensureCapacity()")
    public class EnsureCapacityTest {

        @Test
        void testEnsureCapacityWithWithNewSizeLessThanCurrentCapacity() {
            assertThrows(IllegalArgumentException.class,
                    () -> dynamicArray.ensureCapacity(1),
                    "The method ensureCapacity should throw an IllegalArgumentException" +
                            " with newSize less then the current array capacity!");
        }

        @Test
        void testEnsureCapacityWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertEquals(localDynamicArrayTest.capacity(), 1);

            localDynamicArrayTest.ensureCapacity(10);
            assertEquals(localDynamicArrayTest.capacity(), 10);
        }

        @Test
        void testEnsureCapacityWithNonEmptyArray() {
            dynamicArray.ensureCapacity(10);

            List<Integer> copyListOfDynamicArray = new LinkedList<>();
            copyListOfDynamicArray.add(1);
            copyListOfDynamicArray.add(2);
            copyListOfDynamicArray.add(3);
            copyListOfDynamicArray.add(null);

            assertEquals(dynamicArray.capacity(), 10);
            assertTrue(copyListOfDynamicArray.containsAll(dynamicArray.copy()));
        }
    }

    @Nested
    @DisplayName("Tests for method capcity()")
    public class CapacityTest {

        @Test
        void testCapacityWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            assertEquals(1, localDynamicArrayTest.capacity());
        }

        @Test
        void testCapacityWithNonEmptyArray() {
            assertEquals(4, dynamicArray.capacity());
        }
    }

    @Nested
    @DisplayName("Tests for method trim()")
    public class TrimTest {

        @Test
        void testTrimWithEmptyArray() {
            DynamicArray<Integer> localDynamicArrayTest = new DynamicArrayImplementation<>(Integer.class);

            localDynamicArrayTest.trimToSize();
            assertEquals(0, localDynamicArrayTest.capacity());
        }

        @Test
        void testTrimWithNonEmptyArray() {
            assertEquals(4, dynamicArray.capacity());

            dynamicArray.trimToSize();
            assertEquals(3, dynamicArray.capacity());
        }

    }

    @Nested
    @DisplayName("Tests for method set()")
    public class SetTest {

        @Test
        void testSetWithNegativeNumberForIndexValue() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.set(-1, 12),
                    "The method set should throw an IndexOutOfBoundsException " +
                            "for index value less than 0");
        }

        @Test
        void testSetWithIndexValueMoreThanArraySize() {
            assertThrows(IndexOutOfBoundsException.class,
                    () -> dynamicArray.set(3, 12),
                    "The method set should throw an IndexOutOfBoundsException " +
                            "for index value more than array size!");
        }

        @Test
        void testSetWithNullForValueArgument() {
            assertThrows(IllegalArgumentException.class,
                    () -> dynamicArray.set(1, null),
                    "The method set should throw an IllegalArgumentException " +
                            "for value null of argument value!");
        }

        @Test
        void testSetWithIndexZeroAndNewValueFive() {
            assertEquals(1, dynamicArray.set(0, 5));

            List<Integer> copyListOfDynamicArray = new LinkedList<>();
            copyListOfDynamicArray.add(5);
            copyListOfDynamicArray.add(2);
            copyListOfDynamicArray.add(3);

            assertEquals(copyListOfDynamicArray.size(), dynamicArray.size());
            assertTrue(copyListOfDynamicArray.containsAll(dynamicArray.stream().toList()));
        }

        @Test
        void testSetWithLastIndexAndValueZero() {
            assertEquals(3, dynamicArray.set(dynamicArray.size() - 1, 0));

            List<Integer> copyListOfDynamicArray = new LinkedList<>();
            copyListOfDynamicArray.add(1);
            copyListOfDynamicArray.add(2);
            copyListOfDynamicArray.add(0);

            assertEquals(copyListOfDynamicArray.size(), dynamicArray.size());
            assertTrue(copyListOfDynamicArray.containsAll(dynamicArray.stream().toList()));

        }
    }
}