package com.itheima.common.utils;


import com.itheima.common.constants.SystemConstants;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author Arthurocky
 */
public class AppJwtUtil {

    /**
     * TOKEN的有效期一天（S）
      */
    private static final int TOKEN_TIME_OUT = 3600;

    /**
     * 加密KEY
     */
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";

    /**
     * 最小刷新间隔(S)
     */
    private static final int REFRESH_TIME = 300;

    /**
     * 生产令牌ID
     * @param id-登录用户的id值
     * @return
     */
    public static String createToken(Long id) {
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id", id);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                //令牌的唯一标识
                .setId(UUID.randomUUID().toString())
                //签发时间
                .setIssuedAt(new Date(currentTime))
                //说明
                .setSubject("system")
                //签发者信息
                .setIssuer("heima")
                //接收用户
                .setAudience("app")
                //数据压缩方式
                .compressWith(CompressionCodecs.GZIP)
                //加密方式
                .signWith(SignatureAlgorithm.HS512, generalKey())
                //过期一个小时--过期时间戳
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))
                //cla信息
                .addClaims(claimMaps)
                .compact();
    }

    /**
     * 获取token中的claims信息
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 校验是否过期
     * @param token
     * @return 1 有效  0 无效  2 已过期
     */
    public static Integer verifyToken(String token) {

        try {
            Claims claims = AppJwtUtil.getClaimsBody(token);
            if (claims == null) {
                return SystemConstants.JWT_FAIL;
            }
            return SystemConstants.JWT_OK;
        } catch (ExpiredJwtException ex) {
            return SystemConstants.JWT_EXPIRE;
        } catch (Exception e) {
            return SystemConstants.JWT_FAIL;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        //对称加密
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
       /* Map map = new HashMap();
        map.put("id","11");*/
        //生成令牌
        String token = AppJwtUtil.createToken(1102L);
        System.out.println(token);

        //校验令牌
        Integer integer = AppJwtUtil.verifyToken("dsafafsa");
        System.out.println(integer);

        Claims claims = AppJwtUtil.getClaimsBody(token);
        System.out.println(claims);

    }
}