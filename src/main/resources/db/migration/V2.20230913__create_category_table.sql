-- Create Category
CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    color VARCHAR(6) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE category IS 'Categories of the items';

-- Add link to category
ALTER TABLE item ADD category_id INT;

ALTER TABLE item ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id);
