
package com.example.web.tools.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;


public class AliPayHelper {

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    private static final String APP_ID = "9021000135645491";

    private static final String App_Private_Key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCxvpIqoODjhpKFaT3p9dfU4tP2JT8OF9sGSRBrAZs+NBVzjYAJcOeRjtn9z/11z+xYEXP5OjEbArTLjd15mbBOtA0lE6O1n3MDBFezY+BCCnAdCSvk8BTcyk6wifqrrYpH+S5Yl5JmLDgyIRIh14usw1DLncAZCRrx1/w7exNWNRs2thTwiU96y5e4Gfjh7Nkq0TgwpaCO3kTscaZrnuQU/3KFXiz0NUt1Ev+Ls4Hd0J3Z0swk0/SW4GbHKXNRfZ2OUsGt16CT9pXz2NYWLeit7xZouKMlKiAvWtB5uYsCtdBt0/2h8iTq8dQQh6gMkqtT/5j2e8R7M1fOZd5n4RvZAgMBAAECggEAfroG8Mm/Tbl24hPFBvg3KBuJUGgB6wRrLrINOJ3ul4z+9mIetZyAHjGhjusqJUNXmPromvs4Y6+sQ60kCTzgjVymOpckE6xZjf8coFRHYg6bHLibSSE7XqgHLqtRw/F5NBOfTZ7TzHEep+8YRHSvqz+ebW5Ntgvd4CxENAxuHvmPef7SNjoSAWGB9DYR8QMAHIupuVRgDvupqjw+AirpFGoUqFVMpcX/3YaryAIQIaSE4zXAuikHJW2z5f6ZHz3U4VsiH6qdfzQNH25ZIk0v4IGJ9LM9RWgX/bTiVz6EU1zWqaRRU1ADAqdnCNyAnp1xCMuWkvsqXhZMPsaIp8hYAQKBgQDykuUJHmn92S3B1DxRKDXXjA8oKJF2ehsjdGDsGOwDItp7Gi/c+VrPeac235Iu7lRWEbzZJN7zm65+DQQKVmfbcmQR7noW5MyRc0aet7xyi8dC2+z/WypfwIkvPAVpVm9R/+GFCm0q8AfzhLqT6c662RTs5gQZLRvNe6WeAjcSWQKBgQC7lRaMZrnzJdfkWDZgj8DiQyTG+PT19bXQVxsPNWHlXY3sJvAXIzIGlgoRlz78MpMc6AjHVa3Le/Z6tF5qczIjMm2TwfgerJ/dGhatqZlS6CC7/XQy+mQMiKbfmXT/Le3WLKsAP2nxTFNVcz90sra0Y0+SfIVQl47ub56l5QolgQKBgEgpOslDqvRBL0OCB499DsdYewnPR7afDIO3kz2pDEzCiYwldieeZk1iO0QvriUX8S7JiAxw04JUxcvpw1Hv8mVt2icbcmZtGg4K5O9CaWUJ//T7x70POcPSWv0aNeHoPKWAIW6ac29aiIMA8SAfZchf28G9i2qR6LtwFQy0S/3BAoGBAJNgZSDWLM5uMxrCe6aOCvs5yZFSVBNSgxA2/5mJVWrBgBnD1eo3yrwF8c80R1YZ3h9d5lfGZw9yS4rIX02/R+sDWbg3M1vWQJuUPbEfiMjse5t3XG4Y77YxPvp6NMOwMCaPXaKKMBBW6To/m+Fx7732MLRkhNF24eNPaxPnyXsBAoGBAIWP/ZmVVLGZuES01nrzTIMV/c2fj7NSbY4FVD8ymM1N1PEfrv33xseDe39A+Z9zvcCJXnpKXOqtIzHp9ENrE/AtBgXRineDP0UUTjC5v6EetBwE45u5y5KzL7OBtQKvSZaXTVg7typS4Gn7UENYccCdYyGOiOcXXJLg+DyW2MdC";

    private static final String App_Public_Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjEZh/2i90xz0JhYZjxcOQ6L3rhPUbh+AB4+uQCMz/xA/OhG4x8hDr5MHbJqCc53Utcn5swtCYteDhvxvwQtgutYXCEVv73NG2hubUjbtoCq/OzWvrGNFothUA/4K/Re+BGypjUguvRYrjcptC9fxvx4QLD9ZPrqmiCPsUK7UmvCm7LbvFrBo+IP7gEOY/IkNohH6PU/iZ4IhrKcQMt541B0d69Ra17V4AsFkxqe+O09XrXKZ20JO00lFdcgJknAoFuIdszD4Y79Q63yT4ZxWd39Ad82jrwqpLLwzxK4fcGxktE3yDBy19dEc+FsEKFLTZwG6wLr5nMPkGPdJMozuiQIDAQAB";


    public static AlipayClient GetClient() {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID,
                App_Private_Key, "json", "UTF-8", App_Public_Key, SIGN_TYPE);
        return alipayClient;
    }

    /**
     * 支付宝进行支付流程
     *
     * @param subject     名称
     * @param outTradeNo  订单号
     * @param totalAmount 金额
     * @param callBackUrl 回调地址
     * @return
     * @throws Exception
     */
    public static String CreatePay(String body, String subject, String outTradeNo, String totalAmount, String callBackUrl) throws Exception {

        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = GetClient();

        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl("");
        request.setReturnUrl(callBackUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);  // 我们自己生成的订单编号
        bizContent.put("total_amount", totalAmount); // 订单的总金额
        bizContent.put("subject", subject);   // 支付的名称
        bizContent.put("body", body);   // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    public static String QueryPayStatus(String outTradeNo) {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = GetClient();
        //请求
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //数据
        AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
        bizModel.setOutTradeNo(outTradeNo);
        request.setBizModel(bizModel);
        try {
            //完成签名并执行请求
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response.getBody();

            } else {

                return null;
            }
        } catch (AlipayApiException e) {

            return null;
        }


    }

    public static String CreateReturn(String OrderNo, String PayOrderNo, String returnMoney) throws AlipayApiException {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = GetClient();
        // 构造请求参数以调用接口
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();

        // 设置商户订单号
        model.setOutTradeNo(PayOrderNo);


        // 设置退款金额
        model.setRefundAmount(returnMoney);


        // 设置退款原因说明
        model.setRefundReason("正常退款");

        // 设置退款请求号
        model.setOutRequestNo(OrderNo);

        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);

        if (response.isSuccess()) {
            return response.getBody();
        } else {
            return null;
        }
    }

}
