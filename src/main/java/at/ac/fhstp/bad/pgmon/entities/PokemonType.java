package at.ac.fhstp.bad.pgmon.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PokemonType {
    @Id
    private Integer typeId;
    private String typeName_de;
    private String typeName_en;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName_de() {
        return typeName_de;
    }

    public void setTypeName_de(String typeName_de) {
        this.typeName_de = typeName_de;
    }

    public String getTypeName_en() {
        return typeName_en;
    }

    public void setTypeName_en(String typeName_en) {
        this.typeName_en = typeName_en;
    }
}
