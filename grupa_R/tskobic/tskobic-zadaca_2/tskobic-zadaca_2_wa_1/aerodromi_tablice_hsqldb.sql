CREATE TABLE AERODROMI_POLASCI (
  id integer not null generated always as identity (start with 1, increment by 1),
  icao24 varchar(30) NOT NULL,
  firstSeen integer not null,
  estDepartureAirport varchar (10) NOT NULL,
  lastSeen integer not null,
  estArrivalAirport varchar (10) NOT NULL,
  callsign varchar (20),
  estDepartureAirportHorizDistance integer not null,
  estDepartureAirportVertDistance integer not null,
  estArrivalAirportHorizDistance integer not null,
  estArrivalAirportVertDistance integer not null,
  departureAirportCandidatesCount integer not null,
  arrivalAirportCandidatesCount integer not null,
  stored TIMESTAMP NOT NULL, 
  PRIMARY KEY (id),
  FOREIGN KEY (estDepartureAirport) REFERENCES airports (ident),
  CONSTRAINT AERODROMI_POLASCI_icao24_firstSeen UNIQUE (icao24, firstSeen)
);

CREATE TABLE AERODROMI_DOLASCI (
  id integer not null generated always as identity (start with 1, increment by 1),
  icao24 varchar(30) NOT NULL,
  firstSeen integer not null,
  estDepartureAirport varchar (10) NOT NULL,
  lastSeen integer not null,
  estArrivalAirport varchar (10) NOT NULL,
  callsign varchar (20),
  estDepartureAirportHorizDistance integer not null,
  estDepartureAirportVertDistance integer not null,
  estArrivalAirportHorizDistance integer not null,
  estArrivalAirportVertDistance integer not null,
  departureAirportCandidatesCount integer not null,
  arrivalAirportCandidatesCount integer not null,
  stored TIMESTAMP NOT NULL, 
  PRIMARY KEY (id),
  FOREIGN KEY (estArrivalAirport) REFERENCES airports (ident),
  CONSTRAINT AERODROMI_DOLASCI_icao24_firstSeen UNIQUE (icao24, firstSeen)  
);

CREATE TABLE AERODROMI_PRACENI (
  id integer not null generated always as identity (start with 1, increment by 1),
  ident varchar (10) NOT NULL,
  stored TIMESTAMP NOT NULL, 
  PRIMARY KEY (id),
  FOREIGN KEY (ident) REFERENCES airports (ident)
);

CREATE TABLE AERODROMI_PROBLEMI (
  id integer not null generated always as identity (start with 1, increment by 1),
  ident varchar (10) NOT NULL,
  description varchar(1024) NOT NULL,
  stored TIMESTAMP NOT NULL, 
  PRIMARY KEY (id),
  FOREIGN KEY (ident) REFERENCES airports (ident)
);

GRANT SELECT, UPDATE, INSERT ON TABLE AERODROMI_POLASCI TO "aplikacija";
GRANT SELECT, UPDATE, INSERT ON TABLE AERODROMI_DOLASCI TO "aplikacija";
GRANT SELECT, UPDATE, INSERT ON TABLE AERODROMI_PRACENI TO "aplikacija";
GRANT SELECT, UPDATE, INSERT ON TABLE AERODROMI_PROBLEMI TO "aplikacija";

