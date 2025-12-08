public class TransitRepository {

    private final TransitDao dao;

    public TransitRepository(Context context) {
        TransitDatabase db = TransitDatabase.getInstance(context);
        dao = db.transitDao();
    }

    public void getAllCities(Callback<List<City>> callback) {
        new Thread(() -> {
            List<City> cities = dao.getAllCities();
            callback.onResult(cities);
        }).start();
    }

    public void getRoutesForCity(int cityId, Callback<List<Route>> callback) {
        new Thread(() -> {
            List<Route> routes = dao.getRoutesForCity(cityId);
            callback.onResult(routes);
        }).start();
    }

    public interface Callback<T> {
        void onResult(T data);
    }
}