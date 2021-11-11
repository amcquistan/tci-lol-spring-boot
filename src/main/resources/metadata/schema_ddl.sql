
CREATE TABLE IF NOT EXISTS users (
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    created     TIMESTAMP NOT NULL,
    updated     TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id      INT NOT NULL,
    role_id      INT NOT NULL,
    CONSTRAINT   fk_user_role_user_id
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT   fk_user_role_role_id
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_lists (
    id         SERIAL PRIMARY KEY,
    user_id    INT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    created    TIMESTAMP NOT NULL,
    updated    TIMESTAMP,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS list_items (
    id        SERIAL PRIMARY KEY,
    list_id   INT NOT NULL,
    title     VARCHAR(255) NOT NULL,
    created   TIMESTAMP NOT NULL,
    updated   TIMESTAMP,
    completed TIMESTAMP,
    CONSTRAINT fk_list
        FOREIGN KEY (list_id)
        REFERENCES user_lists(id)
        ON DELETE CASCADE
);
