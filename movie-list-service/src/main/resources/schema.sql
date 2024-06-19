CREATE TABLE IF NOT EXISTS movie_list (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_list_movie_ids (
    movie_list_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    FOREIGN KEY (movie_list_id) REFERENCES movie_list(id) ON DELETE CASCADE
);