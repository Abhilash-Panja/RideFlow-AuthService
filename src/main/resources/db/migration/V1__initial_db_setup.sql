CREATE TABLE driver (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        created_date DATE NOT NULL,
                        last_updated_date DATETIME NOT NULL,

                        driver_name VARCHAR(255),
                        licence_number VARCHAR(255),

                        PRIMARY KEY (id)
);
CREATE TABLE passenger (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           created_date DATE NOT NULL,
                           last_updated_date DATETIME NOT NULL,

                           passenger_name VARCHAR(255),

                           PRIMARY KEY (id)
);
CREATE TABLE booking_review (
                                id BIGINT NOT NULL AUTO_INCREMENT,
                                created_date DATE NOT NULL,
                                last_updated_date DATETIME NOT NULL,

                                description VARCHAR(255) NOT NULL,
                                rating DOUBLE NOT NULL,

                                PRIMARY KEY (id)
);
CREATE TABLE passenger_review (
                                  id BIGINT NOT NULL,

                                  passenger_review_content VARCHAR(255) NOT NULL,
                                  passenger_rating DOUBLE NOT NULL,

                                  PRIMARY KEY (id),

                                  CONSTRAINT fk_passenger_review_review
                                      FOREIGN KEY (id)
                                          REFERENCES booking_review(id)
);
CREATE TABLE booking (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         created_date DATE NOT NULL,
                         last_updated_date DATETIME NOT NULL,

                         start_time TIME,
                         end_time TIME,
                         total_distance BIGINT,

                         booking_status ENUM(
                             'STARTED',
                             'CANCELED',
                             'CAB_ARRIVED',
                             'ASSIGNED_DRIVER',
                             'IN_RIDE',
                             'COMPLETED'
                             ),

                         review_id BIGINT,
                         driver_id BIGINT,
                         passenger_id BIGINT,

                         PRIMARY KEY (id),

                         UNIQUE KEY uk_booking_review (review_id),

                         CONSTRAINT fk_booking_review
                             FOREIGN KEY (review_id)
                                 REFERENCES booking_review(id),

                         CONSTRAINT fk_booking_driver
                             FOREIGN KEY (driver_id)
                                 REFERENCES driver(id),

                         CONSTRAINT fk_booking_passenger
                             FOREIGN KEY (passenger_id)
                                 REFERENCES passenger(id)
);