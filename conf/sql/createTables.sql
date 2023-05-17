# DB Collation: utf8mb4_unicode_ci

########################################################################################################################

# --- Drop tables if applicble ---
DROP TABLE IF EXISTS banner;
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
    id           int UNSIGNED                              NOT NULL AUTO_INCREMENT,
    publisher_id int UNSIGNED                              NOT NULL,
    name         ENUM ('top', 'body', 'bottom', 'sidebar') NOT NULL,
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

CREATE TABLE IF NOT EXISTS banner
(
    id                  int UNSIGNED NOT NULL AUTO_INCREMENT,
    zone_id             int UNSIGNED NOT NULL,
    advertiser_id       int UNSIGNED NOT NULL,
    name                varchar(255) NOT NULL,
    start_datetime      datetime     NOT NULL,
    end_datetime        datetime     NOT NULL,
    target_num_of_views int          NOT NULL,
    num_of_views        int          NOT NULL DEFAULT 0,
    num_of_dispatches   int          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (advertiser_id) REFERENCES advertiser (id),
    FOREIGN KEY (zone_id) REFERENCES zone (id)
);


########################################################################################################################

# ---Initial data ---
INSERT INTO publisher
    (name)
VALUES ('Finans'),
       ('Jyllands-Posten'),
       ('Uge-Bladet'),
       ('Magasinet')
;

INSERT INTO zone
    (publisher_id, name)
VALUES ((SELECT id FROM publisher where name = 'Finans'), 'sidebar'),
       ((SELECT id FROM publisher where name = 'Finans'), 'top'),
       ((SELECT id FROM publisher where name = 'Finans'), 'body'),
       ((SELECT id FROM publisher where name = 'Finans'), 'bottom'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'), 'sidebar'),
       ((SELECT id FROM publisher where name = 'Uge-Bladet'), 'sidebar'),
       ((SELECT id FROM publisher where name = 'Magasinet'), 'body')
;

INSERT INTO advertiser
    (name)
VALUES ('Cane Line'),
       ('Københavns Listefabrik'),
       ('Læger uden grænser'),
       ('Nordicals'),
       ('Small Danish Hotels')
;

SET @publisher = 'Jyllands-Posten';
INSERT INTO banner
(zone_id, advertiser_id, name, start_datetime, end_datetime, target_num_of_views)
VALUES (
           # Zone id
           (SELECT id
            FROM zone
            where name = 'sidebar'
              AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
           # Advertiser id
           (SELECT id FROM advertiser where name = 'Cane Line'),
           'Dansk Design',
           # Start
           '2013-05-01 00:00:00',
           # End
           '2013-06-01 00:00:00',
           # Target num-of-views
           100),
       (
           # Zone id
           (SELECT id
            FROM zone
            where name = 'sidebar'
              AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
           # Advertiser id
           (SELECT id FROM advertiser where name = 'Københavns ListeFabrik'),
           'generic',
           # Start
           '2013-05-01 00:00:00',
           # End
           '2013-06-01 00:00:00',
           # Target num-of-views
           100),
       (
           # Zone id
           (SELECT id
            FROM zone
            where name = 'sidebar'
              AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
           # Advertiser id
           (SELECT id FROM advertiser where name = 'Læger uden grænser'),
           'generic',
           # Start
           '2013-05-01 00:00:00',
           # End
           '2013-06-01 00:00:00',
           # Target num-of-views
           100)
;


########################################################################################################################
