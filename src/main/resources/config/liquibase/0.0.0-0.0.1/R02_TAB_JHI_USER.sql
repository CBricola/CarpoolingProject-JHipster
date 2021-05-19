ALTER TABLE path DROP FOREIGN KEY fk_path_user_id;
ALTER TABLE registration DROP FOREIGN KEY fk_registration_user_id;

ALTER TABLE path DROP COLUMN user_id;
ALTER TABLE registration DROP COLUMN user_id;

ALTER TABLE jhi_user DROP COLUMN phone;
