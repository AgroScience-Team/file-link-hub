-- Таблица для хранения информации о фотографиях
CREATE TABLE file_link_hub.photos
(
    photo_id VARCHAR PRIMARY KEY
);

COMMENT ON TABLE file_link_hub.photos IS 'Таблица для хранения полных путей файлов';
COMMENT ON COLUMN file_link_hub.photos.photo_id IS 'Уникальный идентификатор фотографии относительно бакета';

commit;
