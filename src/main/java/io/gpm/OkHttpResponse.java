package io.gpm;

import lombok.Getter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import twitter4j.HttpClientConfiguration;
import twitter4j.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author George
 * @since 06-Jan-18
 */
public class OkHttpResponse extends HttpResponse{

    @Getter
    private OkHttpClient client;
    @Getter
    private Call call;
    @Getter
    private Response response;
    @Getter
    private HashMap<String, List<String>> headerFields;

    @Override
    public String getResponseHeader(String s) {
        return null;
    }

    @Override
    public Map<String, List<String>> getResponseHeaderFields() {
        return null;
    }

    @Override
    public void disconnect() throws IOException {

    }

    public OkHttpResponse(Call call, OkHttpClient client, HttpClientConfiguration configuration) throws IOException {
        super(configuration);
        this.client = client;
        this.call = call;
        this.response = call.execute();


    }
}
