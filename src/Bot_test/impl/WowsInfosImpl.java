package Bot_test.impl;

import Bot_test.HttpUtil;
import Bot_test.WowsInfos;
import Bot_test.pojo.WowsApiConfig;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class WowsInfosImpl implements WowsInfos {

    /**
     *
     * @param args
     * ���Է���
     * �����Ǵ�������ϵ㿴���زξ�ok��
     *
     */
    public static void main(String[] args){

        WowsInfosImpl tst = new WowsInfosImpl();

            String msg = "��ˮ�� Orisland_EX";
        String[] msgSplit = msg.split( " ");

        String uid = tst.getUserId(msgSplit[1]);
        String res = tst.getUserBasicInfo(uid);

        System.out.println(res);
    }

    /**
     * GET
     * ͨ���û�����ȡ�û�id
     * wows�����û���ص�api����ͨ��uid���õģ�������һ���ǻ�����һ��
     * template url : https://api.worldofwarships.eu/wows/account/list/?application_id=21a433ec77510c77b44a3047937b4bb1&search=lsahi
     * {
     *      "status": "ok",
     *      "meta": {
     *          "count": 1
     *      },
     *      "data": [
     *      {
     *             "nickname": "lsahi",
     *             "account_id": 567190085
     *      }
     *      ]
     * }
     */
    @Override
    public String getUserId(String username) {

        // get user uid from WG
        String getUrl = WowsApiConfig.GET_PLAYER_ID
                + "?application_id=" + WowsApiConfig.APPID
                + "&search=" + username;
        JSONObject resp = JSONObject.parseObject(HttpUtil.Get(getUrl,""));

        List<Object> data = resp.getJSONArray("data");
        String status = resp.getString("status");

        if(!"ok".equals(status)){
            return "EOF";
        }

        JSONObject curUser = JSONObject.parseObject(data.get(0).toString());

        String name = curUser.getString("nickname");
        String id = curUser.getString("account_id");

        if(!name.equals(username)){
            return "EOF";
        }

        return id;
    }

    /**
     * GET
     * ͨ��uid��ȡ�û�������Ϣ(ˮ���ܱ�)
     * template url : https://api.worldofwarships.eu/wows/account/info/?application_id=21a433ec77510c77b44a3047937b4bb1&account_id=567190085
     *
     * ����̫�鷳�ˣ�ȥWG��api�ĵ�����
     * https://developers.wargaming.net/reference/eu/wows/account/info/
     */
    @Override
    public String getUserBasicInfo(String uid) {

        // get user info from WG
        String getUrl = WowsApiConfig.GET_PLAYER_BASIC_INFO
                + "?application_id=" + WowsApiConfig.APPID
                + "&account_id=" + uid;
        JSONObject resp = JSONObject.parseObject(HttpUtil.Get(getUrl,""));

        JSONObject userinfo = resp.getJSONObject("data").getJSONObject(uid);

        if (userinfo == null){
            return "EOF";
        }

        String nickname = userinfo.getString("nickname");
        JSONObject pvpStatus = userinfo.getJSONObject("statistics").getJSONObject("pvp");

        Long battles = pvpStatus.getLongValue("battles");
        Long survivedBattles = pvpStatus.getLongValue("survived_battles");
        Long max_xp = pvpStatus.getLongValue("max_xp");

        String res =
                "��ѯ�����Ϣ" + nickname + "\n"
                + "�ܳ���" + battles +"\n"
                + "����" + survivedBattles + "\n"
                + "�����������"+max_xp + "\n";

        return res;
    }

}
