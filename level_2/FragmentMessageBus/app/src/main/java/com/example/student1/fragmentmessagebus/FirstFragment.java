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

// В любом другом месте, где нужно получать
// сообщения из EventBus, должна быть
// функция для приема сообщений, проаннотированная
// аннотицией @Subscribe

// Чтобы сберечь ресурсы, на EventBus желательно подписыватья
// когда фрагмент виден и отпиываться когда он не виден.

// Подписываться удобно в onAttach, отписываться в onDetach.

public class FirstFragment extends Fragment {

    private TextView receiveData;
    private EditText dataToSend;
    private Button sendData;

    /*//ссылка на кого-то, кто реализует интерфейс
    private FirstFragmentReceiver firstReceiver;

    public interface FirstFragmentReceiver {
        public void firstReceive(String data);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        receiveData = (TextView) view.findViewById(R.id.first_data);
        dataToSend = (EditText) view.findViewById(R.id.first_text);
        sendData = (Button) view.findViewById(R.id.first_post);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = dataToSend.getText().toString();
                /*//если есть ссылка на кого-то, кто реализует нужный интерфейс
                if(firstReceiver != null) {
                    firstReceiver.firstReceive(text);
                }*/
                EventBus.getDefault().post(
                        new Message(
                                // Класс отправителя - используется в качестве тега
                                // чтобы понимать, от кого пришли сообщения.
                                FirstFragment.this.getClass().getSimpleName(),
                                text
                        )
                );
            }
        });

        return view;
    }

    /*void dataReceived(String data) {
        receiveData.setText(data);
    }*/

    // Специальная аннотация
    // Сюда будут приходить сообщения
    @Subscribe
    public void onEvent(Message message)
    {
        // Если нужный отправитель, меняем текст
        // Нужно чтобы не показывать и свои сообщения тоже
        if(message.sender.equals(SecondFragment.class.getSimpleName()))
            receiveData.setText(message.message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*if(context instanceof FirstFragmentReceiver) {
            firstReceiver = (FirstFragmentReceiver) context;
        }*/

        // Подписываемся на шину
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //firstReceiver = null;

        // Отписываемся от шины
        EventBus.getDefault().unregister(this);
    }
}
