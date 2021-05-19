CREATE TABLE IF NOT EXISTS `member` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `professional_email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE path ADD member_id BIGINT NOT NULL;
ALTER TABLE registration ADD member_id BIGINT NOT NULL;

ALTER TABLE path ADD CONSTRAINT fk_path__member_id FOREIGN KEY (member_id) REFERENCES member(id) ;
ALTER TABLE registration ADD CONSTRAINT fk_registration__member_id FOREIGN KEY (member_id) REFERENCES member(id);