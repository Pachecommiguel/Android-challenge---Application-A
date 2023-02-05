package com.example.applicationa.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.applicationa.utils.IntentAction;
import com.example.applicationa.utils.StringUtils;

import java.util.concurrent.BlockingQueue;

public class SenderConverter extends BroadcastReceiver implements Runnable {

    private final MessageListener listener;
    private final BlockingQueue<String> queue;

    public SenderConverter(MessageListener listener, BlockingQueue<String> queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (IntentAction.MESSAGE_RESULT.getAction().equals(intent.getAction())) {
            boolean result = intent.getBooleanExtra(IntentAction.RESULT.getAction(), false);
            Log.e("SenderConverter -> ", "Message shown " + result);
        }
    }

    @Override
    public void run() {
        try {
            String message = queue.take();
            Intent intent = new Intent(IntentAction.MESSAGE_SEND.getAction()).putExtra(
                    IntentAction.MESSAGE.getAction(),
                    StringUtils.UTF8ToHex(message)
            );

            listener.onMessage(intent);

            if (!isInterrupted()) {
                run();
            }

        } catch (InterruptedException ignored) {
            // Expected if current thread is waiting on "queue.take()" and gets interrupted
        }
    }

    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted() && queue.isEmpty();
    }

    interface MessageListener {
        void onMessage(Intent intent);
    }
}
