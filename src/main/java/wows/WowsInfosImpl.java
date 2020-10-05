package wows;

import Bot_test.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class WowsInfosImpl implements WowsInfos {
    @Override
    public String getShipInfo(Long shipid, int flag) {

        // get ship uid from WG
        String getUrl;
        if (flag == 0){
            getUrl = WowsApiConfig.GET_BOAT_BASIC_INFO
                    + "?application_id=" + WowsApiConfig.APPID
                    + "&ship_id=" + shipid;
        }
        else {
            getUrl = WowsApiConfig.GET_BOAT_BASIC_INFO_ASIA
                    + "?application_id=" + WowsApiConfig.APPID
                    + "&ship_id=" + shipid;
        }
        JSONObject resp = JSONObject.parseObject(HttpUtil.Get(getUrl,""));
        String shipname = "ĳ���";
        if (!(resp.getJSONObject("data").toJSONString().equals("{}"))){
             shipname = resp.getJSONObject("data").getJSONObject(String.valueOf(shipid)).getString("name");
        }


        String status = resp.getString("status");
        if(!"ok".equals(status)){
            System.out.println(status);
            System.out.println("wait what???");
            return "EOF";
        }

        return shipname;
    }

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

        //String uid = tst.getUserId(msgSplit[1]);
        //String res = tst.getUserBasicInfo(uid);

        //System.out.println(res);
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
    public String getUserId(String username, int flag) {
        System.out.println("��ʼ����username api");
        String getUrl;
        // get user uid from WG
        if (flag == 0){
            getUrl = WowsApiConfig.GET_PLAYER_ID
                    + "?application_id=" + WowsApiConfig.APPID
                    + "&search=" + username;
        }
        else {
            getUrl = WowsApiConfig.GRT_PLAYER_ID_ASIA
                    + "?application_id=" + WowsApiConfig.APPID
                    + "&search=" + username;

        }
        JSONObject resp = JSONObject.parseObject(HttpUtil.Get(getUrl,""));

        List<Object> data = resp.getJSONArray("data");
        String status = resp.getString("status");


        if(!"ok".equals(status)){
            return "EOF";
        }

        JSONObject curUser = JSONObject.parseObject(data.get(0).toString());

        String name = curUser.getString("nickname");
        String id = curUser.getString("account_id");
        if (id.equals("") || id == null){
            return "EOF";
        }

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
    public player getUserBasicInfo(String uid, int flag) {
        System.out.println("��ʼ����userid api");
        String getUrl;
        // get user info from WG
        if (flag==0){
            getUrl = WowsApiConfig.GET_PLAYER_BASIC_INFO
                    + "?application_id=" + WowsApiConfig.APPID
                    + "&account_id=" + uid;
        }else {
            getUrl = WowsApiConfig.GET_PLAYER_BASIC_INFO_ASIA
                    + "?application_id=" + WowsApiConfig.APPID
                    + "&account_id=" + uid;
        }
        JSONObject resp = JSONObject.parseObject(HttpUtil.Get(getUrl,""));

        JSONObject userinfo = resp.getJSONObject("data").getJSONObject(uid);
        if (userinfo == null){
            return new player();
        }

        String nickname = userinfo.getString("nickname");
        System.out.println(nickname);
        JSONObject pvpStatus = userinfo.getJSONObject("statistics").getJSONObject("pvp");

        Long battles = pvpStatus.getLongValue("battles");
        Long survivedBattles = pvpStatus.getLongValue("survived_battles");
        Long max_xp = pvpStatus.getLongValue("max_xp");
        double hit_rate1 = (1.0 * pvpStatus.getJSONObject("main_battery").getLongValue("hits")) / pvpStatus.getJSONObject("main_battery").getLongValue("shots");
        BigDecimal hit_rate = new BigDecimal(Double.toString(hit_rate1));
        double torpedoes_rate1 = (1.0 * pvpStatus.getJSONObject("torpedoes").getLongValue("hits")) / pvpStatus.getJSONObject("torpedoes").getLongValue("shots");
        BigDecimal torpedoes_rate = new BigDecimal(Double.toString(torpedoes_rate1));
        double sen_hit_rate1 = (1.0 * pvpStatus.getJSONObject("second_battery").getLongValue("hits")) / pvpStatus.getJSONObject("second_battery").getLongValue("shots");
        BigDecimal sen_hit_rate = new BigDecimal(Double.toString(sen_hit_rate1));
        long ave_xp = pvpStatus.getLongValue("xp") / battles;
        double survived_rate1 = survivedBattles * 1.0 / battles;
        BigDecimal survived_rate = new BigDecimal(Double.toString(survived_rate1));
        Long wins = pvpStatus.getLongValue("wins");
        Long losses = pvpStatus.getLongValue("losses");
        Long survived_wins = pvpStatus.getLongValue("survived_wins");
        double win_rate1 = (wins *1.0) / battles;
        BigDecimal win_rate = new BigDecimal(Double.toString(win_rate1));
        Long damage = pvpStatus.getLongValue("damage_dealt");
        Long ave_damage = damage / battles;
        Long frags = pvpStatus.getLongValue("frags");
        Long die = battles - survivedBattles;

        Long torpedoes_max_frags_battle = pvpStatus.getJSONObject("torpedoes").getLongValue("max_frags_battle");    //�������׻�ɱ
        Long torpedoes_max_frags_ship_id = pvpStatus.getJSONObject("torpedoes").getLongValue("max_frags_ship_id");    //�������׻�ɱid
        String name_torpedoes_max_frags_ship_id = getShipInfo(torpedoes_max_frags_ship_id,flag);                             //������

//        Long cv_max_frags_battle = pvpStatus.getJSONObject("aircraft").getLongValue("max_frags_battle");    //������ɱ���
//        Long cv_max_frags_ship_id = pvpStatus.getJSONObject("aircraft").getLongValue("max_frags_ship_id");  //������߻�ɱ��ֻ
//        String name_cv_max_frags_ship_id = "��Ī��cv~";
//        if (cv_max_frags_ship_id != 0){
//            name_cv_max_frags_ship_id = getShipInfo(cv_max_frags_ship_id,flag);                                   //������
//        }
        Long max_single_kill = pvpStatus.getLongValue("max_frags_battle");                                  //���ɱ
        Long max_frags_boat_id = pvpStatus.getJSONObject("main_battery").getLongValue("max_frags_ship_id"); //����ɱid
        String name_max_frags_boat_id = getShipInfo(max_frags_boat_id,flag);                                         //����ɱ����
        //System.out.println(name_max_frags_boat_id);

        Long max_damage_dealt = pvpStatus.getLongValue("max_damage_dealt");                                 //����˺�
        Long max_damage_boat_id = pvpStatus.getLongValue("max_damage_dealt_ship_id");                       //����˺�id
        String name_max_damage_boat_id = getShipInfo(max_damage_boat_id,flag);                                       //����˺�������
        //System.out.println(name_max_damage_boat_id);

        Long max_xp_ship_id = pvpStatus.getLongValue("max_xp_ship_id");
        String name_max_xp_ship_id = getShipInfo(max_xp_ship_id,flag);

        double KD1 = frags* 1.0 / die;
        BigDecimal KD = new BigDecimal(Double.toString(KD1));
        double win_servived_rate1 = survived_wins * 1.0 / wins;
        BigDecimal win_servived_rate = new BigDecimal(Double.toString(win_servived_rate1));


        //��̤������double�Ҿ���nt����
        BigDecimal ten = new BigDecimal(100.0);
        hit_rate = hit_rate.setScale(3, RoundingMode.HALF_UP).multiply(ten);
        hit_rate = hit_rate.setScale(1, RoundingMode.HALF_UP);
        sen_hit_rate = sen_hit_rate.setScale(3,RoundingMode.HALF_UP).multiply(ten);
        sen_hit_rate = sen_hit_rate.setScale(1,RoundingMode.HALF_UP);
        torpedoes_rate = torpedoes_rate.setScale(3,RoundingMode.HALF_UP).multiply(ten);
        torpedoes_rate = torpedoes_rate.setScale(1,RoundingMode.HALF_UP);
        BigDecimal survived_rate3 = survived_rate.setScale(3,RoundingMode.HALF_UP).multiply(ten);
        String survived_rate2 = survived_rate3.setScale(1,RoundingMode.HALF_UP).toString();

        KD = KD.setScale(1,RoundingMode.HALF_UP);
        win_rate = win_rate.setScale(3,RoundingMode.HALF_UP).multiply(ten);
        win_rate = win_rate.setScale(1,RoundingMode.HALF_UP);
        win_servived_rate = win_servived_rate.setScale(3,RoundingMode.HALF_UP).multiply(ten);
        win_servived_rate = win_servived_rate.setScale(1,RoundingMode.HALF_UP);
        player player = new player();
        player.max_xp_ship_id = max_xp_ship_id;
        player.name_max_xp_ship_id = name_max_xp_ship_id;
        player.user_id = Long.parseLong(uid);
        player.nickname =nickname;
        player.ave_damage =ave_damage;
        player.ave_xp =ave_xp;
        player.battles =battles;
//        player.cv_max_frags_battle = cv_max_frags_battle;
//        player.cv_max_frags_ship_id = cv_max_frags_ship_id;
        player.damage = damage;
        player.die = die;
        player.frags = frags;
        player.hit_rate = hit_rate.toString();
        player.KD = KD.toString();
        player.losses = losses;
        player.max_damage_boat_id = max_damage_boat_id;
        player.max_damage_dealt = max_damage_dealt;
        player.max_frags_boat_id = max_frags_boat_id;
        player.max_single_kill = max_single_kill;
        player.max_xp =max_xp;
//        player.name_cv_max_frags_ship_id = name_cv_max_frags_ship_id;
        player.name_max_damage_boat_id = name_max_damage_boat_id;
        player.name_max_frags_boat_id = name_max_frags_boat_id;
        player.name_torpedoes_max_frags_ship_id = name_torpedoes_max_frags_ship_id;
        player.sen_hit_rate = sen_hit_rate.toString();
        player.survived_rate = survived_rate2;
        player.survived_wins = survived_wins;
        player.survivedBattles = survivedBattles;
        player.torpedoes_max_frags_battle = torpedoes_max_frags_battle;
        player.torpedoes_max_frags_ship_id = torpedoes_max_frags_ship_id;
        player.torpedoes_rate = torpedoes_rate.toString();
        player.win_rate = win_rate.toString();
        player.win_servived_rate = win_servived_rate.toString();
        player.wins = wins;


        String res =
                "��ѯ�����Ϣ" + nickname + "\n"
                + "�ܳ���" + battles +"\n"
                + "����" + survivedBattles + "\n"
                + "�����������"+max_xp + "\n"
                + "������"+hit_rate+"%" +"\n"
                + "��������ɱ"+max_single_kill+"\n"
                + "����������"+sen_hit_rate + "%" + "\n"
                + "��������" + ave_xp +"\n"
                + "�����" + survived_rate + "%" + "\n"
                + "KD" + KD +"\n"
                + "ƽ���˺�" + ave_damage + "\n"
                + "ƽ������" + ave_xp + "\n"
                + "ʤ��" + win_rate + "%" + "\n"
                + "ʤ�����"+ survived_wins + "\n"
                + "ʤ�������" + win_servived_rate + "%"
                + "���׾���" + torpedoes_rate + "%";

        return player;
    }

}
