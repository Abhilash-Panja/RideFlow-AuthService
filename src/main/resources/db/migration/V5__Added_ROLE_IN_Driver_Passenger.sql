-- Migration for driver table
ALTER TABLE driver
    ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'DRIVER';

-- Migration for passenger table
ALTER TABLE passenger
    ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'PASSENGER';