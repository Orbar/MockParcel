package com.orbar.mockparcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by orbar on 11/13/16.
 */

public class ParcelObject implements Parcelable {
    String mString;
    int mInt;
    long mLong;
    float mFloat;
    double mDouble;
    boolean mBoolean;

    Object[] mObjects;
    boolean[] mBooleans;
    int[] mInts;
    long[] mLongs;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelObject that = (ParcelObject) o;

        if (mInt != that.mInt) return false;
        if (mLong != that.mLong) return false;
        if (Float.compare(that.mFloat, mFloat) != 0) return false;
        if (Double.compare(that.mDouble, mDouble) != 0) return false;
        if (mBoolean != that.mBoolean) return false;
        if (!mString.equals(that.mString)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(mObjects, that.mObjects)) return false;
        if (!Arrays.equals(mBooleans, that.mBooleans)) return false;
        if (!Arrays.equals(mInts, that.mInts)) return false;
        return Arrays.equals(mLongs, that.mLongs);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mString.hashCode();
        result = 31 * result + mInt;
        result = 31 * result + (int) (mLong ^ (mLong >>> 32));
        result = 31 * result + (mFloat != +0.0f ? Float.floatToIntBits(mFloat) : 0);
        temp = Double.doubleToLongBits(mDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mBoolean ? 1 : 0);
        result = 31 * result + Arrays.hashCode(mObjects);
        result = 31 * result + Arrays.hashCode(mBooleans);
        result = 31 * result + Arrays.hashCode(mInts);
        result = 31 * result + Arrays.hashCode(mLongs);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mString);
        dest.writeInt(this.mInt);
        dest.writeLong(this.mLong);
        dest.writeFloat(this.mFloat);
        dest.writeDouble(this.mDouble);
        dest.writeByte(this.mBoolean ? (byte) 1 : (byte) 0);
        dest.writeArray(this.mObjects);
        dest.writeBooleanArray(this.mBooleans);
        dest.writeIntArray(this.mInts);
        dest.writeLongArray(this.mLongs);
    }

    public ParcelObject() {
    }

    protected ParcelObject(Parcel in) {
        this.mString = in.readString();
        this.mInt = in.readInt();
        this.mLong = in.readLong();
        this.mFloat = in.readFloat();
        this.mDouble = in.readDouble();
        this.mBoolean = in.readByte() != 0;
        this.mObjects = in.readArray(Object[].class.getClassLoader());
        this.mBooleans = in.createBooleanArray();
//        in.readBooleanArray(this.mBooleans);

        this.mInts = in.createIntArray();
//        in.readIntArray(this.mInts);

        this.mLongs = in.createLongArray();
//        in.readLongArray(this.mLongs);

    }

    public static final Creator<ParcelObject> CREATOR = new Creator<ParcelObject>() {
        @Override
        public ParcelObject createFromParcel(Parcel source) {
            return new ParcelObject(source);
        }

        @Override
        public ParcelObject[] newArray(int size) {
            return new ParcelObject[size];
        }
    };
}
