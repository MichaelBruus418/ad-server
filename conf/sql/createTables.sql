# DB Collation: utf8mb4_unicode_ci

########################################################################################################################

# --- Drop tables if applicble ---
DROP TABLE IF EXISTS creative;
DROP TABLE IF EXISTS zone;
DROP TABLE IF EXISTS campaign;
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
    id           int UNSIGNED      NOT NULL AUTO_INCREMENT,
    publisher_id int UNSIGNED      NOT NULL,
    name         varchar(255)      NOT NULL,
    minWidth     smallint UNSIGNED NOT NULL,
    minHeight    smallint UNSIGNED NOT NULL,
    maxWidth     smallint UNSIGNED NOT NULL,
    maxHeight    smallint UNSIGNED NOT NULL,
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

CREATE TABLE IF NOT EXISTS campaign
(
    id             int UNSIGNED NOT NULL AUTO_INCREMENT,
    publisher_id   int UNSIGNED NOT NULL,
    advertiser_id  int UNSIGNED NOT NULL,
    name           varchar(255) NOT NULL,
    active         boolean      NOT NULL,
    start_datetime datetime     NOT NULL,
    end_datetime   datetime     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id),
    FOREIGN KEY (advertiser_id) REFERENCES advertiser (id)
);

CREATE TABLE IF NOT EXISTS creative
(
    id           int UNSIGNED                              NOT NULL AUTO_INCREMENT,
    campaign_id  int UNSIGNED                              NOT NULL,
    filename     varchar(255)                              NOT NULL,
    hash         varchar(255)                              NOT NULL,
    active       boolean                                   NOT NULL,
    width        smallint UNSIGNED                         NOT NULL,
    height       smallint UNSIGNED                         NOT NULL,
    served       int UNSIGNED                              NOT NULL DEFAULT 0,
    downloaded   int UNSIGNED                              NOT NULL DEFAULT 0,
    viewable     int UNSIGNED                              NOT NULL DEFAULT 0,
    targetMetric ENUM ('served', 'downloaded', 'viewable') NOT NULL,
    targetValue  int UNSIGNED                              NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (campaign_id) REFERENCES campaign (id)
);


########################################################################################################################

# ---Initial data ---
INSERT INTO publisher
    (name)
VALUES ('Finans'),
       ('Jyllands-Posten'),
       ('Uge-Bladet'),
       ('Lokalavisen')
;

INSERT INTO zone
    (publisher_id, name, minWidth, minHeight, maxWidth, maxHeight)
VALUES ((SELECT id FROM publisher where name = 'Finans'),
        'sidebar',
        160, 250,
        300, 600),
       ((SELECT id FROM publisher where name = 'Finans'),
        'top',
        930, 930,
        180, 180),
       ((SELECT id FROM publisher where name = 'Finans'),
        'body',
        930, 930,
        180, 600),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        'sidebar',
        160, 250,
        300, 600),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        'body',
        930, 930,
        180, 600),
       ((SELECT id FROM publisher where name = 'Lokalavisen'),
        'top',
        930, 930,
        180, 180),
       ((SELECT id FROM publisher where name = 'Lokalavisen'),
        'body',
        930, 930,
        180, 600)
;

INSERT INTO advertiser
    (name)
VALUES ('Cane Line'),
       ('Københavns Listefabrik'),
       ('Læger uden grænser'),
       ('Nordicals'),
       ('Small Danish Hotels'),
       ('Mercury Motor'),
       ('SJEC Danmark'),
       ('Tryg Forsikring')
;

INSERT INTO campaign
    (publisher_id, advertiser_id, name, active, start_datetime, end_datetime)
VALUES (
        (SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Cane Line'),
        'Cane Line Campaign',
        false,
        '2013-05-01 00:00:00',
        '2013-06-01 00:00:00'
        ),
       (
        (SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Københavns Listefabrik'),
        'Københavns Listefabrik Campaign',
        true,
        '2013-05-01 00:00:00',
        '2013-06-01 00:00:00'
        ),
       (
        (SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Læger uden grænser'),
        'Læger uden grænser Campaign',
        true,
        '2013-05-01 00:00:00',
        '2013-06-01 00:00:00'
        ),
       (
        (SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Mercury Motor'),
        'Mercury Motor Campaign',
        true,
        '2013-05-01 00:00:00',
        '2013-06-01 00:00:00'
        ),
       (
        (SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'SJEC Danmark'),
        'SJEC Danmark Campaign',
        true,
        '2013-05-01 00:00:00',
        '2013-06-01 00:00:00'
        ),
       (
        (SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Tryg Forsikring'),
        'Tryg Forsikring Campaign',
        true,
        '2013-05-01 00:00:00',
        '2013-06-01 00:00:00'
        )
;


SET @publisher = 'Jyllands-Posten';
INSERT INTO creative
(campaign_id, filename, hash , active, width, height, targetMetric, targetValue)
VALUES ((SELECT id
         FROM campaign
         where name = 'Københavns Listefabrik Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'half_page_300x600.html',
        'fa66bd2dc50c024b60c593ec8aecf67c',
        false,
        300,
        600,
        'downloaded',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Læger uden grænser Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'medium_rectangle_300x250.html',
        'a8a811a923772a31c0433af84536d479',
        true,
        300,
        250,
        'downloaded',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Læger uden grænser Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_930x600.html',
        'f54fa8ec17f218b2bda17c7580eec1c8',
        true,
        930,
        600,
        'downloaded',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Læger uden grænser Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'wide_skyscraper_160x600.html',
        '677bee18872b7c6c4f2b50b7facc7805',
        true,
        160,
        600,
        'downloaded',
        100)
;


########################################################################################################################
