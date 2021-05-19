ALTER TABLE path DROP FOREIGN KEY fk_path__member_id;
ALTER TABLE registration DROP FOREIGN KEY fk_registration__member_id;

ALTER TABLE path DROP COLUMN member_id;
ALTER TABLE registration DROP COLUMN member_id;

DROP TABLE member;
