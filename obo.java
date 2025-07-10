CREATE SCHEMA IF NOT EXISTS "SolarHealth";

CREATE TABLE "SolarHealth"."SOLAR_FARMS" (
    "site_id" SERIAL PRIMARY KEY,
    "site_name" VARCHAR(255),
    "address" VARCHAR(255),
    "active" BOOLEAN,
    "Nickname" VARCHAR(255),
    "current_state" INTEGER,
    "geom" TEXT,
    "panel_phototropic" BOOLEAN,
    "Maximo_Site_Id" VARCHAR(255)
);

CREATE TABLE "SolarHealth"."SOLAR_FARMS_PANELS" (
    "id" SERIAL PRIMARY KEY,
    "geom" TEXT,
    "site_id" SMALLINT NOT NULL,
    "panel_id" VARCHAR(255),
    "inverter" VARCHAR(255),
    "combiner" VARCHAR(255),
    "string" VARCHAR(255),
    CONSTRAINT "fk_solar_farm_panel" 
        FOREIGN KEY ("site_id") 
        REFERENCES "SolarHealth"."SOLAR_FARMS" ("site_id")
        ON DELETE CASCADE
);

CREATE INDEX "idx_solar_farms_panels_site_id" 
    ON "SolarHealth"."SOLAR_FARMS_PANELS" ("site_id");
