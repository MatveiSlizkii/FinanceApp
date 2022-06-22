package by.it_academy.jd2.hw.example.messenger.dao.entities;



import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
@Entity
@Table(name = "test", schema = "app")
public class TestEntity implements Serializable {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    //@Lob
//    @Type(type = "")
//    @Column(name = "json", columnDefinition = "jsonb")
    private String json;

    public TestEntity() {
    }

    public TestEntity(UUID uuid, String json) {
        this.uuid = uuid;
        this.json = json;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
