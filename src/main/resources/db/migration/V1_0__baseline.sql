CREATE SEQUENCE vegetable_id_seq START WITH 1;


CREATE TABLE vegetables
(
  id      BIGINT       NOT NULL DEFAULT nextval('vegetable_id_seq') PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  price   DECIMAL      NOT NULL,
  version BIGINT,
  created TIMESTAMP    NOT NULL,
  updated TIMESTAMP    NOT NULL
);

insert into vegetables (name, price, version, created, updated) values ('Cucumber', 12, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into vegetables (name, price, version, created, updated) values ('Tomato', 9, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into vegetables (name, price, version, created, updated) values ('Potato', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
