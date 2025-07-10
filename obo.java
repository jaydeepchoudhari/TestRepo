package duke.aigis.fho.uvt.migration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SOLAR_FARMS_PANELS", schema = "SolarHealth")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolarFarmPanel {
    
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "geom")
    private String geom;

    @Column(name = "site_id")
    @JsonProperty("siteId")
    private Short siteId;

    @Column(name = "panel_id")
    @JsonProperty("panelId")
    private String panelId;

    @Column(name = "inverter")
    private String inverter;

    @Column(name = "combiner")
    private String combiner;

    @Column(name = "string")
    @JsonProperty("panelString")
    private String panelString;

}


package duke.aigis.fho.uvt.migration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "SOLAR_FARMS", schema = "SolarHealth")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class SolarFarm {
    
    @Id
    @Column(name = "site_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty("siteId")
    private Short siteId;

    @Column(name = "site_name")
    @JsonProperty("siteName")
    private String siteName;

    @Column(name = "address")
    private String address;

    @Column(name = "active")
    @JsonProperty("isActive")
    private  Boolean isActive;

    @Column(name = "Nickname")
    @JsonProperty("nickName")
    private String nickName;

    @Column(name = "current_state")
    @JsonProperty("currentState")
    private Integer currentState;
    
    @Column(name = "geom")
    private String geom;

    @Column(name = "panel_phototropic")
    @JsonProperty("panelPhotographic")
    private Boolean panelPhotographic;

    @Column(name = "Maximo_Site_Id")
    @JsonProperty("maximoSiteId")
    private String maximoSiteId;
    /*
    @OneToMany(mappedBy = "siteId", fetch = FetchType.LAZY)
    @JsonProperty("panels")
    private List<SolarFarmPanel> panels; */
    
}
