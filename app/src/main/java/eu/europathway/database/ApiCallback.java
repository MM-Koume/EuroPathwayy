package eu.europathway.database;

public interface ApiCallback<T> {
    void onSuccess(T data);
    void onError(Throwable error);
}
