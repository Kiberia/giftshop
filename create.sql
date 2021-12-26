drop table if exists public.t_user cascade;
drop table if exists public.item cascade;
drop table if exists public.inventory cascade;
drop table if exists public.wallet cascade;

CREATE TABLE public.t_user (
	guid varchar(36) NOT NULL,
	login text NULL,
	CONSTRAINT t_user_pkey PRIMARY KEY (guid)
);
CREATE INDEX user_idx ON public.t_user USING btree (guid);

CREATE TABLE public.item (
	id bigserial NOT NULL,
	description text NULL,
	gold_price int8 NULL,
	silver_price int8 NULL,
	CONSTRAINT item_pkey PRIMARY KEY (id)
);

CREATE TABLE public.inventory (
	id bigserial NOT NULL,
	user_guid varchar(36) NULL,
	item_id int8 NOT NULL,
	quantity int8 NOT NULL DEFAULT 0,
	CONSTRAINT inventory_pkey PRIMARY KEY (id)
);
CREATE INDEX inventory_idx ON public.inventory USING btree (id);
ALTER TABLE public.inventory ADD CONSTRAINT inventory_guid_item_id_unique UNIQUE (user_guid,item_id);

CREATE TABLE public.wallet (
	user_guid varchar(36) NOT NULL,
	gold_coins int8 NULL DEFAULT 0,
	silver_coins int8 NULL DEFAULT 0,
	CONSTRAINT wallet_pkey PRIMARY KEY (user_guid)
);
CREATE INDEX wallet_idx ON public.wallet USING btree (user_guid);




INSERT INTO public.t_user
(guid, login)
VALUES('6591c91a-f41a-47c3-9d4e-325086f10784', 'A');
INSERT INTO public.t_user
(guid, login)
VALUES('54a0dfb2-2bb1-41e8-8f28-bd16cdd31064', 'B');
INSERT INTO public.t_user
(guid, login)
VALUES('5153d807-8f2d-45c5-b2fe-31990becae2c', 'C');
INSERT INTO public.t_user
(guid, login)
VALUES('bdec3820-cd18-46e1-a8c9-564dc62d9036', 'D');

INSERT INTO public.wallet
(user_guid, gold_coins, silver_coins)
VALUES('6591c91a-f41a-47c3-9d4e-325086f10784', 100, 100);
INSERT INTO public.wallet
(user_guid, gold_coins, silver_coins)
VALUES('5153d807-8f2d-45c5-b2fe-31990becae2c', 10, 0);
INSERT INTO public.wallet
(user_guid, gold_coins, silver_coins)
VALUES('bdec3820-cd18-46e1-a8c9-564dc62d9036', 0, 10);
INSERT INTO public.wallet
(user_guid, gold_coins, silver_coins)
VALUES('54a0dfb2-2bb1-41e8-8f28-bd16cdd31064', 0, 0);

INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(1, 'Cat Face', 0, 10);
INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(2, 'Cat Gun', 10, 0);
INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(3, 'Duck Face', 5, 5);
INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(4, 'Duct Tape Gun', 10, 0);
INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(5, 'Unicorn Horn', 3, 7);
INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(6, 'Unicorn Shooter 3000', 100, 100);
INSERT INTO public.item
(id, description, gold_price, silver_price)
VALUES(7, 'Coca-Cola Pit Truck', 53, 45);
