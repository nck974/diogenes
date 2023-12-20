-- Create Category
CREATE TABLE location (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    icon VARCHAR(50) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE location IS 'Location of the items';

-- Add link to location
ALTER TABLE item ADD location_id INT;

ALTER TABLE item ADD CONSTRAINT fk_location_id FOREIGN KEY (location_id) REFERENCES location(id);
