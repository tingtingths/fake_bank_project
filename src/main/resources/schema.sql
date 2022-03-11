CREATE TABLE IF NOT EXISTS ACCOUNT
(
    ID BIGINT NOT NULL,
    NAME VARCHAR2(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS TRANSACTION
(
    ID              BIGINT                   NOT NULL AUTO_INCREMENT,
    SRC_ACCOUNT_ID  BIGINT,
    DEST_ACCOUNT_ID BIGINT                   NOT NULL,
    AMOUNT          NUMERIC(18, 2)           NOT NULL,
    TS              TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (ID),
    FOREIGN KEY (SRC_ACCOUNT_ID) REFERENCES ACCOUNT (ID),
    FOREIGN KEY (DEST_ACCOUNT_ID) REFERENCES ACCOUNT (ID)
);