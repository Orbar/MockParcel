package com.orbar.mockparcel.internal;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by orbar on 11/20/16.
 */

public class CreateArrayAnswer<U> implements Answer {

    private Class<U> uClass;
    private MutableInt position;
    private List<Object> objects;

    public CreateArrayAnswer(final Class<U> uClass, final List<Object> objects, final MutableInt position) {
        this.uClass = uClass;
        this.objects = objects;
        this.position = position;
    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        // check is not needed (i think)
        if (objects.size() > position.value) {
            int count = (int) objects.get(position.value++);
            if (count >= 0) {
                Object dest = Array.newInstance(uClass, count);

                for (int i = 0; i < count; i++) {
                    Object value =  Array.get(objects.get(position.value), i);
                    Array.set(dest, i, value);
                }
                position.value++;
                return dest;
            }
        }
        return null;
    }
}
