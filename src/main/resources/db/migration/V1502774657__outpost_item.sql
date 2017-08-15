CREATE TABLE public.outpost_item
(
    outpost_id uuid NOT NULL,
    item_id uuid NOT NULL,
    stock integer NOT NULL,
    shipping integer NOT NULL,
    CONSTRAINT outpost_item_pkey PRIMARY KEY (outpost_id, item_id)
)
WITH (
    OIDS = FALSE
)
