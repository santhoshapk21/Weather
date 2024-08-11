/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hrms.hrms.retrofit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public final class HttpLoggingInterceptor implements Interceptor {
    private final char TOP_LEFT_CORNER = '┌';
    private final char MIDDLE_CORNER = '├';
    private final char HORIZONTAL_LINE = '│';
    private final char BOTTOM_LEFT_CORNER = '└';

    private final String DOUBLE_DIVIDER = "──────────────────────────────────────────────────────────────────────";
    private final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";

    private String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
    private String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        void log(String message);

        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                //Platform.get().log(INFO, message, null);
            }
        };
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    StringBuilder log;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        if (logHeaders) {
            try {
                log = new StringBuilder(TOP_BORDER);
                log.append("\n" + HORIZONTAL_LINE + "--> " + request.method() + ' ' + request.url());
                log.append("\n" + MIDDLE_BORDER);
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        log.append("\n" + HORIZONTAL_LINE).append(name).append(": ").append(headers.value(i));
                    }
                }
            } catch (Exception e) {
            }
        }

        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }

        ResponseBody responseBody = response.body();

        if (logHeaders) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            try {
//                log = new StringBuilder(TOP_BORDER);
                log.append("\n" + MIDDLE_BORDER);
                log.append("\n" + HORIZONTAL_LINE + "<-- " + response.code() + ' ' + response.request().url());
                log.append("\n" + MIDDLE_BORDER);
                String json = buffer.clone().readString(charset).trim();

                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    log.append("\n" + HORIZONTAL_LINE).append(jsonObject.toString(2).replaceAll("\n", "\n" + HORIZONTAL_LINE));
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    log.append("\n" + HORIZONTAL_LINE).append(jsonArray.toString(2).replaceAll("\n", "\n" + HORIZONTAL_LINE));
                } else
                    log.append("\n" + HORIZONTAL_LINE).append(json);

                log.append("\n").append(MIDDLE_BORDER);
                log.append("\n" + HORIZONTAL_LINE + "<-- END ").append(request.method());
                log.append("\n").append(BOTTOM_BORDER);
                logger.log(log.toString());
            } catch (Exception e) {
            }
        }

        return response;
    }
}
