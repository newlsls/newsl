package test2;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest; 

import com.fasterxml.jackson.databind.ObjectMapper;

/**
*
* 快递鸟订阅推送2.0接口
*
* @技术QQ: 4009633321
* @技术QQ群: 200121393
* @see: http://www.kdniao.com/api-subscribe
* @copyright: 深圳市快金数据技术服务有限公司
* 
* ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
*/

public class KdniaoSubscribeAPI {
	
	//DEMO
	public static void main(String[] args) {
		KdniaoSubscribeAPI api = new KdniaoSubscribeAPI();
		try {
			String result = api.orderTracesSubByJson();
			System.out.print("=="+result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//电商ID
	private String EBusinessID="1293604";
	//private String EBusinessID="请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
//	private String AppKey="请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";
	
	private String AppKey="ed3e001e-c0dd-4a8a-ab87-093c9174a542";
	//测试请求url
	private String ReqURL = "http://testapi.kdniao.cc:8081/api/dist";
	//正式请求url
	//private String ReqURL = "http://api.kdniao.cc/api/dist";
 
	/**
     * Json方式  物流信息订阅
	 * @throws Exception 
     */
	public String orderTracesSubByJson() throws Exception{
		
		Map <String,Object>map=new HashMap();
		//快递公司编码
		String ShipperCode="SF";
		map.put("ShipperCode", ShipperCode);
		
		//快递单号
		String LogisticCode="3100707578976";
		map.put("LogisticCode", LogisticCode);
		
		//订单编号
		String OrderCode="SF201608081055208281";
		map.put("OrderCode", OrderCode);
		
		//支付类型 1-现付，2-到付，3-月结，4-第三方支付
		String PayType="1";
		map.put("PayType", PayType);
		
		
		//快递类型：1-标准快件
		String ExpType="1";
		map.put("ExpType", ExpType);
		
		
		//寄件费（运费）
		double Cost=1.0;
		map.put("Cost", Cost);
		
		//其他费用
		double OtherCost=1.0;
		map.put("OtherCost", OtherCost);
		
		Map <String,Object>Receiver =new HashMap();
		
		//收件人公司
		String Company="GCCUI";
		Receiver.put("Company", Company);
		
		//收件人
		String Name="Yann";
		Receiver.put("Name", Name);
		
		//手机
		String Mobile="15018442396";
		Receiver.put("Mobile", Mobile);
		
		//省
		String ProvinceName="北京";
		Receiver.put("ProvinceName", ProvinceName);
		
		//城
		String CityName="北京";
		Receiver.put("CityName", CityName);
		
		//区
		String ExpAreaName="朝阳区";
		Receiver.put("ExpAreaName", ExpAreaName);
		
		//详细地址
		String Address="三里屯街道雅秀大厦";
		Receiver.put("Address", Address);
				
		map.put("Receiver", Receiver);
		
		Map <String,Object>Sender =new HashMap();
		
		//收件人公司
		 Company="GCCUI";
		 Sender.put("Company", Company);
		
		//收件人
		 Name="Yann";
		 Sender.put("Name", Name);
		
		//手机
		 Mobile="15018442396";
		 Sender.put("Mobile", Mobile);
		
		//省
		 ProvinceName="北京";
		 Sender.put("ProvinceName", ProvinceName);
		
		//城
		 CityName="北京";
		 Sender.put("CityName", CityName);
		
		//区
		 ExpAreaName="朝阳区";
		 Sender.put("ExpAreaName", ExpAreaName);
		
		//详细地址
		 Address="三里屯街道雅秀大厦";
		 Sender.put("Address", Address);
				
		map.put("Sender", Sender);
		
		Map info =new HashMap();
		
		String GoodsName="鞋子";
		info.put("GoodsName", GoodsName);
		
		int Goodsquantity=1;
		info.put("Goodsquantity", Goodsquantity);
		
		double GoodsWeight=1.0;
		info.put("GoodsWeight", GoodsWeight);
		
		ArrayList Commodity= new ArrayList();
		Commodity.add(info);
		
		map.put("Commodity", Commodity);
		
		
//		String requestData="{'OrderCode': 'SF201608081055208281'," +
//                                "'ShipperCode':'SF'," +
//                                "'LogisticCode':'3100707578976'," +
//                                "'PayType':1," +
//                                "'ExpType':1," +
//                                "'CustomerName':'',"+
//                                "'CustomerPwd':''," +
//                                "'MonthCode':''," +
//                                "'IsNotice':0," +
//                                "'Cost':1.0," +
//                                "'OtherCost':1.0," +
//                                "'Sender':" +
//                                "{" +
//                                "'Company':'LV','Name':'Taylor','Mobile':'15018442396','ProvinceName':'上海','CityName':'上海','ExpAreaName':'青浦区','Address':'明珠路73号'}," +
//                                "'Receiver':" +
//                                "{" +
//                                "'Company':'GCCUI','Name':'Yann','Mobile':'15018442396','ProvinceName':'北京','CityName':'北京','ExpAreaName':'朝阳区','Address':'三里屯街道雅秀大厦'}," +
//                                "'Commodity':" +
//                                "[{" +
//                                "'GoodsName':'鞋子','Goodsquantity':1,'GoodsWeight':1.0}]," +
//                                "'Weight':1.0," +
//                                "'Quantity':1," +
//                                "'Volume':0.0," +
//                                "'Remark':'小心轻放'}";
		String requestData=new ObjectMapper().writeValueAsString(map);
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1008");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=sendPost(ReqURL, params);	
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
		
	/**
     * MD5加密
     * @param str 内容       
     * @param charset 编码方式
	 * @throws Exception 
     */
	@SuppressWarnings("unused")
	private String MD5(String str, String charset) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(str.getBytes(charset));
	    byte[] result = md.digest();
	    StringBuffer sb = new StringBuffer(32);
	    for (int i = 0; i < result.length; i++) {
	        int val = result[i] & 0xff;
	        if (val <= 0xf) {
	            sb.append("0");
	        }
	        sb.append(Integer.toHexString(val));
	    }
	    return sb.toString().toLowerCase();
	}
	
	/**
     * base64编码
     * @param str 内容       
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException 
     */
	private String base64(String str, String charset) throws UnsupportedEncodingException{
		String encoded = base64Encode(str.getBytes(charset));
		return encoded;    
	}	
	
	@SuppressWarnings("unused")
	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}
	
	/**
     * 电商Sign签名生成
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
     */
	@SuppressWarnings("unused")
	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
	{
		if (keyValue != null)
		{
			return base64(MD5(content + keyValue, charset), charset);
		}
		return base64(MD5(content, charset), charset);
	}
	
	 /**
     * 向指定 URL 发送POST方法的请求     
     * @param url 发送请求的 URL    
     * @param params 请求的参数集合     
     * @return 远程资源的响应结果
     */
	@SuppressWarnings("unused")
	private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		        	  System.out.println(entry.getKey()+":"+entry.getValue());
		          }
		          System.out.println("param:"+param.toString());
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
	
    private static char[] base64EncodeChars = new char[] { 
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
            'w', 'x', 'y', 'z', '0', '1', '2', '3', 
            '4', '5', '6', '7', '8', '9', '+', '/' }; 
    	
    public static String base64Encode(byte[] data) { 
        StringBuffer sb = new StringBuffer(); 
        int len = data.length; 
        int i = 0; 
        int b1, b2, b3; 
        while (i < len) { 
            b1 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]); 
                sb.append("=="); 
                break; 
            } 
            b2 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]); 
                sb.append("="); 
                break; 
            } 
            b3 = data[i++] & 0xff; 
            sb.append(base64EncodeChars[b1 >>> 2]); 
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]); 
            sb.append(base64EncodeChars[b3 & 0x3f]); 
        } 
        return sb.toString(); 
    }
}