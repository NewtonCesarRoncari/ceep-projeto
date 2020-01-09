package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

public class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private final ExecuteListener<T> executeListener;
    private final FinishedListener<T> finishedListener;

    public BaseAsyncTask(ExecuteListener<T> executarListener, FinishedListener<T> finalizadoListener) {
        this.executeListener = executarListener;
        this.finishedListener = finalizadoListener;
    }

    @Override
    protected T doInBackground(Void... voids) {
        return executeListener.whenExecute();
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        finishedListener.quandoFinalizado(result);
    }

    public interface ExecuteListener<T> {
        T whenExecute();
    }

    public interface FinishedListener<T> {
        void quandoFinalizado(T resultado);
    }

}
