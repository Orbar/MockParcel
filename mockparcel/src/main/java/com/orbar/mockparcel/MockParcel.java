package com.orbar.mockparcel;

import android.os.Parcel;

import com.orbar.mockparcel.internal.CreateArrayAnswer;
import com.orbar.mockparcel.internal.MutableInt;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import com.orbar.mockparcel.internal.ReadArrayAnswer;
import com.orbar.mockparcel.internal.WriteArrayAnswer;

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
    MutableInt position;
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
        position = new MutableInt(0);
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
        doAnswer(new WriteArrayAnswer(char.class, objects)).when(mockedParcel).writeCharArray(any(char[].class));
        doAnswer(new WriteArrayAnswer(int.class, objects)).when(mockedParcel).writeIntArray(any(int[].class));
        doAnswer(new WriteArrayAnswer(byte.class, objects)).when(mockedParcel).writeByteArray(any(byte[].class));
        doAnswer(new WriteArrayAnswer(boolean.class, objects)).when(mockedParcel).writeBooleanArray(any(boolean[].class));
        doAnswer(new WriteArrayAnswer(long.class, objects)).when(mockedParcel).writeLongArray(any(long[].class));
        doAnswer(new WriteArrayAnswer(float.class, objects)).when(mockedParcel).writeFloatArray(any(float[].class));
        doAnswer(new WriteArrayAnswer(double.class, objects)).when(mockedParcel).writeDoubleArray(any(double[].class));
    }


    private void setupReads() {
        when(mockedParcel.readString()).thenAnswer(new Answer<String>() {
            @Override
            public String answer(final InvocationOnMock invocation) throws Throwable {
                return (String) objects.get(position.value++);
            }
        });
        when(mockedParcel.readInt()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(final InvocationOnMock invocation) throws Throwable {
                return (Integer) objects.get(position.value++);
            }
        });
        when(mockedParcel.readLong()).thenAnswer(new Answer<Long>() {
            @Override
            public Long answer(final InvocationOnMock invocation) throws Throwable {
                return (Long) objects.get(position.value++);
            }
        });
        when(mockedParcel.readFloat()).thenAnswer(new Answer<Float>() {
            @Override
            public Float answer(final InvocationOnMock invocation) throws Throwable {
                return (Float) objects.get(position.value++);
            }
        });
        when(mockedParcel.readDouble()).thenAnswer(new Answer<Double>() {
            @Override
            public Double answer(final InvocationOnMock invocation) throws Throwable {
                return (Double) objects.get(position.value++);
            }
        });
        when(mockedParcel.readByte()).thenAnswer(new Answer<Byte>() {
            @Override
            public Byte answer(final InvocationOnMock invocation) throws Throwable {
                return (Byte) objects.get(position.value++);
            }
        });
        when(mockedParcel.readArray(Object[].class.getClassLoader())).thenAnswer(new Answer<Object[]>() {
            @Override
            public Object[] answer(final InvocationOnMock invocation) throws Throwable {
                return (Object[]) objects.get(position.value++);
            }
        });

        doAnswer(new ReadArrayAnswer(char.class, objects, position)).when(mockedParcel).readCharArray(any(char[].class));
        doAnswer(new ReadArrayAnswer(int.class, objects, position)).when(mockedParcel).readIntArray(any(int[].class));
        doAnswer(new ReadArrayAnswer(byte.class, objects, position)).when(mockedParcel).readByteArray(any(byte[].class));
        doAnswer(new ReadArrayAnswer(boolean.class, objects, position)).when(mockedParcel).readBooleanArray(any(boolean[].class));
        doAnswer(new ReadArrayAnswer(long.class, objects, position)).when(mockedParcel).readLongArray(any(long[].class));
        doAnswer(new ReadArrayAnswer(float.class, objects, position)).when(mockedParcel).readFloatArray(any(float[].class));
        doAnswer(new ReadArrayAnswer(double.class, objects, position)).when(mockedParcel).readDoubleArray(any(double[].class));
    }


    private void setupOthers() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                position.value = ((Integer) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockedParcel).setDataPosition(anyInt());

        when(mockedParcel.createCharArray()).thenAnswer(new CreateArrayAnswer(char.class, objects, position));
        when(mockedParcel.createIntArray()).thenAnswer(new CreateArrayAnswer(int.class, objects, position));
        when(mockedParcel.createByteArray()).thenAnswer(new CreateArrayAnswer(byte.class, objects, position));
        when(mockedParcel.createBooleanArray()).thenAnswer(new CreateArrayAnswer(boolean.class, objects, position));
        when(mockedParcel.createLongArray()).thenAnswer(new CreateArrayAnswer(long.class, objects, position));
        when(mockedParcel.createFloatArray()).thenAnswer(new CreateArrayAnswer(float.class, objects, position));
        when(mockedParcel.createDoubleArray()).thenAnswer(new CreateArrayAnswer(double.class, objects, position));

    }
}