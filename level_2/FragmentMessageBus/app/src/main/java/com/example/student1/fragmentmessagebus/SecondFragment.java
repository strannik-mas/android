package com.example.student1.fragmentmessagebus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


// Комментарии в первом фрагменте
public class SecondFragment extends Fragment {

    private TextView receiveData;
    private EditText dataToSend;
    private Button sendData;
    /*private SecondFragmentReceiver secondReceiver;

    public interface SecondFragmentReceiver {
        public void secondReceive(String data);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        receiveData = (TextView) view.findViewById(R.id.second_data);
        dataToSend = (EditText) view.findViewById(R.id.second_text);
        sendData = (Button) view.findViewById(R.id.second_post);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(secondReceiver != null)
                {
                    String text = dataToSend.getText().toString();
                    secondReceiver.secondReceive(text);
                }*/
                String text = dataToSend.getText().toString();
                EventBus.getDefault().post(
                        new Message(
                                SecondFragment.this.getClass().getSimpleName(),
                                text
                        )
                );
            }
        });

        return view;
    }

    /*void dataReceived(String data)
    {
        receiveData.setText(data);
    }*/

    @Subscribe
    public void onEvent(Message message)
    {
        if(message.sender.equals(FirstFragment.class.getSimpleName()))
            receiveData.setText(message.message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if(context instanceof SecondFragmentReceiver)
        {
            secondReceiver = (SecondFragmentReceiver) context;
        }*/
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //secondReceiver = null;
        EventBus.getDefault().unregister(this);
    }
}
