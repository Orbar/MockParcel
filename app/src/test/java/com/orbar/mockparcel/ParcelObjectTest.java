package com.orbar.mockparcel;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by orbar on 11/13/16.
 */
public class ParcelObjectTest {

    private ParcelObject parcelObject;

    @Before
    public void setup() {
        parcelObject = new ParcelObject();

        parcelObject.mInt = 1;
        parcelObject.mByte = 1;
        parcelObject.mBoolean = true;
        parcelObject.mLong = 2L;
        parcelObject.mFloat = 3.0f;
        parcelObject.mDouble = 4.0d;

        parcelObject.mString = "string";
        parcelObject.mObjects = new Object[]{"String", Integer.MAX_VALUE};

        parcelObject.mChars = new char[]{'a', 'b', 'c'};
        parcelObject.mInts = new int[]{0, 1, 2};
        parcelObject.mBytes = new byte[]{1, 2, 7};
        parcelObject.mBooleans = new boolean[]{true, false};
        parcelObject.mLongs = new long[] {1l, 2l, 3l};
        parcelObject.mFloats = new float[] {-1f, 2.0f, 3.5f};
        parcelObject.mDoubles = new double[] {-1d, 2.0d, 3.7d};

    }

    @Test
    public void givenPerson_whenParcelAndUnparcel_thenEqualValue() {

        final Parcel parcel = MockParcel.obtain();
        parcelObject.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        final ParcelObject parceledParcelObject = ParcelObject.CREATOR.createFromParcel(parcel);

        assertThat(parceledParcelObject, equalTo(parcelObject));
    }
}