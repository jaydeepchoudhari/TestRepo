create schema if not exists SolarHealth;

create table SolarHealth.solar_farms (
    site_id serial primary key,
    site_name varchar(255),
    address varchar(255),
    active boolean,
    nickname varchar(255),
    current_state integer,
    geom text,
    panel_phototropic boolean,
    maximo_site_id varchar(255),
    created_by VARCHAR(255) NOT NULL,
    created_ts TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(255),
    modified_ts TIMESTAMP WITH TIME ZONE
);

create table SolarHealth.solar_farms_panels (
    id serial primary key,
    geom text,
    site_id smallint not null,
    panel_id varchar(255),
    inverter varchar(255),
    combiner varchar(255),
    string varchar(255),
    created_by VARCHAR(255) NOT NULL,
    created_ts TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(255),
    modified_ts TIMESTAMP WITH TIME ZONE
    constraint fk_solar_farm_panel 
        foreign key (site_id) 
        references SolarHealth.solar_farms (site_id)
        on delete cascade
);

create index idx_solar_farms_panels_site_id 
    on SolarHealth.solar_farms_panels (site_id);
