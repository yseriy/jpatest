CREATE TABLE brand (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX brand_unique_name_index
  ON brand (name);

CREATE TABLE tariff (
  id     BIGSERIAL PRIMARY KEY,
  name   VARCHAR(255) NOT NULL,
  weight INTEGER      NOT NULL
);

CREATE UNIQUE INDEX tariff_unique_name_index
  ON tariff (name);

CREATE TABLE location (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX location_unique_name_index
  ON location (name);

CREATE TABLE ipnet_type (
  id       BIGSERIAL PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  required BOOLEAN      NOT NULL
);

CREATE UNIQUE INDEX ipnet_type_unique_name_index
  ON ipnet_type (name);

CREATE TABLE ipnet (
  id            BIGSERIAL PRIMARY KEY,
  ipnet_type_id BIGINT REFERENCES ipnet_type (id),
  net           INET NOT NULL
);

CREATE UNIQUE INDEX ipnet_unique_net_index
  ON ipnet (net);

CREATE TABLE farm_type (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX farm_type_unique_type_index
  ON farm_type (name);

CREATE TABLE farm (
  id           BIGSERIAL PRIMARY KEY,
  farm_type_id BIGINT REFERENCES farm_type (id),
  location_id  BIGINT REFERENCES location (id),
  name         VARCHAR(255) NOT NULL,
  capacity     INTEGER      NOT NULL
);

CREATE UNIQUE INDEX farm_unique_name_index
  ON farm (name);

CREATE TABLE farmip_type (
  id       BIGSERIAL PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  required BOOLEAN      NOT NULL
);

CREATE UNIQUE INDEX farmip_type_unique_name_index
  ON farmip_type (name);

CREATE TABLE farmip (
  id             BIGSERIAL PRIMARY KEY,
  farm_id        BIGINT NOT NULL REFERENCES farm (id),
  farmip_type_id BIGINT REFERENCES farmip_type (id),
  ip             INET   NOT NULL
);

CREATE UNIQUE INDEX farmip_unique_ip_index
  ON farmip (ip);

CREATE TABLE brand_farmip (
  id        BIGSERIAL PRIMARY KEY,
  brand_id  BIGINT REFERENCES brand (id),
  farmip_id BIGINT REFERENCES farmip (id)
);

CREATE UNIQUE INDEX brand_farmip_unique_index
  ON brand_farmip (brand_id, farmip_id);

CREATE TABLE brand_ipnet (
  id       BIGSERIAL PRIMARY KEY,
  brand_id BIGINT REFERENCES brand (id),
  ipnet_id BIGINT REFERENCES ipnet (id)
);

CREATE UNIQUE INDEX brand_ipnet_unique_index
  ON brand_ipnet (brand_id, ipnet_id);

CREATE TABLE farm_ipnet (
  id       BIGSERIAL PRIMARY KEY,
  farm_id  BIGINT REFERENCES farm (id),
  ipnet_id BIGINT REFERENCES ipnet (id)
);

CREATE UNIQUE INDEX farm_ipnet_unique_index
  ON farm_ipnet (farm_id, ipnet_id);

CREATE TABLE tariff_farm_type (
  tariff_id    BIGINT REFERENCES tariff (id),
  farm_type_id BIGINT REFERENCES farm_type (id),
  PRIMARY KEY (tariff_id, farm_type_id)
);
