package io.gpm;

import lombok.Getter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import twitter4j.HttpClientConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/***
 * @author George
 * @since 06-Jan-18
 */
public class OkHttpResponse {

    @Getter
    private OkHttpClient client;
    @Getter
    private Call call;
    @Getter
    private Response response;
    @Getter
    private HashMap<String, List<String>> headerFields;

    OkHttpResponse(){
        super();
    }

    public OkHttpResponse(Call call, OkHttpClient client, HttpClientConfiguration configuration) throws IOException {

    }
}
