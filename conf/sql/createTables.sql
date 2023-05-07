# DB Collation: utf8mb4_unicode_ci

########################################################################################################################

# --- Drop tables if applicble ---
DROP TABLE IF EXISTS zone;
DROP TABLE IF EXISTS publisher;


# --- Define tables ---
CREATE TABLE IF NOT EXISTS publisher
(
    id   int          UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS zone
(
    id           int UNSIGNED NOT NULL AUTO_INCREMENT,
    publisher_id int UNSIGNED NOT NULL,
    name         varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);





########################################################################################################################

# --- Insert initial data ---
INSERT INTO Publisher
    (name)
VALUES ('Chuck Norris'),
       ('Jean-Claude Van Damme'),
       ('Steven Seagal'),
       ('Jet Li'),
       ('Bruce Lee');

########################################################################################################################
