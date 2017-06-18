package aviation.recoding.zeppelin.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hanson on 18/06/2017.
 */
public class HeaderInterceptor implements Interceptor {

    static final String HEADER_ACCEPT = "Accept";
    private static final String VALUE_ACCEPT = "application/json";

    static final String X_API_KEY = "X-apiKey";
    private static final String VALUE_API_KEY = "2cfd0827f82ceaccae7882938b4b1627";

    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request request = chain.request()
                .newBuilder()
                .addHeader(HEADER_ACCEPT, VALUE_ACCEPT)
                .addHeader(X_API_KEY, VALUE_API_KEY)
                .build();
        return chain.proceed(request);

    }
}
