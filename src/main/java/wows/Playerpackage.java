package wows;

//��װ��Ϣ

public class Playerpackage {
    String spackage = "";
    String pic;
    public Playerpackage(String msg, int flag){
        WowsInfosImpl tst = new WowsInfosImpl();
        String[] msgSplit = msg.split( " ");
        String uid;
        player player = null;
        System.out.println("��ˮ���������");
        if (flag == 0){
            uid = tst.getUserId(msgSplit[1],0);
            System.out.println("ŷ��ģʽ");
            player = tst.getUserBasicInfo(uid, 0);
        }
        else {
            uid = tst.getUserId(msgSplit[1],1);
            System.out.println("�Ƿ�ģʽ");
            player = tst.getUserBasicInfo(uid, 1);
        }
        System.out.println("�û����ݻ�ȡ���");
        spackage += "��ѯid��"+player.nickname+"\n";
        spackage += "ս��������"+player.battles + "\n";
        spackage += "ƽ��ʤ�ʣ�"+player.win_rate + "%\n";
        spackage += "ƽ���˺���"+player.ave_damage + "\n";
        spackage += "ƽ�����飺"+player.ave_xp + "\n";
        spackage += "KD��"+player.KD + "\n";
        spackage += "����ʣ�"+player.survived_rate + "%\n";
        spackage += "���ʤ���ʣ�"+player.win_servived_rate + "%\n";
        spackage += "���������ʣ�"+player.hit_rate + "%\n";
        spackage += "���������ʣ�"+player.sen_hit_rate + "%\n";
        spackage += "���������ʣ�"+player.torpedoes_rate + "%\n";
        spackage += "����˺���"+player.name_max_damage_boat_id+"��"+player.max_damage_dealt+"\n";
        spackage += "��߾��飺"+player.name_max_xp_ship_id+"��"+player.max_xp+"\n";
        spackage += "��߻�ɱ��"+player.name_max_frags_boat_id+"��"+player.max_single_kill+"\n";
        spackage += "������׻�ɱ��"+player.name_torpedoes_max_frags_ship_id+"��"+player.torpedoes_max_frags_battle + "\n";
        pic = pic(String.valueOf(player.user_id));
        pic = "[netpic:"+ pic + "]";
        //spackage += pic + "\n";
        //System.out.println(pic);
        System.out.println("������");
    }

    public String getSpackage() {
        return spackage;
    }

    public void setSpackage(String spackage) {
        this.spackage = spackage;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public static String pic(String uid){
        String url = "https://static.wows-numbers.com/wows/";
        url += uid + ".png";

        return url;
    }
}
