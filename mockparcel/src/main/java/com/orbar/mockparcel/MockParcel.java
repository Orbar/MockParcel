package com.orbar.mockparcel;

import android.os.Parcel;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by orbar on 11/13/16.
 */

public class MockParcel {

    Parcel mockedParcel;
    int position;
    List<Object> objects;

    public static Parcel obtain() {
        return new MockParcel().getMockedParcel();
    }

    public Parcel getMockedParcel() {
        return mockedParcel;
    }

    public MockParcel() {
        mockedParcel = mock(Parcel.class);
        objects = new ArrayList<>();
        setupMock();
    }

    private void setupMock() {
        setupWrites();
        setupReads();
        setupOthers();
    }

    private void setupWrites() {
        final Answer<Void> writeValueAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object parameter = invocation.getArguments()[0];
                objects.add(parameter);
                return null;
            }
        };

        doAnswer(writeValueAnswer).when(mockedParcel).writeString(anyString());
        doAnswer(writeValueAnswer).when(mockedParcel).writeInt(anyInt());
        doAnswer(writeValueAnswer).when(mockedParcel).writeLong(anyLong());
        doAnswer(writeValueAnswer).when(mockedParcel).writeFloat(anyFloat());
        doAnswer(writeValueAnswer).when(mockedParcel).writeDouble(anyDouble());
        doAnswer(writeValueAnswer).when(mockedParcel).writeByte(anyByte());

        doAnswer(writeValueAnswer).when(mockedParcel).writeArray(anyCollection().toArray());
        doAnswer(new WriteIntArrayAnswer()).when(mockedParcel).writeIntArray(any(int[].class));
        doAnswer(new WriteBooleanArrayAnswer()).when(mockedParcel).writeBooleanArray(any(boolean[].class));
        doAnswer(new WriteLongArrayAnswer()).when(mockedParcel).writeLongArray(any(long[].class));

    }


    private void setupReads() {
        when(mockedParcel.readString()).thenAnswer(new Answer<String>() {
            @Override
            public String answer(final InvocationOnMock invocation) throws Throwable {
                return (String) objects.get(position++);
            }
        });
        when(mockedParcel.readInt()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(final InvocationOnMock invocation) throws Throwable {
                return (Integer) objects.get(position++);
            }
        });
        when(mockedParcel.readLong()).thenAnswer(new Answer<Long>() {
            @Override
            public Long answer(final InvocationOnMock invocation) throws Throwable {
                return (Long) objects.get(position++);
            }
        });
        when(mockedParcel.readFloat()).thenAnswer(new Answer<Float>() {
            @Override
            public Float answer(final InvocationOnMock invocation) throws Throwable {
                return (Float) objects.get(position++);
            }
        });
        when(mockedParcel.readDouble()).thenAnswer(new Answer<Double>() {
            @Override
            public Double answer(final InvocationOnMock invocation) throws Throwable {
                return (Double) objects.get(position++);
            }
        });
        when(mockedParcel.readByte()).thenAnswer(new Answer<Byte>() {
            @Override
            public Byte answer(final InvocationOnMock invocation) throws Throwable {
                return (Byte) objects.get(position++);
            }
        });
        when(mockedParcel.readArray(Object[].class.getClassLoader())).thenAnswer(new Answer<Object[]>() {
            @Override
            public Object[] answer(final InvocationOnMock invocation) throws Throwable {
                return (Object[]) objects.get(position++);
            }
        });

        doAnswer(new ReadBooleanArray()).when(mockedParcel).readBooleanArray(any(boolean[].class));
        doAnswer(new ReadIntArray()).when(mockedParcel).readIntArray(any(int[].class));
        doAnswer(new ReadLongArray()).when(mockedParcel).readLongArray(any(long[].class));

    }


    private void setupOthers() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                position = ((Integer) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockedParcel).setDataPosition(anyInt());

        when(mockedParcel.createBooleanArray()).thenAnswer(new CreateBooleanArrayAnswer());
        when(mockedParcel.createIntArray()).thenAnswer(new CreateIntArrayAnswer());
        when(mockedParcel.createLongArray()).thenAnswer(new CreateLongArrayAnswer());

    }

    private class WriteBooleanArrayAnswer implements Answer<boolean[]> {
        @Override
        public boolean[] answer(InvocationOnMock invocation) throws Throwable {
            boolean[] parameter = invocation.getArgumentAt(0, boolean[].class);
            if (parameter != null) {
                objects.add(parameter.length);
                objects.add(parameter);
            } else {
                objects.add(-1);
            }
            return null;
        }
    }

    private class CreateBooleanArrayAnswer implements Answer<boolean[]> {
        @Override
        public boolean[] answer(InvocationOnMock invocation) throws Throwable {
            if (objects.size() > position) {
                int count = (int) objects.get(position++);
                if (count >= 0) {
                    boolean[] array = new boolean[count];
                    System.arraycopy(objects.get(position++), 0, array, 0, count);
                    return array;
                }
            }
            return null;
        }
    }

    private class ReadBooleanArray implements Answer<boolean[]> {
        @Override
        public boolean[] answer(InvocationOnMock invocation) throws Throwable {
            if (objects.size() > position) {
                boolean[] val = invocation.getArgumentAt(0, boolean[].class);
                int count = (int) objects.get(position++);
                if (count == val.length) {
                    boolean[] intArray = (boolean[]) objects.get(position++);
                    System.arraycopy(intArray, 0, val, 0, count);
                    return intArray;
                } else {
                    throw new RuntimeException("bad array lengths");
                }
            }
            return null;
        }
    }

    private class WriteIntArrayAnswer implements Answer<int[]> {
        @Override
        public int[] answer(InvocationOnMock invocation) throws Throwable {
            int[] val = invocation.getArgumentAt(0, int[].class);
            if (val != null) {
                objects.add(val.length);
                objects.add(val);
            } else {
                objects.add(-1);
            }
            return null;
        }
    }

    private class CreateIntArrayAnswer implements Answer<int[]> {
        @Override
        public int[] answer(InvocationOnMock invocation) throws Throwable {
            if (objects.size() > position) {
                int count = (int) objects.get(position++);
                if (count >= 0) {
                    int[] array = new int[count];
                    System.arraycopy(objects.get(position++), 0, array, 0, count);
                    return array;
                }
            }
            return null;
        }
    }

    private class ReadIntArray implements Answer<int[]> {
        @Override
        public int[] answer(InvocationOnMock invocation) throws Throwable {
            if (objects.size() > position) {
                int[] val = invocation.getArgumentAt(0, int[].class);
                int count = (int) objects.get(position++);
                if (count == val.length) {
                    int[] intArray = (int[]) objects.get(position++);
                    System.arraycopy(intArray, 0, val, 0, count);
                    return intArray;
                } else {
                    throw new RuntimeException("bad array lengths");
                }
            }
            return null;
        }
    }

    private class WriteLongArrayAnswer implements Answer<long[]> {
        @Override
        public long[] answer(InvocationOnMock invocation) throws Throwable {
            long[] parameter = invocation.getArgumentAt(0, long[].class);
            if (parameter != null) {
                objects.add(parameter.length);
                objects.add(parameter);
            } else {
                objects.add(-1);
            }
            return null;
        }
    }

    private class CreateLongArrayAnswer implements Answer<long[]> {
        @Override
        public long[] answer(InvocationOnMock invocation) throws Throwable {
            if (objects.size() > position) {
                int count = (int) objects.get(position++);
                if (count >= 0) {
                    long[] array = new long[count];
                    System.arraycopy(objects.get(position++), 0, array, 0, count);
                    return array;
                }
            }
            return null;
        }
    }

    private class ReadLongArray implements Answer<long[]> {
        @Override
        public long[] answer(InvocationOnMock invocation) throws Throwable {
            if (objects.size() > position) {
                long[] val = invocation.getArgumentAt(0, long[].class);
                int count = (int) objects.get(position++);
                if (count == val.length) {
                    long[] intArray = (long[]) objects.get(position++);
                    System.arraycopy(intArray, 0, val, 0, count);
                    return intArray;
                } else {
                    throw new RuntimeException("bad array lengths");
                }
            }
            return null;
        }
    }

    // TODO: 11/13/16 figure out how to generify this
    private class WriteArrayAnswer< U> implements Answer {

        private Class<U> uClass;

        public WriteArrayAnswer(Class<U> uClass) {
            this.uClass = uClass;
        }

        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            Object parameter = invocation.getArgumentAt(0, Array.newInstance(uClass, 0).getClass());

            objects.add(uClass.getClasses().length);
            objects.add(parameter);
            return null;
        }
    }
}