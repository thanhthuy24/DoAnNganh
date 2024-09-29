package com.htt.ecourse.components;

import org.springframework.beans.factory.annotation.Value;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class MomoService {

    @Value("${momo.partnerCode}")
    private String partnerCode;

    @Value("${momo.accessKey}")
    private String accessKey;

    @Value("${momo.secretKey}")
    private String secretKey;

    @Value("${momo.endpoint}")
    private String endpoint;

    public String createSignature(String rawData) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(rawData.getBytes()));
    }


    public String createPaymentRequest(String orderId, float amount, String returnUrl) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(endpoint);

        String orderInfo = "Thanh toán đơn hàng";
        String notifyUrl = "http://localhost:3000/cart";
        String requestType = "captureMoMoWallet";

        String rawSignature = "partnerCode=" + partnerCode
                + "&accessKey=" + accessKey
                + "&requestId=" + orderId
                + "&amount=" + String.valueOf((int) amount)
                + "&orderId=" + orderId
                + "&orderInfo=" + orderInfo
                + "&returnUrl=" + returnUrl
                + "&notifyUrl=" + notifyUrl
                + "&extraData=";  // Phải để extraData là rỗng như trong JSON

        String signature = createSignature(rawSignature);

        String json = "{"
                + "\"partnerCode\": \"" + partnerCode + "\","
                + "\"accessKey\": \"" + accessKey + "\","
                + "\"requestId\": \"" + orderId + "\","
                + "\"amount\": \"" + String.valueOf((int) amount) + "\","
                + "\"orderId\": \"" + orderId + "\","
                + "\"orderInfo\": \"" + orderInfo + "\","
                + "\"returnUrl\": \"" + returnUrl + "\","
                + "\"notifyUrl\": \"" + notifyUrl + "\","
                + "\"extraData\": \"\","
                + "\"requestType\": \"" + requestType + "\","
                + "\"signature\": \"" + signature + "\""
                + "}";

        post.setEntity(new StringEntity(json, "UTF-8"));
        post.setHeader("Content-Type", "application/json");

        HttpResponse response = client.execute(post);
        return EntityUtils.toString(response.getEntity());

//        HttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost(endpoint);
//
//        String orderInfo = "Thanh toán đơn hàng";
//        String notifyUrl = "http://localhost:3000/cart";
//        String requestType = "captureATM"; // Thay đổi thành captureATM
//
//        String rawSignature = "partnerCode=" + partnerCode
//                + "&accessKey=" + accessKey
//                + "&requestId=" + orderId
//                + "&amount=" + String.valueOf((int) amount)
//                + "&orderId=" + orderId
//                + "&orderInfo=" + orderInfo
//                + "&returnUrl=" + returnUrl
//                + "&notifyUrl=" + notifyUrl
//                + "&extraData=";
//
//        String signature = createSignature(rawSignature);
//
//        String json = String.format("{\"partnerCode\": \"%s\",\"accessKey\": \"%s\",\"requestId\": \"%s\"," +
//                        "\"amount\": \"%d\",\"orderId\": \"%s\",\"orderInfo\": \"%s\"," +
//                        "\"returnUrl\": \"%s\",\"notifyUrl\": \"%s\",\"extraData\": \"\"," +
//                        "\"requestType\": \"%s\",\"signature\": \"%s\"}",
//                partnerCode, accessKey, orderId, String.valueOf((int) amount), orderId, orderInfo, returnUrl, notifyUrl, requestType, signature);
//
//        post.setEntity(new StringEntity(json, "UTF-8"));
//        post.setHeader("Content-Type", "application/json");
//
//        HttpResponse response = client.execute(post);
//        return EntityUtils.toString(response.getEntity());
    }
}