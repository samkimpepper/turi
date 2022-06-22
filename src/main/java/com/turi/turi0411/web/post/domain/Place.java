package com.turi.turi0411.web.post.domain;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;

    @Column(columnDefinition = "geometry")
    private Point location;

    @Column(unique = true)
    private String roadAddress;

    private String jibunAddress;

    private double x;

    private double y;

    @Enumerated(EnumType.STRING)
    private PostType type;
}
