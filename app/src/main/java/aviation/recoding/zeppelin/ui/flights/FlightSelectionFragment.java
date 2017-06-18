package aviation.recoding.zeppelin.ui.flights;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.api.HeaderInterceptor;
import aviation.recoding.zeppelin.api.ZeppelinApiService;
import aviation.recoding.zeppelin.model.FlightRecord;
import aviation.recoding.zeppelin.model.FlightsResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class FlightSelectionFragment extends Fragment {

    @BindView(R.id.flightList)
    RecyclerView flightList;

    @BindView(R.id.searchViewTop)
    SearchView searchView;

    private ArrayList<FlightRecord> flightsArrayList;
    private DataAdapter mAdapter;
    private Filter filter;


    public interface FlightInfoListener {
        void onSelectedFlight();
    }

    private FlightInfoListener mListener;

    private CompositeDisposable mCompositeDisposable;

    public FlightSelectionFragment() {
        // Required empty public constructor
    }

    public static FlightSelectionFragment newInstance() {
        FlightSelectionFragment fragment = new FlightSelectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_selection, container, false);
        ButterKnife.bind(this, view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter = mAdapter.getFilter();
                return true;
            }
        });
        initRecyclerView();
        loadJSON("MIA");
        return view;
    }


    private void initRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        flightList.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(flightList.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        flightList.addItemDecoration(dividerItemDecoration);

    }

    public void onItemSelected(int position) {
        mListener.onSelectedFlight();
    }

    private void loadJSON(String airport) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();

        ZeppelinApiService requestInterface = new Retrofit.Builder()
                .baseUrl("https://flifo-qa.api.aero/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build().create(ZeppelinApiService.class);

        mCompositeDisposable.add(requestInterface.getFlights(airport)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<FlightsResponse>() {
                    @Override
                    public void accept(@NonNull FlightsResponse flightsResponse) throws Exception {
                        handleResponse(flightsResponse.getFlightRecord());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }));
    }


    private void handleResponse(List<FlightRecord> androidList) {

        flightsArrayList = new ArrayList<>(androidList);
        mAdapter = new DataAdapter(flightsArrayList, this);
        flightList.setAdapter(mAdapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FlightInfoListener) {
            mListener = (FlightInfoListener) context;


        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FlightInfoListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
