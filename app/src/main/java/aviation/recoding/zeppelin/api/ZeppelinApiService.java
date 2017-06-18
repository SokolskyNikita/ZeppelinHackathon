package aviation.recoding.zeppelin.api;

import aviation.recoding.zeppelin.model.FlightsResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ZeppelinApiService {

    // SITA API
    //https://flifo-qa.api.aero/flifo/v3/flights/MIA/d?futureWindow=4

    @GET("flifo/v3/flights/{airport}/d?futureWindow=4")
    Observable<FlightsResponse> getFlights(@Path("airport") String airport);

}
