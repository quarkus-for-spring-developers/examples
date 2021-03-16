CREATE TABLE fruits
(
	id bigserial NOT NULL,
	name varchar(255) NOT NULL UNIQUE,
	description varchar(255),
	PRIMARY KEY (id)
);

INSERT INTO fruits(name, description) VALUES
  ('Apple', 'Hearty fruit'),
  ('Pear', 'Juicy fruit');
