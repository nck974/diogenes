-- Create updated on and created on to the table of item
ALTER TABLE item ADD created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE item ADD updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

