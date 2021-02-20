package su.strannik.colorselector_fragment;

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

public class ColorAdapter extends ArrayAdapter<ColorItem> {

    private List<ColorItem> colors;

    static class ViewHolder {
        TextView colorName;
        FrameLayout colorBackground;
    }

    public ColorAdapter(@NonNull Context context, int resource, List<ColorItem> colors) {
        super(context, resource);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return this.colors.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        ViewHolder holder = new ViewHolder();
        ColorItem item = colors.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder.colorName = convertView.findViewById(R.id.colorSelector);
            holder.colorBackground = convertView.findViewById(R.id.item);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder != null) {
            holder.colorName.setText(item.colorName);
            holder.colorBackground.setBackgroundColor(item.color);
        }

        return convertView;
    }
}
