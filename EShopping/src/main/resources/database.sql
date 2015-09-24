 create table ES_ITEM (
	id bigint not null auto_increment,
	DESCRIPTION VARCHAR(4000),
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	STUFF VARCHAR(100), 
	SUMMARY VARCHAR(1000), 
	primary key (id)
);


 create table ESHOPPING_ITEM_CONFIG_DETAIL (
	id bigint not null,
	ITEM_TYPE INT(1),
	IS_SALE BOOL default false,
	IS_NEW BOOL default false,
	IS_RECOMMAND BOOL default false,
	IS_HOT BOOL default false,
	ITEM_RANK NUMERIC(3,1) default 0, 
	ITEM_STOCK_COUNTS INT(6) default 0,
	SALED_COUNTS INT(6) default 0,
	STOCK_DATE  DATETIME,
	primary key (id)
);



 create table ES_COMBO_ITEM (
	id bigint not null auto_increment, 
	DESCRIPTION VARCHAR(1000), 
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	STUFF VARCHAR(100), 
	primary key (id)
);


create table ES_COMBO_ITEM_M (
	ES_COMBO_ITEM_ID bigint not null, 
	ES_ITEM_ID bigint not null,
	primary key (ES_COMBO_ITEM_ID, ES_ITEM_ID)
);



create table ES_IMAGE (
	id bigint not null  auto_increment, 
	ES_ITEM_ID bigint not null,
	ITEM_TYPE int(2),
	IMAGE_TYPE int(2),
	IMAGE_URI  VARCHAR(200),
	primary key (id)
);


create table ES_CATEGORY (
	id bigint not null auto_increment,
	NAME VARCHAR(100), 
	SEQ_NUMBER NUMERIC(2), 
	PARENT_ID NUMERIC(20), 
	primary key (id)
);



create table ES_CATEGORY_ITEM_SPECIAL_TYPE (
	ID bigint not null auto_increment,
	TYPE_GROUP  int(2),
	GROUP_NAME  VARCHAR(100),
	TYPE_NAME   VARCHAR(100),
	CATEGORY_ID  bigint not null,
	IS_SHOW   BOOL,
	primary key (ID)
);


create table ES_ITEM_SPECIAL_TYPE_M (
	ES_ITEM_ID bigint not null,
	ES_SPECIAL_TYPE_ID  bigint not null,
	ES_CATEGORY_ID  bigint not null,
	ES_ITEM_TYPE int(2) default 1,
	primary key (ES_ITEM_ID, ES_SPECIAL_TYPE_ID, ES_ITEM_TYPE)
);

create table ES_CATEGORY_ITEM (
	ES_CATEGORY_ID bigint not null, 
	ES_ITEM_ID bigint not null,
	ITEM_TYPE NUMERIC(1, 0),
	primary key (ES_CATEGORY_ID, ES_ITEM_ID)
);






create table ES_USER (
	id bigint not null auto_increment, 
	ADDRESS VARCHAR(200), 
	CELL_PHONE VARCHAR(40), 
	NAME VARCHAR(40), 
	USER_PWD VARCHAR(40), 
	MAIL VARCHAR(100),
	primary key (id)
);


create table ES_USER_ADDRESS (
	id bigint not null auto_increment, 
	ADDR_COUNTRY VARCHAR(200), 
	ADDR_STATE VARCHAR(200), 
	ADDR_CITY VARCHAR(200), 
	ADDR_DETAIL VARCHAR(200), 
	ADDR_POST_CODE VARCHAR(200),
	NAME VARCHAR(200),
	PHONE_NUMBER VARCHAR(200),
	ADDR_DEF  BOOL default FALSE,
	ES_USER_ID bigint,
	primary key (id)
);



create table ES_ORDER (
	id bigint not null auto_increment,
	TRANSACTION_ID NUMERIC(30), 
	PRICE NUMERIC(6,2), 
	ORDER_STATE INT(2), 
	PAYMENT_TYPE INT(2),
	RETRIEVE_PLACE VARCHAR(1000),
	PAID_DATE  DATETIME, 
	FINISH_DATE DATETIME, 
	CANCEL_DATE DATETIME,
	DELIVERY_DATE DATETIME,
	LAST_UPDATE_DATE DATETIME,
	USER_ID bigint not null,
	primary key (id)
);

create table ES_ORDER_ITEM (
	id bigint not null auto_increment, 
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	ES_ORDER_ID bigint not null,
	primary key (id)
);


alter table ES_COMBO_ITEM_M add constraint FK_60ny9vea3t31no0269kf2b8y2 foreign key (ES_ITEM_ID) references ES_ITEM (id);

alter table ES_COMBO_ITEM_M add constraint FK_rq0h09mi3q7i3um10xr4t8v0i foreign key (ES_COMBO_ITEM_ID) references ES_COMBO_ITEM (id);

alter table ES_COMBO_ITEM_M add constraint UK_2d3wi5c7ydy3nq1pm4o2tql9a  unique (ES_COMBO_ITEM_ID, ES_ITEM_ID);

alter table ES_ORDER_ITEM add constraint FK_74aytr1c15gudg9yltny49y5a foreign key (ES_ORDER_ID) references ES_ORDER (id);


alter table ES_CATEGORY_ITEM add constraint FK_rq0h09mi3q7i3um10xr4t8v0b foreign key (ES_CATEGORY_ID) references ES_CATEGORY (id);

alter table ES_CATEGORY_ITEM add constraint UK_2d3wi5c7ydy3nq1pm4o2tql9c  unique (ES_CATEGORY_ID, ES_ITEM_ID);

alter table ES_CATEGORY_ITEM_SPECIAL_TYPE add constraint FK_74aytr1c15gudg9yltny49y43 foreign key (CATEGORY_ID) references ES_CATEGORY (id);

alter table ES_USER_ADDRESS add constraint FK_74aytr1c15gudg9yltny49y41 foreign key (ES_USER_ID) references ES_USER (id);





