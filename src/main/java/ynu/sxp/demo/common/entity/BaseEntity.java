package ynu.sxp.demo.common.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@MappedSuperclass
@GenericGenerator(name = "uuid2", strategy = "uuid2")
public class BaseEntity {
    @Id @GeneratedValue(generator = "uuid2")
    UUID id;
}
