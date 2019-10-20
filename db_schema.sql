CREATE DATABASE short_url;
USE short_url;
CREATE TABLE url_profile(
	id BIGINT NOT NULL AUTO_INCREMENT,
	short_url VARCHAR(10) DEFAULT NULL,
	long_url VARCHAR(1023) NOT NULL,
	created_datetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE (short_url)
);
CREATE TABLE url_logs(
	id BIGINT NOT NULL,
	short_url VARCHAR(10) NOT NULL,
	created_datetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_surl ON url_logs(short_url);
CREATE INDEX idx_id ON url_logs(id);



	