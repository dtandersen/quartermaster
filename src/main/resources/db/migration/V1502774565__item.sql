CREATE TABLE public.item
(
    bmat integer NOT NULL,
    emat integer NOT NULL,
    item_id uuid NOT NULL,
    item_name character varying COLLATE pg_catalog."default" NOT NULL,
    min_qty integer NOT NULL,
    pack integer NOT NULL,
    rmat integer NOT NULL,
    sort_order integer NOT NULL DEFAULT 0,
    CONSTRAINT item_pkey PRIMARY KEY (item_id)
)
WITH (
    OIDS = FALSE
)
