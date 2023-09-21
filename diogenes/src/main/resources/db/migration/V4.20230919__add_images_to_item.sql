-- Add field to store the path of the images in the database
ALTER TABLE
    item
ADD
    COLUMN image_path VARCHAR(255) NULL;