package com.orbar.mockparcel.internal;

/**
 * Created by orbar on 11/20/16.
 */

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Array;
import java.util.List;

public class ReadArrayAnswer<U> implements Answer {

    private Class<U> uClass;
    private MutableInt position;
    private List<Object> objects;

    public ReadArrayAnswer(final Class<U> uClass, final List<Object> objects, final MutableInt position) {
        this.uClass = uClass;
        this.objects = objects;
        this.position = position;
    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        // check is not needed (i think)
        if (objects.size() > position.value) {
            Object dest = invocation.getArgumentAt(0, uClass);
            int count = (int) objects.get(position.value++);

            if (count == Array.getLength(dest)) {

                for (int i = 0; i < count; i++) {
                    Object value =  Array.get(objects.get(position.value), i);
                    Array.set(dest, i, value);
                }
                position.value++;
                return dest;
            } else {
                throw new RuntimeException("bad array lengths");
            }
        }
        return null;
    }
}