
import java.time.LocalDateTime;
import java.util.List;

import com.flightreservation.business.controller.AppController;
import com.flightreservation.business.service.BookingService;
import com.flightreservation.business.service.FlightService;
import com.flightreservation.business.service.PaymentService;
import com.flightreservation.business.service.UserService;
import com.flightreservation.data.repository.memory.InMemoryRepository;
import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;
import com.flightreservation.model.Payment;
import com.flightreservation.model.Role;
import com.flightreservation.model.User;

/**
 * Small demo that wires the business layer to in-memory repositories
 * and exercises a few operations.
 */
public final class AppDemo {

    public static void main(String[] args) {
        InMemoryRepository<Flight> flightRepo = new InMemoryRepository<>(Flight::getId);
        InMemoryRepository<User> userRepo = new InMemoryRepository<>(User::getId);
        InMemoryRepository<Booking> bookingRepo = new InMemoryRepository<>(Booking::getId);
        InMemoryRepository<Payment> paymentRepo = new InMemoryRepository<>(Payment::getId);

        BookingService bookingService = new BookingService(bookingRepo);
        PaymentService paymentService = new PaymentService(paymentRepo, bookingService);
        FlightService flightService = new FlightService(flightRepo);
        UserService userService = new UserService(userRepo);

        AppController app = AppController.init(paymentService, bookingService, flightService, userService);

        // Seed flights, users, and a booking.
        app.addFlight(new Flight(1, "AC", "YYC", "YVR", "2025-01-10T09:00", "2025-01-10T10:00", 250.0f, 50));
        app.addFlight(new Flight(2, "WS", "YYC", "YYZ", "2025-01-12T11:00", "2025-01-12T16:30", 320.0f, 40));
        app.createUser(new User(1, "Avi Agent", "agent@example.com", "secret", Role.AGENT));
        app.createUser(new User(2, "Chris Customer", "customer@example.com", "p@ssw0rd", Role.CUSTOMER));
        bookingService.addBooking(new Booking(1, 2, 1, "12A", LocalDateTime.parse("2025-01-01T12:00:00"), "NEW"));

        System.out.println("Flights:");
        printFlights(app.getAllFlights());

        System.out.println("\nUsers:");
        printUsers(app.getAllUsers());

        System.out.println("\nBooking before payment:");
        printBookings(app.getAllBookings());

        System.out.println("\nRecording payment and marking booking as paid...");
        app.payForBooking(new Payment(1, 2, 1, 250.0, LocalDateTime.parse("2024-12-15T09:00:00"), "CARD"));

        System.out.println("\nBookings after payment:");
        printBookings(app.getAllBookings());
    }

    private static void printFlights(List<Flight> flights) {
        for (Flight f : flights) {
            System.out.println(" - " + f.getId() + " " + f.getAirline()
                    + " " + f.getOrigin() + "->" + f.getDestination()
                    + " $" + f.getPrice() + " seats=" + f.getAvailableSeats());
        }
    }

    private static void printUsers(List<User> users) {
        for (User u : users) {
            System.out.println(" - " + u.getId() + " " + u.getName()
                    + " (" + u.getRole() + ") " + u.getEmail());
        }
    }

    private static void printBookings(List<Booking> bookings) {
        for (Booking b : bookings) {
            System.out.println(" - booking " + b.getId()
                    + " customer=" + b.getCustomerId()
                    + " flight=" + b.getFlightId()
                    + " seat=" + b.getSeatNumber()
                    + " status=" + b.getStatus());
        }
    }

    private AppDemo() {
    }
}
