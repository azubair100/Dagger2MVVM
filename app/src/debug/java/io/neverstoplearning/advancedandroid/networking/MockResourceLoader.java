package io.neverstoplearning.advancedandroid.networking;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import timber.log.Timber;

//This method is for Calling Mock responses, after successful processing Mock responses
class MockResourceLoader {

    private MockResourceLoader() {

    }

    @Nullable
    static String getResponseString(Context context, String method, String[] endpointParts) {
        try {
            //root of mock directory
            String currentPath = "mock";
            //and then we will put all of our files in the current directory as a set
            //holds all of our directories in mock
            Set<String> mockList = new HashSet<>(Arrays.asList(context.getAssets().list(currentPath)));

            //go over the list to get our endpoint
            for (String endpointPart : endpointParts) {
                //if the current path is in the list
                if (mockList.contains(endpointPart)) {
                    //update the current path
                    currentPath = currentPath + "/" + endpointPart;
                    //now update the mocklist
                    mockList = new HashSet<>(Arrays.asList(context.getAssets().list(currentPath)));
                }
            }

            // At this stage, our mock list will be the list of files in the matching directory for
            // the endpoint parts.
            //try to find the final path of our mock response
            String finalPath = null;

            //iterate through each item of our mock response
            for (String path : mockList) {

                if (path.contains(method.toLowerCase())) {
                    finalPath = currentPath + "/" + path;
                    break;
                }
            }

            if (finalPath != null) {
                return responseFromPath(context, finalPath);
            }
            return null;
        } catch (IOException e) {
            Timber.e(e, "Error loading mock response from assets");
            return null;
        }
    }

    //very similar to our TestUtil class
    private static String responseFromPath(Context context, String path) {
        StringBuilder sb = new StringBuilder();
        try (InputStream assetStream = context.getAssets().open(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(assetStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Timber.e(e, "Error reading mock response");
        }
        return sb.toString();
    }
}
