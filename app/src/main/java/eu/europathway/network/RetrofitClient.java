package eu.europathway.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// ============================================
// RETROFIT CLIENT
// ============================================
public class RetrofitClient {

    // For XAMPP localhost:
    // - Android Emulator: use "http://10.0.2.2/transit-api/"
    // - Physical Device on same network: use your PC's IP like "http://192.168.1.100/transit-api/"
    private static final String BASE_URL = "http://192.168.140.216/transit-api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static TransitApiService getApiService() {
        return getClient().create(TransitApiService.class);
    }
}

// ============================================
// UPDATED REPOSITORY WITH API SYNC
// ============================================
/*
Update your TransitRepository.java to use the API:


*/