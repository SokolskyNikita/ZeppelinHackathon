package aviation.recoding.zeppelin.ui.flights;

/**
 * Created by Hanson on 18/06/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.model.FlightRecord;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {

    private List<FlightRecord> flightRecords;
    final FlightSelectionFragment listener;

    protected List<FlightRecord> tempList;


    public DataAdapter(ArrayList<FlightRecord> androidList, FlightSelectionFragment fragment) {
        flightRecords = androidList;
        listener = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        FlightRecord flightRecord = flightRecords.get(position);
        holder.mCityName.setText(flightRecord.getCity());
        holder.mScheduledTime.setText(flightRecord.getScheduled());
        holder.mStatusText.setText(flightRecord.getStatusText());
        holder.mCityFlight.setText(flightRecord.getAircraft());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return flightRecords.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tempList = (List<FlightRecord>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<FlightRecord> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = flightRecords;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

     public List<FlightRecord> getFilteredResults(String constraint) {
        List<FlightRecord> results = new ArrayList<>();

        for (FlightRecord item : flightRecords) {
            if (item.getCity().toLowerCase().contains(constraint.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        int position;
        private TextView mCityName, mScheduledTime, mStatusText, mCityFlight;

        ViewHolder(View view) {
            super(view);

            mCityName = (TextView)view.findViewById(R.id.city_line);
            mScheduledTime = (TextView)view.findViewById(R.id.time);
            mStatusText = (TextView)view.findViewById(R.id.status);
            mCityFlight = (TextView)view.findViewById(R.id.flight_line);
            mCityName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelected(position);
                }
            });
        }

    }
}