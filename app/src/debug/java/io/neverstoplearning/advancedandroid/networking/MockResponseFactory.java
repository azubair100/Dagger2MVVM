package io.neverstoplearning.advancedandroid.networking;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Request;

class MockResponseFactory {

    private final Context context;
    //base url length
    private final int startIndex;

    @Inject
    MockResponseFactory(Context context, @Named("base_url") String baseUrl) {
        this.context = context;
        startIndex = baseUrl.length();
    }

    //request objects are okhttp3 object
    @Nullable
    String getMockResponse(Request request) {
        String[] endpointParts = getEndpoint(request).split("/");
        return MockResourceLoader.getResponseString(context, request.method(), endpointParts);
    }

    private String getEndpoint(Request request) {
        String url = request.url().toString();
        int queryParamStart = url.indexOf("?");
        //if there was no query param
        //just return the whole substring
        //otherwise get url after ?
        return queryParamStart == -1 ? url.substring(startIndex) : url.substring(startIndex, queryParamStart);
    }
}
