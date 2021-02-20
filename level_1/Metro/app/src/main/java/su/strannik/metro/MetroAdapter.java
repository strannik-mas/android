package su.strannik.metro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MetroAdapter extends ArrayAdapter<Station> {

    private List<Station> stations;

    static class ViewHolder {
        TextView stationName;
        FrameLayout frameStation;
    }

    public MetroAdapter(@NonNull Context context, int resource, List<Station> stations) {
        super(context, resource);
        this.stations = stations;
    }

    @Override
    public int getCount() {
        return this.stations.size();    //сколько элементов отображать
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        ViewHolder holder = new ViewHolder();
        Station station = stations.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder.stationName = convertView.findViewById(R.id.station);
            holder.frameStation = convertView.findViewById(R.id.item);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.stationName.setText(station.name);
        holder.frameStation.setBackgroundColor(station.color);

        return convertView;
    }
}
