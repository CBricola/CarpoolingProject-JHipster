ALTER TABLE path ADD user_id BIGINT NULL;
ALTER TABLE registration ADD user_id BIGINT NULL;

ALTER TABLE path ADD CONSTRAINT fk_path_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);
ALTER TABLE registration ADD CONSTRAINT fk_registration_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);

ALTER TABLE jhi_user ADD phone BIGINT(10);