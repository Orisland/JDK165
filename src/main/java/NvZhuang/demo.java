package NvZhuang;

import NvZhuang.bot.Core;
import NvZhuang.bot.NvMain;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class demo implements Job {
    static int flag=1;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(flag);
        Core.sendGroupMessages(NvMain.selfQQ,1007248323,"[@1378580689]����bot�ĵ�"+flag+"�δߴ�:\r���콹��Ůװ����",0);
        //[@1378580689]����bot�ĵ�"+flag+"�δߴ�\r���콹��Ůװ����
        flag++;
    }
}
