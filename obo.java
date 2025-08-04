//Existing Opex Locations
package com.dukeenergy.formula.model.entities;

import lombok.Data;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "LOCATIONS")
public class LocationEntity {
    @Id
    String uuid;
    @Column(name = "site_id")
    String siteId;
    @Column(name = "site_name")
    String siteName;
    @Column(name = "address_1")
    String address1;
    @Column(name = "address_2")
    String address2;
    @Column(name = "city")
    String city;
    @Column(name = "state_code")
    String stateCode;
    @Column(name = "postal_code")
    String postalCode;
    @Column(name = "county")
    String county;
    @Column(name = "created")
    Date created;
    @Column(name = "created_by")
    String createdBy;
    @Column(name = "updated")
    Date updated;
    @Column(name = "updated_by")
    String updatedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locationId")
    private Set<BuildingEntity> buildings;
}

//Existing buildings:

package com.dukeenergy.formula.model.entities;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BUILDINGS")
public class BuildingEntity {
    @Id
    String uuid;
    @Column(name = "name")
    String name;
    @Column(name = "building_id")
    Integer buildingId;
    @Column(name = "building_code")
    String buildingCode;
    @Column(name = "location_id")
    String locationId;
}

//Source of buildings

package com.dukeenergy.formula.model.dto;

public interface BuildingViewDTO {
    String getSiteId();
    String getBuildingId();
    String getBuildingCode();
    String getBuildingName();
}
