package com.orbar.mockparcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by orbar on 11/13/16.
 */

public class ParcelObject implements Parcelable {
    int mInt;
    byte mByte;
    boolean mBoolean;
    long mLong;
    float mFloat;
    double mDouble;

    String mString;

    Object[] mObjects;

    char[] mChars;
    int[] mInts;
    byte[] mBytes;
    boolean[] mBooleans;
    long[] mLongs;
    float[] mFloats;
    double[] mDoubles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelObject that = (ParcelObject) o;

        if (mInt != that.mInt) return false;
        if (mByte != that.mByte) return false;
        if (mBoolean != that.mBoolean) return false;
        if (mLong != that.mLong) return false;
        if (Float.compare(that.mFloat, mFloat) != 0) return false;
        if (Double.compare(that.mDouble, mDouble) != 0) return false;
        if (!mString.equals(that.mString)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(mObjects, that.mObjects)) return false;
        if (!Arrays.equals(mChars, that.mChars)) return false;
        if (!Arrays.equals(mInts, that.mInts)) return false;
        if (!Arrays.equals(mBytes, that.mBytes)) return false;
        if (!Arrays.equals(mBooleans, that.mBooleans)) return false;
        if (!Arrays.equals(mLongs, that.mLongs)) return false;
        if (!Arrays.equals(mFloats, that.mFloats)) return false;
        return Arrays.equals(mDoubles, that.mDoubles);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) mInt;
        result = 31 * result + (int) mByte;
        result = 31 * result + (mBoolean ? 1 : 0);
        result = 31 * result + (int) (mLong ^ (mLong >>> 32));
        result = 31 * result + (mFloat != +0.0f ? Float.floatToIntBits(mFloat) : 0);
        temp = Double.doubleToLongBits(mDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mString.hashCode();
        result = 31 * result + Arrays.hashCode(mObjects);
        result = 31 * result + Arrays.hashCode(mChars);
        result = 31 * result + Arrays.hashCode(mInts);
        result = 31 * result + Arrays.hashCode(mBytes);
        result = 31 * result + Arrays.hashCode(mBooleans);
        result = 31 * result + Arrays.hashCode(mLongs);
        result = 31 * result + Arrays.hashCode(mFloats);
        result = 31 * result + Arrays.hashCode(mDoubles);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.mInt);
        dest.writeByte(this.mByte);
        dest.writeByte(this.mBoolean ? (byte) 1 : (byte) 0);
        dest.writeLong(this.mLong);
        dest.writeFloat(this.mFloat);
        dest.writeDouble(this.mDouble);

        dest.writeString(this.mString);
        dest.writeArray(this.mObjects);

        dest.writeCharArray(mChars);
        dest.writeByteArray(mBytes);
        dest.writeBooleanArray(this.mBooleans);
        dest.writeIntArray(this.mInts);
        dest.writeLongArray(this.mLongs);
        dest.writeFloatArray(this.mFloats);
        dest.writeDoubleArray(this.mDoubles);
    }

    public ParcelObject() {
    }

    protected ParcelObject(Parcel in) {
        this.mInt = in.readInt();
        this.mByte = in.readByte();
        this.mBoolean = in.readByte() != 0;
        this.mLong = in.readLong();
        this.mFloat = in.readFloat();
        this.mDouble = in.readDouble();

        this.mString = in.readString();
        this.mObjects = in.readArray(Object[].class.getClassLoader());

        this.mChars = in.createCharArray();
        this.mBytes = in.createByteArray();
        this.mBooleans = in.createBooleanArray();
//        this.mBooleans = new boolean[2];
//        in.readBooleanArray(this.mBooleans);

        this.mInts = in.createIntArray();
        this.mLongs = in.createLongArray();
        this.mFloats = in.createFloatArray();
        this.mDoubles = in.createDoubleArray();
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
