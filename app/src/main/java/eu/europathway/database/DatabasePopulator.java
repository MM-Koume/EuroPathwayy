package eu.europathway.database;

import android.content.Context;
import eu.europathway.database.entities.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to populate the database with initial data
 */
public class DatabasePopulator {

    private final TransitRepository repository;

    public DatabasePopulator(Context context) {
        this.repository = new TransitRepository(context);
    }

    /**
     * METHOD 1: Add sample cities manually
     * Call this when app first starts
     */
    public void populateSampleCities() {
        // Create cities
        City berlin = new City("Berlin", "Germany", "Europe/Berlin",
                52.5200, 13.4050, "EUR");
        City paris = new City("Paris", "France", "Europe/Paris",
                48.8566, 2.3522, "EUR");
        City madrid = new City("Madrid", "Spain", "Europe/Madrid",
                40.4168, -3.7038, "EUR");
        City rome = new City("Rome", "Italy", "Europe/Rome",
                41.9028, 12.4964, "EUR");
        City warsaw = new City("Warsaw", "Poland", "Europe/Warsaw",
                52.2297, 21.0122, "PLN");
        City amsterdam = new City("Amsterdam", "Netherlands", "Europe/Amsterdam",
                52.3676, 4.9041, "EUR");
        City vienna = new City("Vienna", "Austria", "Europe/Vienna",
                48.2082, 16.3738, "EUR");

        // Insert into database
        repository.insertCity(berlin);
        repository.insertCity(paris);
        repository.insertCity(madrid);
        repository.insertCity(rome);
        repository.insertCity(warsaw);
        repository.insertCity(amsterdam);
        repository.insertCity(vienna);
    }

    /**
     * METHOD 2: Add stops for a specific city
     * Example: Berlin Metro stops
     */
    public void populateBerlinStops() {
        List<Stop> berlinStops = new ArrayList<>();

        // Alexanderplatz
        Stop alex = new Stop();
        alex.cityId = 1; // Berlin's ID
        alex.stopCode = "ALEX";
        alex.stopName = "Alexanderplatz";
        alex.stopDesc = "Major transportation hub in central Berlin";
        alex.latitude = 52.5219;
        alex.longitude = 13.4132;
        alex.zoneId = "A";
        alex.wheelchairAccessible = 1;
        berlinStops.add(alex);

        // Hauptbahnhof (Central Station)
        Stop hbf = new Stop();
        hbf.cityId = 1;
        hbf.stopCode = "HBF";
        hbf.stopName = "Hauptbahnhof";
        hbf.stopDesc = "Berlin Central Station";
        hbf.latitude = 52.5250;
        hbf.longitude = 13.3694;
        hbf.zoneId = "A";
        hbf.wheelchairAccessible = 1;
        berlinStops.add(hbf);

        // Potsdamer Platz
        Stop potsdamer = new Stop();
        potsdamer.cityId = 1;
        potsdamer.stopCode = "POTSPL";
        potsdamer.stopName = "Potsdamer Platz";
        potsdamer.latitude = 52.5096;
        potsdamer.longitude = 13.3761;
        potsdamer.zoneId = "A";
        potsdamer.wheelchairAccessible = 1;
        berlinStops.add(potsdamer);

        // Brandenburger Tor
        Stop brandenburg = new Stop();
        brandenburg.cityId = 1;
        brandenburg.stopCode = "BRTOR";
        brandenburg.stopName = "Brandenburger Tor";
        brandenburg.latitude = 52.5163;
        brandenburg.longitude = 13.3777;
        brandenburg.zoneId = "A";
        brandenburg.wheelchairAccessible = 1;
        berlinStops.add(brandenburg);

        // Insert all stops
        repository.insertStops(berlinStops);
    }

