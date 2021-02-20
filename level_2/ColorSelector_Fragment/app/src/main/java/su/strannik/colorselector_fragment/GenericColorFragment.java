package su.strannik.colorselector_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GenericColorFragment extends Fragment {

    // Параметры view, которые будем менять
    private int backgroundColor;
    private String title;

    // Ключи для сохранения настроек
    private static final String FRAGMENT_COLOR = "FRAGMENT_COLOR";
    private static final String FRAGMENT_TITLE = "FRAGMENT_TITLE";

    public static GenericColorFragment newInstance(int color, String colorName) {
        GenericColorFragment fragment = new GenericColorFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_COLOR, color);
        bundle.putString(FRAGMENT_TITLE, colorName);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(FRAGMENT_COLOR)) {
                backgroundColor = bundle.getInt(FRAGMENT_COLOR);
            }
            if (bundle.containsKey(FRAGMENT_TITLE)) {
                title = bundle.getString(FRAGMENT_TITLE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.generic_fragment);
        relativeLayout.setBackgroundColor(backgroundColor);
        TextView text = (TextView) view.findViewById(R.id.title_text);
        text.setText(title);

        return view;
    }
}
