package pic;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.*;

public class Getpic {
    
    public static pic[] doGet(int num) {
        String path = "https://api.lolicon.app/setu/?apikey=232368045f2bc262c4e5e4&size1200=true&num=";
        path = path+num;
        OutputStream out = null;
        BufferedReader br = null;
        String result = "";
        pic[] pic = new pic[num];
        JSONObject obj1 = null;
        try {
            System.out.println("׼����ʼ");
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            System.out.println("��������");

            InputStream is = conn.getInputStream();
            System.out.println("��ȡ���������");
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            System.out.println("��ʼд��json");
            JSONObject obj = JSON.parseObject(br.readLine());
            JSONArray jsonArray = JSONArray.parseArray(obj.getString("data"));
            for (int i=0; i<pic.length; i++){
                obj1 = jsonArray.getJSONObject(i);
                pic[i] = JSONObject.parseObject(String.valueOf(obj1),pic.class);
            }

//            for (Map.Entry<String, Object> entry : json.entrySet()) {
//                System.out.println(entry.getKey() + ":" + entry.getValue());
//            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pic;
    }
    public static pic doGet(String path, String date){
        OutputStream out = null;
        BufferedReader br = null;
        String result = "";
        pic pic = null;
        try {
            System.out.println("׼����ʼ1");
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            conn.setReadTimeout(10*1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            System.out.println("��������2");

            InputStream is = conn.getInputStream();
            System.out.println("��ȡ���������3");
            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            System.out.println("��ʼд��json4");
            JSONObject obj = JSON.parseObject(br.readLine());
            JSONArray jsonArray = JSONArray.parseArray(obj.getString("data"));
            JSONObject obj1 = jsonArray.getJSONObject(0);
            pic = JSONObject.parseObject(String.valueOf(obj1),pic.class);
            System.out.println("���5");

//            System.out.println("��ʼת��base64");
//            result = URLbase64.image2Base64(new URL(pic.getUrl()));
//            System.out.println("base64ת�����");
//            pic.base64 = result;

//            for (Map.Entry<String, Object> entry : json.entrySet()) {
//                System.out.println(entry.getKey() + ":" + entry.getValue());
//            }
            is.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (out != null){
                    out.close();
                }
                if (br != null){
                    br.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return pic;
    }

    public static void main(String[] args) {
            //pic pic = doGet("https://api.lolicon.app/setu/?apikey=232368045f2bc262c4e5e4&size1200=true&num=10", "");
            //pic[] pic = test(2);
        //System.out.println(pic);
            //System.out.println(pic);
//        //JSONObject jsonObject = JSON.parseObject("{\"p\":0,\"uid\":19880053,\"author\":\"�줤�ʤ�\",\"r18\":false,\"width\":1068,\"pid\":77607987,\"title\":\"��Ů\",\"url\":\"https://i.pixiv.cat/img-original/img/2019/11/02/11/18/06/77607987_p0.png\",\"height\":1500,\"tags\":[\"���ꥸ�ʥ�\",\"ԭ��\",\"Ů����\",\"Ů����\",\"������\",\"�𷢱���\",\"���ؤ�\",\"����\",\"�\����\",\"��ɫ����\",\"���P\",\"����\",\"�Ϥ���\",\"barely clothed\",\"���äѤ�\",\"ŷ��\",\"���Ѥ�\",\"�ѵ�һ��\"]}");
//        String string = "{\"msg\":\"\",\"code\":0,\"data\":[{\"p\":0,\"uid\":3115085,\"author\":\"���Τʤ�?\",\"r18\":false,\"width\":1118,\"pid\":68146809,\"title\":\"�ө`���ȥЩ`��������\",\"url\":\"https://i.pixiv.cat/img-original/img/2018/04/09/00/31/37/68146809_p0.jpg\",\"height\":1500,\"tags\":[\"�ץꥺ�ޡ���\",\"ħ����Ů��������\",\"����䥹�ե��`��?�ե���?������ĥ٥��\",\"������˿�ƶ�?��?�����ȱ���\",\"Fate/GrandOrder\",\"FGO\",\"��ܥ�`��\",\"�_��\",\"����\",\"�Ȼ�ΤդȤ��\",\"�Ȼ�Ĵ���\"]}],\"quota\":277,\"count\":1,\"quota_min_ttl\":76540}";
//        JSONObject obj = JSON.parseObject(string);
    }


}
