package zero.springboot.study.redission.service;

import org.redisson.api.*;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.api.geo.OptionalGeoSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * Adds geospatial members.
     * @param key geo 集合 key
     * @param member member name
     * @param longitude 经度
     * @param latitude 维度
     * @return number of elements added to the sorted set, not including elements already existing for which the score was updated
     */
    public long add(String key, String member, Double longitude, Double latitude) {
        RGeo<String> geo = redissonClient.getGeo(key);

        return geo.add(new GeoEntry(longitude, latitude, member));
    }

    /**
     * 根据 member name 所在的经纬度为中心，搜索圆形区域内满足条件的 member 集合
     * @param key
     * @param member
     * @param count
     * @param radius
     * @param geoUnit
     * @return
     */
    public List<String> searchFromMember(String key, String member, int count
            , double radius, GeoUnit geoUnit) {
        RGeo<String> geo = redissonClient.getGeo(key);

        OptionalGeoSearch geoSearch = GeoSearchArgs
                .from(member)
                .radius(radius, geoUnit)
                .order(GeoOrder.ASC)
                .count(count);

        return geo.search(geoSearch);

    }

    /**
     * 根据给定经纬度为圆心，搜索附近区域满足条件的 member 列表
     * @param key
     * @param longitude
     * @param latitude
     * @param count
     * @param radius
     * @param geoUnit
     * @return
     */
    public List<String> searchFromLonLat(String key, Double longitude, Double latitude, int count
            , double radius, GeoUnit geoUnit) {
        RGeo<String> geo = redissonClient.getGeo(key);

        OptionalGeoSearch geoSearch = GeoSearchArgs
                .from(longitude, latitude)
                .radius(10, GeoUnit.KILOMETERS)
                .order(GeoOrder.ASC)
                .count(count);

        return geo.search(geoSearch);
    }

    /**
     * 删除坐标
     * @param key
     * @param member
     * @return true if an element was removed as a result of this call
     */
    public boolean delete(String key, String member) {
        RGeo<String> geo = redissonClient.getGeo(key);
        return geo.remove(member);
    }


}
