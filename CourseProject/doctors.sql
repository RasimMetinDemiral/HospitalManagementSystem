--------------------------------------------------------
--  File created - Sunday-January-06-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table DOCTORS
--------------------------------------------------------

  CREATE TABLE "HR"."DOCTORS" 
   (	"DOCTORID" NUMBER(*,0), 
	"DOCTORNAME" VARCHAR2(50 BYTE), 
	"DOCTORSURNAME" VARCHAR2(50 BYTE), 
	"DOCTORDEPARTMENT" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into HR.DOCTORS
SET DEFINE OFF;
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (63,'ahmet','aydin','urology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (21,'burak','�zkurt','emergency');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (65,'ercan','cakir','norology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (25,'m�cahit','orakci','urology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (32,'alp batur','sahin','urology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (13,'seyit','evren','romology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (27,'zeki','celen','norology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (6,'nurg�l','demiral','romology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (14,'g�ner','caglayan','romology');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (62,'ali','bayram','emergency');
Insert into HR.DOCTORS (DOCTORID,DOCTORNAME,DOCTORSURNAME,DOCTORDEPARTMENT) values (64,'kerem','akbulut','norology');
--------------------------------------------------------
--  DDL for Index SYS_C0014564
--------------------------------------------------------

  CREATE UNIQUE INDEX "HR"."SYS_C0014564" ON "HR"."DOCTORS" ("DOCTORID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table DOCTORS
--------------------------------------------------------

  ALTER TABLE "HR"."DOCTORS" ADD PRIMARY KEY ("DOCTORID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
