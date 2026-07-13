
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS line_time CASCADE;
DROP TABLE IF EXISTS location_points CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS network_relationships CASCADE;
DROP TABLE IF EXISTS network_nodes CASCADE;
DROP TABLE IF EXISTS character_images CASCADE;
DROP TABLE IF EXISTS characters CASCADE;
DROP TABLE IF EXISTS premise CASCADE;
DROP TABLE IF EXISTS network CASCADE;
DROP TABLE IF EXISTS plot CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS users CASCADE;


-- USUARIOS
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    blocked BOOLEAN 
);

-- PROYECTO
CREATE TABLE project (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- TRAMAS
CREATE TABLE plot (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    dramatic_situation VARCHAR(255),

    -- ESTRUCTURA NARRATIVA (7 PASOS)
    struct_weakness_need TEXT,
    struct_desire TEXT,
    struct_adversary TEXT,
    struct_plan TEXT,
    struct_struggle TEXT,
    struct_self_revelation TEXT,
    struct_new_equilibrium TEXT,

    network_id BIGINT,
    timeline_id BIGINT,

    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

-- PREMISA 
CREATE TABLE premise (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plot_id BIGINT,     
    premise TEXT,
    narrative_possibilities TEXT,
    narrative_challenges TEXT,
    problems TEXT,
    founding_principle TEXT,
    conflict TEXT,
    moral_decision TEXT,

    FOREIGN KEY (plot_id) REFERENCES plot(id) ON DELETE CASCADE
);

-- PERSONAJES Y REDES
CREATE TABLE network (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plot_id BIGINT NOT NULL,
    description TEXT,
    background_color VARCHAR(10),
    FOREIGN KEY (plot_id) REFERENCES plot(id) ON DELETE CASCADE
);

ALTER TABLE plot 
ADD CONSTRAINT fk_plot_network 
FOREIGN KEY (network_id) REFERENCES network(id) ON DELETE CASCADE;

CREATE TABLE characters (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL, 
    name VARCHAR(50) NOT NULL,
    bio TEXT,
    main_image_url VARCHAR(500),
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE network_nodes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    network_id BIGINT NOT NULL,
    character_id BIGINT NOT NULL,
    
    role ENUM('protagonist', 'principal', 'supporting_character') DEFAULT 'principal',
    
    FOREIGN KEY (network_id) REFERENCES network(id) ON DELETE CASCADE,
    FOREIGN KEY (character_id) REFERENCES characters(id) ON DELETE CASCADE,
    
    UNIQUE(network_id, character_id) 
);

CREATE TABLE network_relationships (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    network_id BIGINT NOT NULL,
    
    node_from_id BIGINT NOT NULL, 
    node_to_id BIGINT NOT NULL,   
    
    relationship ENUM('ally', 'adversary', 'fake_ally', 'neutral', 'fake_adversary'),
    description TEXT,
    
    FOREIGN KEY (network_id) REFERENCES network(id) ON DELETE CASCADE,
    FOREIGN KEY (node_from_id) REFERENCES network_nodes(id) ON DELETE CASCADE,
    FOREIGN KEY (node_to_id) REFERENCES network_nodes(id) ON DELETE CASCADE
);

-- LOCATIONS

CREATE TABLE location (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plot_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    background_history TEXT,
    background_image_url VARCHAR(500),
    FOREIGN KEY (plot_id) REFERENCES plot(id) ON DELETE CASCADE
);

CREATE TABLE location_points (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    location_id BIGINT NOT NULL,
    name VARCHAR(255),
    description TEXT,
    coord_x FLOAT NOT NULL,
    coord_y FLOAT NOT NULL,
    marker_type ENUM('mountain', 'city', 'lake', 'other'), 
    marker_icon VARCHAR(50),
    FOREIGN KEY (location_id) REFERENCES location(id) ON DELETE CASCADE
);

-- EVENTOS Y LÍNEA DE TIEMPO
CREATE TABLE line_time (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plot_id BIGINT NOT NULL,
    name VARCHAR(100), 
    description TEXT,
    color_code VARCHAR(10),
    FOREIGN KEY (plot_id) REFERENCES plot(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    timeline_id BIGINT NOT NULL,
    
    title VARCHAR(150),
    content TEXT, 
    chapter_number INT,
    chrono_order INT DEFAULT 0,       
    
    FOREIGN KEY (timeline_id) REFERENCES line_time(id) ON DELETE CASCADE
);