-- Add booking reference to review table
ALTER TABLE booking_review
    ADD COLUMN booking_id BIGINT NULL;

-- Backfill from the old direction before it's removed
UPDATE booking_review br
    JOIN booking b ON b.review_id = br.id
SET br.booking_id = b.id;

-- Now safe to enforce NOT NULL
ALTER TABLE booking_review
    MODIFY COLUMN booking_id BIGINT NOT NULL;

ALTER TABLE booking_review
    ADD CONSTRAINT uk_review_booking
        UNIQUE (booking_id);

ALTER TABLE booking_review
    ADD CONSTRAINT fk_review_booking
        FOREIGN KEY (booking_id)
            REFERENCES booking(id);

-- Remove review reference from booking table
ALTER TABLE booking
    DROP FOREIGN KEY fk_booking_review;

ALTER TABLE booking
    DROP INDEX uk_booking_review;

ALTER TABLE booking
    DROP COLUMN review_id;