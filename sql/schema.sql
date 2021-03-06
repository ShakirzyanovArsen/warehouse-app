CREATE TABLE IF NOT EXISTS warehouse
(
    uuid       UUID DEFAULT generateUUIDv4(),
    name       String CODEC(LZ4),
    address    String CODEC(LZ4),
    created_at DateTime
) ENGINE = ReplacingMergeTree(created_at)
      order by uuid;

CREATE TABLE IF NOT EXISTS client
(
    uuid       UUID DEFAULT generateUUIDv4(),
    name       String CODEC(LZ4),
    phone      String CODEC(LZ4),
    email      String CODEC(LZ4),
    address    String CODEC(LZ4),
    created_at DateTime
) ENGINE = ReplacingMergeTree(created_at)
      ORDER BY (uuid);

CREATE TABLE IF NOT EXISTS goods
(
    uuid       UUID DEFAULT generateUUIDv4(),
    name       String CODEC(LZ4),
    count      Float64 CODEC(LZ4),
    unit       Enum8('кг.' = 1, 'л.' = 2, 'шт.' = 3),
    barcode    String CODEC(LZ4),
    created_at DateTime
) ENGINE = ReplacingMergeTree(created_at)
      ORDER BY uuid;

CREATE TABLE IF NOT EXISTS document
(
    uuid          UUID DEFAULT generateUUIDv4(),
    userUuid      UUID,
    warehouseUuid UUID,
    clientUuid    UUID,
    goods         Array(String),
    counts        Array(Float64),
    type          Enum8('Приход' = 1, 'Расход' = 2),
    fileName      String CODEC(LZ4),
    fileHash      String CODEC(LZ4),
    sigFileName   Nullable(String) CODEC(LZ4),
    ended         UInt8,
    created_at    DateTime
) ENGINE = ReplacingMergeTree(created_at)
      ORDER BY uuid;

CREATE TABLE IF NOT EXISTS user
(
    uuid       UUID,
    login      String CODEC(LZ4),
    pwd        String CODEC(LZ4),
    publicKeys Array(String) DEFAULT [],
    created_at DateTime
) ENGINE = ReplacingMergeTree(created_at)
      ORDER BY (uuid);