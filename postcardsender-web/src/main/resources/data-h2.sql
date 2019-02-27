/*
 * This file contains some initial data.
 *
 * On startup, Spring Boot automatically processes the data-${platform}.sql files (if present),
 * where platform is the value of spring.datasource.platform. This allows you to switch to database-specific scripts if necessary.
*/

--Campagin
INSERT INTO CAMPAIGN (CAMP_ID, CAMP_KEY, CAMP_DESC, CAMP_IMG_TYPE, CAMP_MAX_CARDS, CAMP_FROM, CAMP_TO, CAMP_BRAND_TYPE, CAMP_MDATE, CAMP_CDATE) VALUES (1, '26d121ad-364b-4977-9271-6ac3fbacb6fb', 'erste Kampagne upload eigene Bilder', 1, 1000, to_date('01.01.2019', 'DD.MM.YYYY'),to_date('31.12.2019', 'DD.MM.YYYY'), 1, sysdate, sysdate );

--Campagin
INSERT INTO CAMPAIGN (CAMP_ID, CAMP_KEY, CAMP_DESC, CAMP_IMG_TYPE, CAMP_MAX_CARDS, CAMP_FROM, CAMP_TO, CAMP_BRAND_TYPE, CAMP_MDATE, CAMP_CDATE) VALUES (2, '26d121ad-364b-4977-9271-asadasss', 'zweite Kampagne wahl aus Auswahl', 2, 1000, to_date('01.01.2019', 'DD.MM.YYYY'),to_date('31.12.2019', 'DD.MM.YYYY'), 1, sysdate, sysdate );

--Images (work's only locally, please save some images and adjust the file path)
INSERT INTO IMAGE (IMG_ID, IMG_FILE, IMG_MDATE, IMG_CDATE) VALUES (1, FILE_READ('~/testimages/testbild1.jpg'), sysdate, sysdate);
INSERT INTO IMAGE (IMG_ID, IMG_FILE, IMG_MDATE, IMG_CDATE) VALUES (2, FILE_READ('~/testimages/testbild2.jpg'), sysdate, sysdate);
INSERT INTO IMAGE (IMG_ID, IMG_FILE, IMG_MDATE, IMG_CDATE) VALUES (3, FILE_READ('~/testimages/testbild3.jpg'), sysdate, sysdate);
INSERT INTO IMAGE (IMG_ID, IMG_FILE, IMG_MDATE, IMG_CDATE) VALUES (4, FILE_READ('~/testimages/testbild4.jpg'), sysdate, sysdate);

--Assign Image to Campaign 2
INSERT INTO CAMP_IMAGE_SET (CIS_ID, CIS_CAMP_ID, CIS_IMG_ID, CIS_MDATE, CIS_CDATE) VALUES (1, 2, 1, sysdate, sysdate);
INSERT INTO CAMP_IMAGE_SET (CIS_ID, CIS_CAMP_ID, CIS_IMG_ID, CIS_MDATE, CIS_CDATE) VALUES (2, 2, 2, sysdate, sysdate);
INSERT INTO CAMP_IMAGE_SET (CIS_ID, CIS_CAMP_ID, CIS_IMG_ID, CIS_MDATE, CIS_CDATE) VALUES (3, 2, 3, sysdate, sysdate);
INSERT INTO CAMP_IMAGE_SET (CIS_ID, CIS_CAMP_ID, CIS_IMG_ID, CIS_MDATE, CIS_CDATE) VALUES (4, 2, 4, sysdate, sysdate);