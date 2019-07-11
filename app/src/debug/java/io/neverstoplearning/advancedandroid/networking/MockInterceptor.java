package io.neverstoplearning.advancedandroid.networking;

import java.io.IOException;

import javax.inject.Inject;

import io.neverstoplearning.advancedandroid.settings.DebugPreferences;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {

    //an Interceptor you can change anything about a request before you letting it proceed
    //you can use it if you have to add authorization headers before all of your request
    private final MockResponseFactory mockResponseFactory;
    private final DebugPreferences debugPreferences;

    @Inject
    MockInterceptor(MockResponseFactory mockResponseFactory, DebugPreferences debugPreferences) {
        this.mockResponseFactory = mockResponseFactory;
        this.debugPreferences = debugPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (debugPreferences.useMockResponsesEnabled()) {
            String mockResponse = mockResponseFactory.getMockResponse(chain.request());
            if (mockResponse != null) {
                return new Response.Builder()
                        .message("")
                        .protocol(Protocol.HTTP_1_1)
                        .request(chain.request())
                        .code(200)
                        .body(ResponseBody.create(MediaType.parse("text/json"), mockResponse))
                        .build();
            }
        }
        return chain.proceed(chain.request());
    }
    //Go and things to debug/NetworkModule
}
