package com.turi.turi0411.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="place_id")
    private Place place;

    private String postImageUrl;

    private double x;

    private double y;

    private String roadAddress;

    private String jibunAddress;

    private String placeName;

    private int likeCount;

    // 0~5만
    private int rating;

    public int increaseLikeCount() {
        return ++likeCount;
    }

}
