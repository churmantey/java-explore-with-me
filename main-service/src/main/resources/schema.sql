DROP TABLE IF EXISTS PARTICIPATION_REQUESTS CASCADE;
DROP TABLE IF EXISTS EVENTS CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;
DROP TABLE IF EXISTS CATEGORIES;

CREATE TABLE IF NOT EXISTS USERS (
  ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  NAME VARCHAR(250) NOT NULL,
  EMAIL VARCHAR(254) UNIQUE NOT NULL
 );

CREATE TABLE IF NOT EXISTS CATEGORIES (
  ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  NAME VARCHAR(50) UNIQUE NOT NULL
 );

CREATE TABLE IF NOT EXISTS EVENTS (
  ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  CATEGORY_ID BIGINT REFERENCES CATEGORIES(ID) NOT NULL,
  INITIATOR_ID BIGINT REFERENCES USERS(ID) NOT NULL,
  TITLE VARCHAR(120) NOT NULL,
  ANNOTATION VARCHAR(2000) NOT NULL,
  DESCRIPTION VARCHAR(7000) NOT NULL,
  EVENT_DATE TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  LOCATION_LAT NUMERIC(8, 6),
  LOCATION_LON NUMERIC(9, 6),
  PAID BOOLEAN DEFAULT FALSE,
  PARTICIPANT_LIMIT INTEGER,
  CREATED_ON TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  PUBLISHED_ON TIMESTAMP WITHOUT TIME ZONE,
  REQUEST_MODERATION BOOLEAN DEFAULT FALSE,
  STATE VARCHAR(50),
  CONFIRMED_REQUESTS BIGINT,
  VIEWS BIGINT
 );

CREATE TABLE IF NOT EXISTS PARTICIPATION_REQUESTS (
    ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    EVENT_ID BIGINT REFERENCES EVENTS(ID) ON DELETE CASCADE,
    REQUESTOR_ID BIGINT REFERENCES USERS(ID) ON DELETE CASCADE,
    CREATED_ON TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    STATE VARCHAR(50),
    UNIQUE (EVENT_ID, REQUESTOR_ID)
);

