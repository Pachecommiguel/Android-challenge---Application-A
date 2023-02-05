package com.example.applicationa.components;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationa.databinding.InputFrameBinding;
import com.example.applicationa.utils.IntentAction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputFrame extends AppCompatActivity {

    private InputFrameBinding binding;
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final SenderConverter receiver = new SenderConverter(this::sendBroadcast, queue);
    private final Thread receiverJob = new Thread(receiver);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        setReceiver();
        setListeners();
    }

    @Override
    protected void onDestroy() {
        receiverJob.interrupt();
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void setView() {
        binding = InputFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setReceiver() {
        IntentFilter intentFilter = new IntentFilter(IntentAction.MESSAGE_RESULT.getAction());
        registerReceiver(receiver, intentFilter);
        receiverJob.start();
    }

    private void setListeners() {
        binding.message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    queue.put(charSequence.toString());
                } catch (InterruptedException ignored) {}
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}