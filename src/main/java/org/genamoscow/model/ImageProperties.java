package org.genamoscow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class ImageProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "IMAGE_FILENAME")
    private String imageFileName;

    @Column(name = "IMAGE_LENGTH")
    @NotNull
    private long imageLength;

//    private Long id;
//    private String firstName;
//    private String middleName;
//    private String lastName;
//    private String imageFileName;
//    private long imageLength;
}
