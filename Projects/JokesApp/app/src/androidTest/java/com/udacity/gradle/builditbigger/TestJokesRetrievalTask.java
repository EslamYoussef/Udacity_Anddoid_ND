package com.udacity.gradle.builditbigger;

import android.util.Log;

import com.udacity.gradle.builditbigger.listeners.JokeListener;
import com.udacity.gradle.builditbigger.tasks.JokesRetrievalTask;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertFalse;

/**
 * Created by Eslam on 10/10/2016.
 */

public class TestJokesRetrievalTask {
    private String mResult;

    @Test
    public void verifyAsyncTaskResult() {
        final CountDownLatch signal = new CountDownLatch(1);

        JokeListener jokeListener = new JokeListener() {

            @Override
            public void setJoke(String joke) {

            }

            @Override
            public void startLoading() {

            }

            @Override
            public void endLoading() {

            }
        };
        JokesRetrievalTask asyncTask = new JokesRetrievalTask(jokeListener) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                mResult = result;
                signal.countDown();
            }
        };
        asyncTask.execute();
        try {
            signal.await();
        } catch (InterruptedException e) {
            Log.v("AsyncTaskTest", e.getMessage());
        }
        Log.v("Joke", mResult);
        assertFalse(mResult.isEmpty());
    }
}