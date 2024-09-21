package com.htt.ecourse.components;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
//import javax.xml.*;

@Service
public class VNPayService {
    @Value("${vnpay.tmn_code}")
    private String tmnCode;

    @Value("${vnpay.hash_secret}")
    private String hashSecret;

    @Value("${vnpay.url}")
    private String vnpUrl;

    @Value("${vnpay.return_url}")
    private String returnUrl;

    public String createPaymentURL(Long orderId, float amount, String orderInfo) {
        Map<String, String> vnp_Params = new HashMap<>();
//        String ipAddr = request.getRemoteAddr();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((long)(amount * 100))); // Số tiền tính bằng VND (nhân 100)
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(orderId));
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", "192.168.2.7");
        vnp_Params.put("vnp_CreateDate", getCurrentTime());

        // Tạo chuỗi hash để mã hóa
        String query = buildQueryString(vnp_Params);
        String vnp_SecureHash = hmacSHA512(hashSecret, query);
        query += "&vnp_SecureHash=" + vnp_SecureHash;

        return vnpUrl + "?" + query;
    }

    private String buildQueryString(Map<String, String> params) {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            if (query.length() > 0) {
                query.append("&");
            }
            query.append(fieldName).append("=").append(params.get(fieldName));
        }
        return query.toString();
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmac.init(secretKey);
            byte[] hashBytes = hmac.doFinal(data.getBytes());
            return bytesToHex(hashBytes).toLowerCase();

        } catch (Exception e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    public static String bytesToHex(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }
}
