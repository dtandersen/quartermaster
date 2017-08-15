CREATE TABLE public.outpost
(
    outpost_name character varying COLLATE pg_catalog."default" NOT NULL,
    outpost_id uuid NOT NULL,
    CONSTRAINT outpost_pkey PRIMARY KEY (outpost_id)
)
WITH (
    OIDS = FALSE
)
