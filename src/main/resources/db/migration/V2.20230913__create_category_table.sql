-- Create Category
CREATE TABLE category (
    id serial PRIMARY KEY,
    name VARCHAR (100) UNIQUE NOT NULL,
    description TEXT,
    color VARCHAR (6) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE diogenes.category IS 'Categories of the items';

-- Add link to category
ALTER TABLE
    item
ADD
    category_id INT,
ADD
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id),
ADD
    CONSTRAINT uk_category_id UNIQUE (category_id);