    /**
     * METHOD 3: Add routes/lines for a city
     * Example: Berlin U-Bahn lines
     */
    public void populateBerlinRoutes() {
        List<Route> routes = new ArrayList<>();

        // First, you need an agency
        // You'd insert this first, then get its ID
        // For this example, assume agency_id = 1 (BVG)

        // U1 Line
        Route u1 = new Route();
        u1.agencyId = 1;
        u1.routeShortName = "U1";
        u1.routeLongName = "Warschauer Straße - Uhlandstraße";
        u1.routeType = 1; // Subway
        u1.routeColor = "#55A22A";
        u1.routeTextColor = "#FFFFFF";
        routes.add(u1);

        // U2 Line
        Route u2 = new Route();
        u2.agencyId = 1;
        u2.routeShortName = "U2";
        u2.routeLongName = "Pankow - Ruhleben";
        u2.routeType = 1; // Subway
        u2.routeColor = "#DA421E";
        u2.routeTextColor = "#FFFFFF";
        routes.add(u2);

        // S-Bahn S1
        Route s1 = new Route();
        s1.agencyId = 1;
        s1.routeShortName = "S1";
        s1.routeLongName = "Wannsee - Oranienburg";
        s1.routeType = 2; // Rail
        s1.routeColor = "#DE5285";
        s1.routeTextColor = "#FFFFFF";
        routes.add(s1);

        // Bus M19
        Route m19 = new Route();
        m19.agencyId = 1;
        m19.routeShortName = "M19";
        m19.routeLongName = "S+U Mehringdamm - S Grunewald";
        m19.routeType = 3; // Bus
        m19.routeColor = "#A3007C";
        m19.routeTextColor = "#FFFFFF";
        routes.add(m19);

        repository.insertRoutes(routes);
    }

    /**
     * METHOD 4: Add fare information
     */
    public void populateBerlinFares() {
        List<Fare> fares = new ArrayList<>();

        // Single ticket AB
        Fare singleAB = new Fare();
        singleAB.cityId = 1;
        singleAB.fareName = "Single Ticket AB";
        singleAB.fareType = "single";
        singleAB.price = 3.20;
        singleAB.currency = "EUR";
        singleAB.validDuration = 120; // 2 hours
        singleAB.zonesCovered = "A,B";
        fares.add(singleAB);

        // Day ticket AB
        Fare dayAB = new Fare();
        dayAB.cityId = 1;
        dayAB.fareName = "Day Ticket AB";
        dayAB.fareType = "day";
        dayAB.price = 8.80;
        dayAB.currency = "EUR";
        dayAB.validDuration = 1440; // 24 hours
        dayAB.zonesCovered = "A,B";
        fares.add(dayAB);

        // Weekly ticket
        Fare weeklyAB = new Fare();
        weeklyAB.cityId = 1;
        weeklyAB.fareName = "7-Day Ticket AB";
        weeklyAB.fareType = "week";
        weeklyAB.price = 36.00;
        weeklyAB.currency = "EUR";
        weeklyAB.validDuration = 10080; // 7 days in minutes
        weeklyAB.zonesCovered = "A,B";
        fares.add(weeklyAB);

        // Monthly ticket
        Fare monthlyAB = new Fare();
        monthlyAB.cityId = 1;
        monthlyAB.fareName = "Monthly Ticket AB";
        monthlyAB.fareType = "month";
        monthlyAB.price = 86.00;
        monthlyAB.currency = "EUR";
        monthlyAB.validDuration = 43200; // 30 days
        monthlyAB.zonesCovered = "A,B";
        fares.add(monthlyAB);

        repository.insertFare(fares.get(0));
        repository.insertFare(fares.get(1));
        repository.insertFare(fares.get(2));
        repository.insertFare(fares.get(3));
    }

    /**
     * METHOD 5: Populate everything at once
     * Call this on first app launch
     */
    public void populateAllData() {
        populateSampleCities();

        // Wait a bit for cities to be inserted
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        populateBerlinStops();
        populateBerlinRoutes();
        populateBerlinFares();
    }

    /**
     * METHOD 6: Check if database is already populated
     * Use this to avoid duplicate data
     */
    public void populateIfEmpty(Context context) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            TransitDatabase db = TransitDatabase.getDatabase(context);

            // Check if cities table is empty
            City existingCity = db.cityDao().getCityByName("Berlin");

            if (existingCity == null) {
                // Database is empty, populate it
                populateAllData();
            }
        });
    }
}