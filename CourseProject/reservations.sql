--------------------------------------------------------
--  File created - Sunday-January-06-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table RESERVATIONS
--------------------------------------------------------

  CREATE TABLE "HR"."RESERVATIONS" 
   (	"RESERVATIONID" NUMBER(*,0), 
	"DOCTORID" NUMBER(*,0), 
	"PATIENTID" NUMBER(*,0), 
	"NURSEID" NUMBER(*,0), 
	"RESERVATIONDATE" DATE, 
	"WARDID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into HR.RESERVATIONS
SET DEFINE OFF;
Insert into HR.RESERVATIONS (RESERVATIONID,DOCTORID,PATIENTID,NURSEID,RESERVATIONDATE,WARDID) values (32,62,97,23,to_date('22-JAN-19','DD-MON-RR'),26);
Insert into HR.RESERVATIONS (RESERVATIONID,DOCTORID,PATIENTID,NURSEID,RESERVATIONDATE,WARDID) values (4,62,1,26,to_date('06-FEB-19','DD-MON-RR'),403);
Insert into HR.RESERVATIONS (RESERVATIONID,DOCTORID,PATIENTID,NURSEID,RESERVATIONDATE,WARDID) values (24,21,6,32,to_date('29-JAN-19','DD-MON-RR'),34);
Insert into HR.RESERVATIONS (RESERVATIONID,DOCTORID,PATIENTID,NURSEID,RESERVATIONDATE,WARDID) values (6,63,34,23,to_date('12-FEB-19','DD-MON-RR'),201);
--------------------------------------------------------
--  DDL for Index RESERVATIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "HR"."RESERVATIONS_PK" ON "HR"."RESERVATIONS" ("RESERVATIONID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table RESERVATIONS
--------------------------------------------------------

  ALTER TABLE "HR"."RESERVATIONS" ADD CONSTRAINT "RESERVATIONS_PK" PRIMARY KEY ("RESERVATIONID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "HR"."RESERVATIONS" MODIFY ("RESERVATIONID" NOT NULL ENABLE);