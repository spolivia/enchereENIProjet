-- Script de création de la base de données TP_ENCHERES
--   type :      SQL Server 2012
--

CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL

)

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article)

CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(20) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(30) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
)

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)


CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
)

ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)


ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

-- Insert data into UTILISATEURS Table
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES
('User1', 'Doe', 'John', 'user1@example.com', '123-456-7890', '123 Main St', '12345', 'City A', 'password1', 500, 0),
('User2', 'Smith', 'Jane', 'user2@example.com', '987-654-3210', '456 Elm St', '67890', 'City B', 'password2', 750, 0),
('User3', 'Johnson', 'Bob', 'user3@example.com', '555-555-5555', '789 Oak St', '54321', 'City C', 'password3', 300, 0),
('User4', 'Williams', 'Alice', 'user4@example.com', '111-222-3333', '101 Pine St', '98765', 'City D', 'password4', 900, 1),
('User5', 'Brown', 'Emily', 'user5@example.com', '999-888-7777', '202 Maple St', '23456', 'City E', 'password5', 600, 0);

-- Insert data into CATEGORIES Table
INSERT INTO CATEGORIES (libelle) VALUES
('Category 1'),
('Category 2'),
('Category 3'),
('Category 4'),
('Category 5');

-- Insert data into ARTICLES_VENDUS Table
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES
('Item 1', 'Description of Item 1', '2023-09-20', '2023-09-25', 50, 1, 1),
('Item 2', 'Description of Item 2', '2023-09-22', '2023-09-27', 75, 2, 2),
('Item 3', 'Description of Item 3', '2023-09-23', '2023-09-28', 60, 3, 3),
('Item 4', 'Description of Item 4', '2023-09-25', '2023-09-30', 80, 4, 4),
('Item 5', 'Description of Item 5', '2023-09-26', '2023-10-01', 70, 5, 5);

-- Insert data into ENCHERES Table
INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES
(1, 1, '2023-09-19T10:00:00', 100),
(2, 1, '2023-09-19T10:05:00', 120),
(3, 2, '2023-09-19T11:00:00', 150),
(4, 3, '2023-09-19T12:30:00', 200),
(5, 4, '2023-09-19T14:15:00', 180);

-- Insert data into RETRAITS Table
INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES
(1, '123 Main St', '12345', 'City A'),
(2, '456 Elm St', '67890', 'City B'),
(3, '789 Oak St', '54321', 'City C'),
(4, '101 Pine St', '98765', 'City D'),
(5, '202 Maple St', '23456', 'City E');