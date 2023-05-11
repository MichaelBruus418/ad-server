# DB Collation: utf8mb4_unicode_ci

########################################################################################################################

# --- Drop tables if applicble ---
DROP TABLE IF EXISTS zone;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS advertiser;


# --- Define tables ---
CREATE TABLE IF NOT EXISTS publisher
(
    id   int UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);


CREATE TABLE IF NOT EXISTS zone
(
    id           int UNSIGNED NOT NULL AUTO_INCREMENT,
    publisher_id int UNSIGNED NOT NULL,
    name         varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);

CREATE TABLE IF NOT EXISTS advertiser
(
    id   int UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

########################################################################################################################

# --- Insert initial data ---
INSERT INTO Publisher
    (name)
VALUES ('Finans'),
       ('Jyllands-Posten'),
       ('Uge-Bladet'),
       ('Magasinet');

INSERT INTO Zone
    (publisher_id, name)
VALUES (1, 'sidebar'),
       (1, 'top'),
       (1, 'body'),
       (1, 'bottom'),
       (2, 'sidebar'),
       (3, 'sidebar'),
       (4, 'body');


########################################################################################################################
