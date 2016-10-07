package com.proxymit.robospice.retrofit;

import java.io.IOException;
import java.io.InputStream;

import retrofit.mime.TypedInput;

/**
 * Created by hatem.noureddine on 22/06/2016.
 */
public class TypedInputStream implements TypedInput {
    private final String mimeType;
    private final long length;
    private final InputStream stream;

    public TypedInputStream(String mimeType, long length, InputStream stream) {
        this.mimeType = mimeType;
        this.length = length;
        this.stream = stream;
    }

    @Override
    public String mimeType() {
        return mimeType;
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public InputStream in() throws IOException {
        return stream;
    }
}