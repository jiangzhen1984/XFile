 create table ES_ITEM (
	id bigint not null auto_increment,
	DESCRIPTION VARCHAR(1000),
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	STUFF VARCHAR(100), 
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

 create table ES_CATEGORY (
	id bigint not null auto_increment,
	NAME VARCHAR(100), 
	SEQ_NUMBER NUMERIC(2), 
	PARENT_ID NUMERIC(20), 
	primary key (id)
);

create table ES_COMBO_ITEM_M (
	ES_COMBO_ITEM_ID bigint not null, 
	ES_ITEM_ID bigint not null,
	primary key (ES_COMBO_ITEM_ID, ES_ITEM_ID)
);

create table TB_USER (
	id bigint not null auto_increment, 
	ADDRESS VARCHAR(200), 
	CELL_PHONE VARCHAR(40), 
	NAME VARCHAR(40), 
	USER_PWD VARCHAR(40), 
	MAIL VARCHAR(100),
	primary key (id)
);


create table ES_ORDER (
	id bigint not null auto_increment,
	ORDER_DATE DATETIME, 
	PRICE NUMERIC(6,2), 
	ORDER_STATE NUMERIC(2), 
	TRANSACTION_ID NUMERIC(20), 
	primary key (id)
);

create table ES_ORDER_ITEM (
	id bigint not null auto_increment, 
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	ORDER_ID bigint not null,
	primary key (id)
);


alter table ES_COMBO_ITEM_M add constraint FK_60ny9vea3t31no0269kf2b8y2 foreign key (ES_ITEM_ID) references ES_ITEM (id);

alter table ES_COMBO_ITEM_M add constraint FK_rq0h09mi3q7i3um10xr4t8v0i foreign key (ES_COMBO_ITEM_ID) references ES_COMBO_ITEM (id);

alter table ES_COMBO_ITEM_M add constraint UK_2d3wi5c7ydy3nq1pm4o2tql9a  unique (ES_COMBO_ITEM_ID, ES_ITEM_ID);

alter table ES_ORDER_ITEM add constraint FK_74aytr1c15gudg9yltny49y5a foreign key (ORDER_ID) references ES_ORDER (id);

