/*
 * Copyright (c) 2014.
 * By Hatem Noureddine.
 */

package com.proxymit.robospice.retrofit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by hatem.noureddine on 18/11/2014.
 * Retrofit client that uses {@link HttpURLConnection} for communication.
 */
public class InterceptingOkClient implements Client {
    static final int CONNECT_TIMEOUT_MILLIS = 60 * 1000; // 20s
    static final int READ_TIMEOUT_MILLIS = 60 * 1000; // 20s
    private static final int CHUNK_SIZE = 4096;
    private final OkUrlFactory okUrlFactory;


    public InterceptingOkClient() {
        this(generateDefaultOkHttp());
    }


    public InterceptingOkClient(OkHttpClient client) {
        this.okUrlFactory = new OkUrlFactory(client);
    }

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        client.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        return client;
    }

    protected HttpURLConnection openConnection(Request request) throws IOException {
        return okUrlFactory.open(new URL(request.getUrl()));
    }

    @Override
    public Response execute(Request request) throws IOException {
        HttpURLConnection connection = openConnection(request);
        prepareRequest(connection, request);
        return readResponse(connection);
    }

    void prepareRequest(HttpURLConnection connection, Request request) throws IOException {
        connection.setRequestMethod(request.getMethod());
        connection.setDoInput(true);

        for (Header header : request.getHeaders()) {
            connection.addRequestProperty(header.getName(), header.getValue());
        }

        TypedOutput body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", body.mimeType());
            long length = body.length();
            if (length != -1) {
                connection.setFixedLengthStreamingMode((int) length);
                connection.addRequestProperty("Content-Length", String.valueOf(length));
            } else {
                connection.setChunkedStreamingMode(CHUNK_SIZE);
            }
            body.writeTo(connection.getOutputStream());
        }
    }

    private Response readResponse(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        String reason = connection.getResponseMessage();
        if (reason == null) reason = ""; // HttpURLConnection treats empty reason as null.

        List<Header> headers = new ArrayList<Header>();
        for (Map.Entry<String, List<String>> field : connection.getHeaderFields().entrySet()) {
            String name = field.getKey();
            for (String value : field.getValue()) {
                headers.add(new Header(name, value));
            }
        }

        String mimeType = connection.getContentType();
        int length = connection.getContentLength();
        InputStream stream;
        if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
            stream = connection.getErrorStream();
        } else {
            stream = connection.getInputStream();
        }

        //--- Clone Input Stream to check if body is json format
        //check if stream is in Json Format
        ByteArrayOutputStream into = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];

        // inputStream is your original stream.
        for (int n; 0 < (n = stream.read(buf)); ) {
            into.write(buf, 0, n);
        }
        into.close();

        byte[] data = into.toByteArray();

        //This is your data in string format.
        String body = new String(data, "UTF-8"); // Or whatever encoding

        //This is the new stream that you can pass it to other code and use its data.
        ByteArrayInputStream newStream = new ByteArrayInputStream(data);

        boolean isBodyJsonFormat = false;
        Pattern pattern = Pattern.compile("(\\s*\\{.*?\\}\\s*)");
        Matcher matcher = pattern.matcher(body);

        if (body != null && matcher.matches())
            isBodyJsonFormat = true;
        //----------------------------------------------------

        TypedInput responseBody = new TypedInputStream(mimeType, length, newStream);
        return new Response(connection.getURL().toString(), (isBodyJsonFormat && status == HttpURLConnection.HTTP_BAD_REQUEST) ? HttpURLConnection.HTTP_OK : status, reason, headers, responseBody);
    }
}