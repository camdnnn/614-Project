-- Clear tables
DELETE FROM Payments;
DELETE FROM Bookings;
DELETE FROM Flights;
DELETE FROM Users;
DELETE FROM MonthlyNews;

-- USERS
INSERT INTO Users (name, email, password, role) VALUES
('Alice Johnson', 'alice@example.com', 'password123', 'customer'),
('Bob Smith', 'bob@example.com', 'password123', 'customer'),
('Jane Agent', 'jane.agent@example.com', 'password123', 'agent'),
('Admin User', 'admin@example.com', 'securepass', 'admin');

-- FLIGHTS
INSERT INTO Flights (airline, origin, destination, departure, arrival, price, seats_available) VALUES
('Air Canada', 'Toronto', 'Vancouver', '2025-12-15 08:00:00', '2025-12-15 10:30:00', 350.00, 120),
('WestJet', 'Calgary', 'Toronto', '2025-12-18 14:00:00', '2025-12-18 19:00:00', 280.00, 95),
('Delta', 'New York', 'Los Angeles', '2025-12-20 09:00:00', '2025-12-20 12:00:00', 450.00, 150);

-- BOOKINGS
INSERT INTO Bookings (customer_id, flight_id, seat_number, booking_date, status) VALUES
(1, 1, '12A', '2025-11-01 15:00:00', 'confirmed'),
(2, 2, '8C', '2025-11-03 10:30:00', 'confirmed'),
(1, 3, '21F', '2025-11-05 12:00:00', 'pending');

-- PAYMENTS
INSERT INTO Payments (customer_id, booking_id, amount, payment_date, method) VALUES
(1, 1, 350.00, '2025-11-02 09:00:00', 'credit_card'),
(2, 2, 280.00, '2025-11-04 11:00:00', 'debit'),
(1, 3, 450.00, '2025-11-06 13:00:00', 'paypal');

-- MONTHLY NEWS
INSERT INTO MonthlyNews (title, content, publish_date) VALUES
('Holiday Travel Tips', 'Plan ahead to avoid delays and get the best deals.', '2025-11-01 00:00:00'),
('New Routes Added', 'We are excited to announce several new international routes!', '2025-12-01 00:00:00'),
('Winter Weather Advisory', 'Stay updated on winter travel conditions.', '2025-12-15 00:00:00');
