package com.orbar.mockparcel.internal;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by orbar on 11/20/16.
 */

public class WriteArrayAnswer<U> implements Answer {

    private Class<U> uClass;
    private List<Object> objects;

    public WriteArrayAnswer(final Class<U> uClass, final List<Object> objects) {
        this.uClass = uClass;
        this.objects = objects;
    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        Object parameter = invocation.getArgumentAt(0, Array.newInstance(uClass, 0).getClass());

        objects.add(Array.getLength(parameter));
        objects.add(parameter);
        return null;
    }
}
