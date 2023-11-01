-- create the roles used by the app
INSERT INTO
    roles (id, "role")
VALUES
    (1, 'ROLE_USER');

INSERT INTO
    roles (id, "role")
VALUES
    (2, 'ROLE_ADMIN');

-- create a user test1 with password test1
INSERT INTO
    users (id, username, password_hash, active)
VALUES
    (
        1,
        'test1',
        '{bcrypt}$2a$12$8VhADH4b.DnQqSxPa/3BeOxgyl9lD2IT7NrynrZjBACTdJdny1ZNG',
        true
    );

-- map user role to the user
INSERT INTO
    user_roles (user_id, role_id)
VALUES
(1, 1);