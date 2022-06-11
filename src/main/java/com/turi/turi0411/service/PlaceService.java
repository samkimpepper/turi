package com.turi.turi0411.service;

import com.turi.turi0411.dto.post.PlaceDto;
import com.turi.turi0411.entity.Place;
import com.turi.turi0411.entity.PostType;
import com.turi.turi0411.exception.NotFoundException;
import com.turi.turi0411.repository.PlaceRepository;
import com.turi.turi0411.util.CardinalDirection;
import com.turi.turi0411.util.GeometryUtils;
import com.turi.turi0411.util.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final EntityManager entityManager;

    @Transactional
    public Place create(PlaceDto placeDto) {
        if(placeRepository.existsByRoadAddress(placeDto.getRoadAddress())) {
            Place place = placeRepository.findByRoadAddress(placeDto.getRoadAddress()).orElseThrow(() -> new NotFoundException("존재하지 않는 장소"));
            return place;
        }

        String pointWKT = String.format("POINT(%s %s)",placeDto.getX(), placeDto.getY());
        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch(ParseException ex) {
            ex.printStackTrace();
            return null;
        }

        Place place = Place.builder()
                .placeName(placeDto.getPlaceName())
                .location(point)
                .jibunAddress(placeDto.getJibunAddress())
                .roadAddress(placeDto.getRoadAddress())
                .x(placeDto.getX())
                .y(placeDto.getY())
                .type(PostType.valueOf(placeDto.getPlaceType()))
                .build();

        placeRepository.save(place);
        return place;
    }

    @Transactional
    public Place create(Place place) {
        if(placeRepository.existsByRoadAddress(place.getRoadAddress())) {
            place = placeRepository.findByRoadAddress(place.getRoadAddress()).orElseThrow(() -> new NotFoundException("존재하지 않는 장소"));
            return place;
        }


        String pointWKT = String.format("POINT(%s %s)",place.getX(), place.getY());
        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch(ParseException ex) {
            ex.printStackTrace();
            return null;
        }

        place.setLocation(point);

        placeRepository.save(place);
        return place;
    }

    public List<PlaceDto> getNearPlaces(double x, double y, String type) {

        PostType postType = PostType.valueOf(type);

//        double baseLat = 37.5099490;
//        double baseLong = 127.1090094;
        double baseLat = y;
        double baseLong = x;
        int distance = 3; // km

        Location northEast = GeometryUtils.calculateByDirection(baseLat, baseLong, distance, CardinalDirection.NORTHEAST.getBearing());
        Location southWest = GeometryUtils.calculateByDirection(baseLat, baseLong, distance, CardinalDirection.SOUTHWEST.getBearing());

        double x1 = northEast.getLongitude();
        double y1 = northEast.getLatitude();
        double x2 = southWest.getLongitude();
        double y2 = southWest.getLatitude();

        Query query = entityManager.createNativeQuery("" +
                        "SELECT * \n" +
                        "FROM place AS p \n" +
                        "WHERE p.type = :type AND " +
                        "MBRContains(ST_LINESTRINGFROMTEXT(" + String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2) + ", p.location)"
                , Place.class).setParameter("type", type);

        List<Place> results = query.getResultList();
        List<PlaceDto> data = results.stream()
                .map(PlaceDto::placeToDto)
                .collect(Collectors.toList());
        return data;
    }

    public List<PlaceDto> getPlaceSearchResult(String keyword, String type) {
        PostType postType = PostType.valueOf(type);


        List<Place> results = placeRepository.findAllByRoadAddressContainingAndTypeEquals(keyword, postType);

        return results.stream()
                .map(place -> {
                    return PlaceDto.placeToDto(place);
                })
                .collect(Collectors.toList());
    }

    public Place findById(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new NotFoundException("존재하지 않는 장소"));
    }
}
