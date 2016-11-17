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

        parcelObject.mString = "string";
        parcelObject.mInt = 1;
        parcelObject.mLong = 2L;
        parcelObject.mFloat = 3.0f;
        parcelObject.mDouble = 4.0d;
        parcelObject.mBoolean = true;
        parcelObject.mObjects = new Object[]{"String", Integer.MAX_VALUE};
        parcelObject.mBooleans = new boolean[]{true, false};
        parcelObject.mInts = new int[]{0, 1, 2};
        parcelObject.mLongs = new long[] {1l, 2l, 3l};
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