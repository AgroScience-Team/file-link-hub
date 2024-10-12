-- Таблица для хранения информации о фотографиях
CREATE TABLE file_link_hub.files
(
    name VARCHAR,
    bucket VARCHAR,
    PRIMARY KEY(name, bucket)
);

COMMENT ON TABLE file_link_hub.files IS 'Таблица для хранения полных путей файлов';
COMMENT ON COLUMN file_link_hub.files.name IS 'Уникальный идентификатор относительно бакета';
COMMENT ON COLUMN file_link_hub.files.bucket IS 'Название бакета в s3';

commit;
