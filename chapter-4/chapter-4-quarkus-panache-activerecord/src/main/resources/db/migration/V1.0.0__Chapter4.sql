CREATE TABLE fruits
(
	id bigint NOT NULL,
	name varchar(255) NOT NULL UNIQUE,
	description varchar(255),
	PRIMARY KEY (id)
);

CREATE SEQUENCE hibernate_sequence OWNED BY fruits.id;

INSERT INTO fruits(id, name, description) VALUES
  (nextval('hibernate_sequence'), 'Apple', 'Hearty fruit'),
  (nextval('hibernate_sequence'), 'Pear', 'Juicy fruit');
