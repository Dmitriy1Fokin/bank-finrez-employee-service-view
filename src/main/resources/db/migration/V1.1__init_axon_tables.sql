--Axon framework table
CREATE TABLE public.association_value_entry (
    id int8 NOT NULL,
    association_key varchar(255) NOT NULL,
    association_value varchar(255) NULL,
    saga_id varchar(255) NOT NULL,
    saga_type varchar(255) NULL,
    CONSTRAINT association_value_entry_pkey PRIMARY KEY (id)
);
CREATE INDEX idxgv5k1v2mh6frxuy5c0hgbau94 ON public.association_value_entry USING btree (saga_id, saga_type);
CREATE INDEX idxk45eqnxkgd8hpdn6xixn8sgft ON public.association_value_entry USING btree (saga_type, association_key, association_value);

--Axon framework table
CREATE TABLE public.saga_entry (
    saga_id varchar(255) NOT NULL,
    revision varchar(255) NULL,
    saga_type varchar(255) NULL,
    serialized_saga oid NULL,
    CONSTRAINT saga_entry_pkey PRIMARY KEY (saga_id)
);

--Axon framework table
CREATE TABLE public.token_entry (
    processor_name varchar(255) NOT NULL,
    segment int4 NOT NULL,
    "owner" varchar(255) NULL,
    "timestamp" varchar(255) NOT NULL,
    "token" oid NULL,
    token_type varchar(255) NULL,
    CONSTRAINT token_entry_pkey PRIMARY KEY (processor_name, segment)
);