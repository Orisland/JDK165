package anke;

import anke.bot.Core;

import java.util.Random;

public class anke {
    public void ankeout(String msg, long selfQQ, long fromGroup, int flag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int first = Integer.parseInt(msg.split("d")[0]);		//�ü�������
                int second = Integer.parseInt(msg.split("d")[1].substring(0,msg.split("d")[1].length()-1));		//�Ӷ��ٷ�Χ��������
                System.out.println(first);
                System.out.println(second);
                if (first>second){
                    Core.sendGroupMessages(selfQQ,fromGroup,"���ݴ���AdB:\r�������㣺A<B",0);
                    return;
                }
                Random random = new Random();
                String res = "";
                if (flag == 1){
                    for (int i=0; i<first; i++){
                        res += "��"+(i+1)+"������:"+ (random.nextInt(second)+1) +"\r";
                        System.out.println(res);
                    }
                }else {
                    res += "��Χ"+first+"~"+second+"֮����������:"+ (random.nextInt((second-first))+first);
                }
                Core.sendGroupMessages(selfQQ,fromGroup,res,0);
            }
        }).start();
    }
}
