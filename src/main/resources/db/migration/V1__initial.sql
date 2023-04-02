CREATE TABLE IF NOT EXISTS item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    price INTEGER NOT NULL,
    stock_number INTEGER NOT NULL,
    item_detail TEXT NOT NULL,
    item_sell_status VARCHAR NOT NULL,
    reg_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL
);