 create table BF_BREAKFAST (
	id bigint not null auto_increment,
	DESCRIPTION VARCHAR(100),
	IS_TOADY BOOL default TRUE not null,
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	STUFF VARCHAR(100), 
	primary key (id)
);


 create table BF_BREAKFAST_COMBO (
	id bigint not null auto_increment, 
	DESCRIPTION VARCHAR(100), 
	IS_TODAY bool default TRUE not null, 
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	STUFF VARCHAR(100), 
	primary key (id)
);

create table BF_COMBO_ITEM (
	COMBO_ID bigint not null, 
	BF_ID bigint not null,
	primary key (COMBO_ID, BF_ID)
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

create table BF_PLACE (
	id bigint not null auto_increment, 
	ADDRESS VARCHAR(200), 
	DISTRICT VARCHAR(50), 
	primary key (id)
);


create table BF_ORDER (
	id bigint not null auto_increment,
	ORDER_DATE DATETIME, 
	PRICE NUMERIC(6,2), 
	ORDER_STATE NUMERIC(2), 
	TRANSACTION_ID NUMERIC(20), 
	primary key (id)
);

create table BF_ORDER_ITEM (
	id bigint not null auto_increment, 
	NAME VARCHAR(40), 
	PIC_PATH VARCHAR(200), 
	PRICE NUMERIC(6,2), 
	ORDER_ID bigint not null,
	primary key (id)
);


alter table BF_COMBO_ITEM add constraint FK_60ny9vea3t31no0269kf2b8yb foreign key (BF_ID) references BF_BREAKFAST (id);

alter table BF_COMBO_ITEM add constraint FK_rq0h09mi3q7i3um10xr4t8v0t foreign key (COMBO_ID) references BF_BREAKFAST_COMBO (id);

alter table BF_COMBO_ITEM add constraint UK_2d3wi5c7ydy3nq1pm4o2tql9w  unique (COMBO_ID, BF_ID);

alter table BF_ORDER_ITEM add constraint FK_74aytr1c15gudg9yltny49y5a foreign key (order_id) references BF_ORDER (id);

