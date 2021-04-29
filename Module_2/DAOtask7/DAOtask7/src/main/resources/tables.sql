DROP TABLE IF EXISTS parameters CASCADE;
DROP TABLE IF EXISTS parameter_groups CASCADE;
DROP TABLE IF EXISTS production_groups CASCADE;
DROP TABLE IF EXISTS production CASCADE;

CREATE TABLE production_groups
(
    id   SERIAL,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE parameter_groups
(
    id                  SERIAL,
    name                varchar(255) NOT NULL,
    production_group_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (production_group_id) REFERENCES production_groups (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE production
(
    id                  SERIAL,
    name                varchar(255) NOT NULL,
    description         text,
    output_date         date         NOT NULL,
    production_group_id int          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (production_group_id) REFERENCES production_groups (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE parameters
(
    id                 SERIAL,
    name               varchar(255) NOT NULL,
    value              varchar(255),
    measure_unit       varchar(255),
    product_id         int,
    parameter_group_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES production (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (parameter_group_id) REFERENCES parameter_groups (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
