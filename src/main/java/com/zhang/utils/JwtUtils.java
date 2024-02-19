package com.zhang.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

/**
 * JWT工具类
 * @author zhang
 * @date 2024/2/13
 * @Description
 */

public class JwtUtils {

    //有效期为
    public static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 10 *1000L;
    public static final Long REFRESH_TOKEN_VALIDITY_SECONDS = 10 * 60 * 60 *1000L;
    public static final Long JWT_TTL = 60 * 60 *1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "test";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jtw字符串
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt字符串
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }
    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    public static String createAccessToken(String subject){
        return createJWT(subject,ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    public static String createRefreshToken(String subject){
        return createJWT(subject,REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtils.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)               //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("sg")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate); //使用HS256对称加密算法签名, 第二个参数为秘钥
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt){
        Claims claims;
        SecretKey secretKey = generalKey();
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey) // 设置标识名
                    .parseClaimsJws(jwt)  //解析token
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }



    public static void main(String[] args) throws Exception {
        /*String jwt = createJWT("123456");
        Claims claims = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlN2YxNTkyYzY3NTk0NjEwYjRmYjMxMzI1MmI0MDkyZCIsInN1YiI6IjE3NTE3OTE4ODk0Mzk5ODk3NjEiLCJpc3MiOiJzZyIsImlhdCI6MTcwNzk3NTYwMCwiZXhwIjoxNzA3OTc5MjAwfQ.3s25E3om5rSn5_YtHLrHuOfbvm_5vx9NzoDYM88MUy8");

        System.out.println(claims.getSubject());*/
        System.out.println(parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyYTU2OTA4MzljOTg0ZjIwYmVmNzc3ZmI4ZmE2MjcwNCIsInN1YiI6IjE3NTgyODQ5MDExNDE2MzUwNzQiLCJpc3MiOiJzZyIsImlhdCI6MTcwODMyMjU1OSwiZXhwIjoxNzA4MzIyNTYwfQ.IQCKprMYzeNYLwJkfqY-qTlf6pzQfoeFUG_EkndYlMQ").getExpiration());
    }

}

