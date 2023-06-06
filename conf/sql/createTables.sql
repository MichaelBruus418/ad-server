# DB Collation: utf8mb4_unicode_ci

########################################################################################################################

# --- Drop tables if applicable ---
DROP TABLE IF EXISTS creative;
DROP TABLE IF EXISTS zone;
DROP TABLE IF EXISTS campaign;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS advertiser;


# --- Define tables ---
CREATE TABLE IF NOT EXISTS publisher
(
    id   bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255)    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);



CREATE TABLE IF NOT EXISTS zone
(
    id           bigint UNSIGNED   NOT NULL AUTO_INCREMENT,
    publisher_id bigint UNSIGNED   NOT NULL,
    name         varchar(255)      NOT NULL,
    minWidth     smallint UNSIGNED NOT NULL,
    minHeight    smallint UNSIGNED NOT NULL,
    maxWidth     smallint UNSIGNED NOT NULL,
    maxHeight    smallint UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id),
    UNIQUE (publisher_id, name)
);

CREATE TABLE IF NOT EXISTS advertiser
(
    id   bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(255)    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS campaign
(
    id             bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    publisher_id   bigint UNSIGNED NOT NULL,
    advertiser_id  bigint UNSIGNED NOT NULL,
    name           varchar(255)    NOT NULL,
    disabled       boolean         NOT NULL,
    start_datetime datetime        NOT NULL,
    end_datetime   datetime        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id),
    FOREIGN KEY (advertiser_id) REFERENCES advertiser (id)
);

CREATE TABLE IF NOT EXISTS creative
(
    id               bigint UNSIGNED                           NOT NULL AUTO_INCREMENT,
    campaign_id      bigint UNSIGNED                           NOT NULL,
    filepath         varchar(255)                              NOT NULL,
    hash             varchar(255)                              NOT NULL,
    disabled         boolean                                   NOT NULL,
    width            smallint UNSIGNED                         NOT NULL,
    height           smallint UNSIGNED                         NOT NULL,
    served           int UNSIGNED                              NOT NULL DEFAULT 0,
    downloaded       int UNSIGNED                              NOT NULL DEFAULT 0,
    viewable         int UNSIGNED                              NOT NULL DEFAULT 0,
    impressionMetric ENUM ('served', 'downloaded', 'viewable') NOT NULL,
    impressionTarget int UNSIGNED                              NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (campaign_id) REFERENCES campaign (id),
    UNIQUE (hash)
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
        930, 180,
        930, 180),
       ((SELECT id FROM publisher where name = 'Finans'),
        'body',
        930, 180,
        930, 600),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        'sidebar',
        160, 250,
        300, 600),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        'body',
        930, 180,
        990, 600),
       ((SELECT id FROM publisher where name = 'Lokalavisen'),
        'top',
        930, 180,
        930, 180),
       ((SELECT id FROM publisher where name = 'Lokalavisen'),
        'body',
        930, 180,
        930, 600)
;

INSERT INTO advertiser
    (name)
VALUES ('Jyllands-Posten'),
       ('Cane Line'),
       ('Københavns Listefabrik'),
       ('Læger uden grænser'),
       ('Nordicals'),
       ('Small Danish Hotels'),
       ('Mercury Motor'),
       ('SJEC Danmark'),
       ('Tryg Forsikring')
;

INSERT INTO campaign
(publisher_id, advertiser_id, name, disabled, start_datetime, end_datetime)
VALUES ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Jyllands-Posten'),
        'Jyllands-Posten Campaign',
        false,
        '2000-01-01 00:00:00',
        '2100-01-01 00:00:00'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Cane Line'),
        'Cane Line Campaign',
        true,
        '2023-05-01 00:00:00',
        '2024-06-01 00:00:00'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Københavns Listefabrik'),
        'Københavns Listefabrik Campaign',
        false,
        '2023-05-01 00:00:00',
        '2024-06-01 00:00:00'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Læger uden grænser'),
        'Læger uden grænser Campaign',
        false,
        '2023-05-01 00:00:00',
        '2024-06-01 00:00:00'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Mercury Motor'),
        'Mercury Motor Campaign',
        false,
        '2023-05-01 00:00:00',
        '2024-06-01 00:00:00'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'SJEC Danmark'),
        'SJEC Danmark Campaign',
        false,
        '2023-05-01 00:00:00',
        '2024-06-01 00:00:00'),
       ((SELECT id FROM publisher where name = 'Jyllands-Posten'),
        (SELECT id FROM advertiser where name = 'Tryg Forsikring'),
        'Tryg Forsikring Campaign',
        false,
        '2023-05-01 00:00:00',
        '2024-06-01 00:00:00')
;

# Filepath is relative to advertiser folder (no start slash)
# Hash function can by amy that produces a unique hash.
# impressionTarget = 0: No limit on num of impressions.
SET @publisher = 'Jyllands-Posten';
INSERT INTO creative
(campaign_id, filepath, hash, disabled, width, height, impressionMetric, impressionTarget)
VALUES ((SELECT id
         FROM campaign
         where name = 'Jyllands-Posten Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'half_page_300x600.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        300,
        600,
        'served',
        0),
       ((SELECT id
         FROM campaign
         where name = 'Jyllands-Posten Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_990x330.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        990,
        330,
        'served',
        0),
       ((SELECT id
         FROM campaign
         where name = 'Jyllands-Posten Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_small_930x180.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        930,
        180,
        'served',
        0),
       ((SELECT id
         FROM campaign
         where name = 'Københavns Listefabrik Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'half_page_300x600.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        300,
        600,
        'served',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Læger uden grænser Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'medium_rectangle_300x250.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        300,
        250,
        'served',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Læger uden grænser Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_930x600.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        930,
        600,
        'served',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Læger uden grænser Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'wide_skyscraper_160x600.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        160,
        600,
        'served',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Mercury Motor Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_930x600.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        930,
        600,
        'served',
        100),
       ((SELECT id
         FROM campaign
         where name = 'SJEC Danmark Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_930x600.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        930,
        600,
        'served',
        100),
       ((SELECT id
         FROM campaign
         where name = 'Tryg Forsikring Campaign'
           AND publisher_id = (SELECT id FROM publisher where name = @publisher)),
        'monster_small_930x180.html',
        MD5(concat(filepath, campaign_id, rand())),
        false,
        930,
        180,
        'served',
        100)
;


########################################################################################################################
