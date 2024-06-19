CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_movie_list_ids (
    user_id BIGINT NOT NULL,
    movie_list_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, movie_list_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);