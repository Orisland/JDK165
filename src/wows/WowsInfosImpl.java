package wows;

import Bot_test.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class WowsInfosImpl implements WowsInfos {
    @Override
    public String getShipInfo(Long shipid) {
        // get ship uid from WG
        String getUrl = WowsApiConfig.GET_BOAT_BASIC_INFO
                + "?application_id=" + WowsApiConfig.APPID
                + "&ship_id=" + shipid;

        JSONObject resp = JSONObject.parseObject(HttpUtil.Get(getUrl,""));

        String shipname = resp.getJSONObject("data").getJSONObject(String.valueOf(shipid)).getString("name");
        String status = resp.getString("status");

        if(!"ok".equals(status)){
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

        String uid = tst.getUserId(msgSplit[1]);
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
    public String getUserId(String username) {
        System.out.println("��ʼ����username api");
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
    public player getUserBasicInfo(String uid) {
        System.out.println("��ʼ����userid api");
        // get user info from WG
        String getUrl = WowsApiConfig.GET_PLAYER_BASIC_INFO
                + "?application_id=" + WowsApiConfig.APPID
                + "&account_id=" + uid;
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
        double hit_rate = (1.0 * pvpStatus.getJSONObject("main_battery").getLongValue("hits")) / pvpStatus.getJSONObject("main_battery").getLongValue("shots");
        double torpedoes_rate = (1.0 * pvpStatus.getJSONObject("torpedoes").getLongValue("hits")) / pvpStatus.getJSONObject("torpedoes").getLongValue("shots");
        double sen_hit_rate = (1.0 * pvpStatus.getJSONObject("second_battery").getLongValue("hits")) / pvpStatus.getJSONObject("second_battery").getLongValue("shots");
        long ave_xp = pvpStatus.getLongValue("xp") / battles;
        double survived_rate = survivedBattles * 1.0 / battles;
        Long wins = pvpStatus.getLongValue("wins");
        Long losses = pvpStatus.getLongValue("losses");
        Long survived_wins = pvpStatus.getLongValue("survived_wins");
        double win_rate = (wins *1.0) / battles;
        Long damage = pvpStatus.getLongValue("damage_dealt");
        Long ave_damage = damage / battles;
        Long frags = pvpStatus.getLongValue("frags");
        Long die = battles - survivedBattles;

        Long torpedoes_max_frags_battle = pvpStatus.getJSONObject("torpedoes").getLongValue("max_frags_battle");    //�������׻�ɱ
        Long torpedoes_max_frags_ship_id = pvpStatus.getJSONObject("torpedoes").getLongValue("max_frags_ship_id");    //�������׻�ɱid
        String name_torpedoes_max_frags_ship_id = getShipInfo(torpedoes_max_frags_ship_id);                             //������

        Long cv_max_frags_battle = pvpStatus.getJSONObject("aircraft").getLongValue("max_frags_battle");    //������ɱ���
        Long cv_max_frags_ship_id = pvpStatus.getJSONObject("aircraft").getLongValue("max_frags_ship_id");  //������߻�ɱ��ֻ
        String name_cv_max_frags_ship_id = "��Ī��cv~";
        if (cv_max_frags_ship_id != 0){
            name_cv_max_frags_ship_id = getShipInfo(cv_max_frags_ship_id);                                   //������
        }

        Long max_single_kill = pvpStatus.getLongValue("max_frags_battle");                                  //���ɱ
        Long max_frags_boat_id = pvpStatus.getJSONObject("main_battery").getLongValue("max_frags_ship_id"); //����ɱid
        String name_max_frags_boat_id = getShipInfo(max_frags_boat_id);                                         //����ɱ����
        //System.out.println(name_max_frags_boat_id);

        Long max_damage_dealt = pvpStatus.getLongValue("max_damage_dealt");                                 //����˺�
        Long max_damage_boat_id = pvpStatus.getLongValue("max_damage_dealt_ship_id");                       //����˺�id
        String name_max_damage_boat_id = getShipInfo(max_damage_boat_id);                                       //����˺�������
        //System.out.println(name_max_damage_boat_id);

        Long max_xp_ship_id = pvpStatus.getLongValue("max_xp_ship_id");
        String name_max_xp_ship_id = getShipInfo(max_xp_ship_id);

        double KD = frags* 1.0 / die;
        double win_servived_rate = survived_wins * 1.0 / wins;



        DecimalFormat df= new DecimalFormat("######0.000");
        hit_rate = Double.parseDouble(df.format(hit_rate)) * 100;
        sen_hit_rate = Double.parseDouble(df.format(sen_hit_rate)) * 100;
        torpedoes_rate = Double.parseDouble(df.format(torpedoes_rate)) * 100;
        survived_rate = Double.parseDouble(df.format(survived_rate)) * 100;
        KD = Double.parseDouble(df.format(KD));
        win_rate = Double.parseDouble(df.format(win_rate)) * 100;
        win_servived_rate = Double.parseDouble(df.format(win_servived_rate)) * 100;
        player player = new player();
        player.max_xp_ship_id = max_xp_ship_id;
        player.name_max_xp_ship_id = name_max_xp_ship_id;
        player.user_id = Long.parseLong(uid);
        player.nickname =nickname;
        player.ave_damage =ave_damage;
        player.ave_xp =ave_xp;
        player.battles =battles;
        player.cv_max_frags_battle = cv_max_frags_battle;
        player.cv_max_frags_ship_id = cv_max_frags_ship_id;
        player.damage = damage;
        player.die = die;
        player.frags = frags;
        player.hit_rate = hit_rate;
        player.KD = KD;
        player.losses = losses;
        player.max_damage_boat_id = max_damage_boat_id;
        player.max_damage_dealt = max_damage_dealt;
        player.max_frags_boat_id = max_frags_boat_id;
        player.max_single_kill = max_single_kill;
        player.max_xp =max_xp;
        player.name_cv_max_frags_ship_id = name_cv_max_frags_ship_id;
        player.name_max_damage_boat_id = name_max_damage_boat_id;
        player.name_max_frags_boat_id = name_max_frags_boat_id;
        player.name_torpedoes_max_frags_ship_id = name_torpedoes_max_frags_ship_id;
        player.sen_hit_rate = sen_hit_rate;
        player.survived_rate = survived_rate;
        player.survived_wins = survived_wins;
        player.survivedBattles = survivedBattles;
        player.torpedoes_max_frags_battle = torpedoes_max_frags_battle;
        player.torpedoes_max_frags_ship_id = torpedoes_max_frags_ship_id;
        player.torpedoes_rate = torpedoes_rate;
        player.win_rate =win_rate;
        player.win_servived_rate = win_servived_rate;
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
