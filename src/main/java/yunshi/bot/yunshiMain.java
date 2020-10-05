package yunshi.bot;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import yunshi.yunshi;

/**
 * ��Ҫ��
 * @author zhaoqk
 *
 * 2020��8��10�� ����5:07:37
 */
public class yunshiMain {
	public static long selfQQ=0;
	public static long fromGroup=0;
	public static long fromQQ=0;
	public static long[] qqnum = new long[100];
	//
	/**/
	/**
	 * ������
	 * 1.�������Ҫ���¼�����û����ô�죿
	 * �������ǲɼ���һЩ���õ��������Ҫ�����¼� ���� ���Ѹ���ǩ��
	 * 	     �����������Ŀ �û����˺ŵĺ��Ѹ�һ��ǩ�� �ͻ��յ���ǩ������Ϣ ����msgType�ǶԶ���Ȼ������Ӹ��жϾ����ˡ�
	 * 2.�������Ҫ��API����Core��û����ô�죿
	 * �������߱������Կ�������������TcpAPI��Դ��Ȼ���ո�ʽ����һ��APIȻ����Core��Ҳ����һ����Ӧ�ľͿ�����
	 *    ����㲻�߱������Կ����������Ⱥ742830386���ҷ�ӳ����Ҫ��API �һ��ڸ���ʱһ�����
	 * 3.��Ĳ�����շѻ���ԴΪ��ʲô��
	 * �����������������˵�ܼ򵥣�����������һ���Լ�����Ȼ��Խ��Խ�������ǾͿ�Դ���һ����
	 *    ��Դ����Ϊ�ҵ�ά��ʱ�䲻������Ҫ�Ŀ������޷��������ƣ������ҿ���ͷ ���� ���鷢��^_^
	 */

	static ChatClient clientTest;

	public static void main(String[] args) {
		clientTest = new ChatClient("127.0.0.1", 8404);
		clientTest.start();
	}

	/**
	 * �յ�������Ϣ���¼�
	 * @param data
	 */
	public static void receivePrivateMessages(String data){

		System.out.println("[�յ�������Ϣ]" + data);
		try{
			JSONObject json = JSONObject.parseObject(data);
			selfQQ = json.getInteger("selfQQ");//���QQ
			fromQQ = json.getInteger("fromQQ");//�Է�QQ
			long random = json.getInteger("random");//������Ϣ��
			long req = json.getInteger("req");//������Ϣ��
			String msg = json.getString("msg");//��Ϣ����
			long msgType = json.getInteger("msgType");//��Ϣ����
			//long msgType2 = json.getInteger("msgType2");//��Ϣ����
			if(msgType == 141){//���ں���Ϣ

			}else if(msgType == 87){//����������Ⱥ

			}else if(msgType == 528){//�����Ϊ����

			}else if(msgType == 529){//�����ļ���Ϣ

			}else if(msgType == 208){//����������Ϣ
				req = 0L;
				random = 0L;
				Core.sendPrivateMessages(selfQQ, fromQQ, msg, random, req);
			}else if(msgType == 87){//����������Ⱥ

			}else if(msgType == 166){//��ͨ������Ϣ
				if (msg.equals("test")) {
					Core.sendPrivateMessages(selfQQ,fromQQ,"������Ч",random,req);
				}
				else {
					msg += "~";
					Core.sendPrivateMessages(selfQQ, fromQQ, msg, random, req);
				}
			}
		}catch (Exception e) {
			System.out.println("[���������쳣]");
		}
		
		//������ͳɹ�  "msgType":141,"msgType2":134,"msgTempType":129
		//87	����������Ⱥ
		//528	�����Ϊ����
		//166	�յ����Ѻ��
	}
	/**
	 * �յ�Ⱥ����Ϣ
	 * @param data
	 */
	public static void receiveGroupMessages(String data){
		System.out.println("[�յ�Ⱥ����Ϣ]" + data);

		String msg;
		try{
			JSONObject json = JSONObject.parseObject(data);
			selfQQ = json.getLong("selfQQ");//���QQ
			fromGroup = json.getLong("fromGroup");//Ⱥ��
			fromQQ = json.getLong("fromQQ");//�Է�QQ
			msg = json.getString("msg");//��Ϣ����

			//������д��һЩ����ָ��
			if (msg.equals("��������")){
				String chouqian = yunshi.rand(fromQQ,qqnum);
				if (chouqian.equals("EOF")){
					Core.sendGroupMessages(selfQQ,fromGroup,"�������Ѿ������Ŷ!������������",0);
					return;
				}
				chouqian = "[@"+fromQQ+"]\r"+chouqian;
				Core.sendGroupMessages(selfQQ,fromGroup,chouqian,0);
			}else if (msg.equals("��������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"����������Ŷ~",0);
			}else if (msg.equals("��������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"[pic,hash=2F85896EF703746299458EB0D1ECD5E6]",0);
				Core.sendGroupMessages(selfQQ,fromGroup,"??�������.",0);
			}else if (msg.equals("��������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"[pic,hash=42A1117060091841F5913D671D3FBEB7]",0);
				Core.sendGroupMessages(selfQQ,fromGroup,"�鿨���ף���ɫȫ��.",0);
			}else if (msg.equals("ȥ������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"[pic,hash=42A1117060091841F5913D671D3FBEB7]",0);
				Core.sendGroupMessages(selfQQ,fromGroup,"��.",0);
			}else if (msg.equals("��������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"��û��Ȩ�޲�ѯ�����ݿ�.",0);
			}else if (msg.equals("���ƽ�������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"����ˢ�½���������",0);
				Date startDate = new Date();
				Date endDate = new Date();
				endDate.setTime(startDate.getTime() + 500000000 + 500000000 + 500000000 + 500000000 + 500000000 + 500000000 + 500000000 + 500000000);
				SchedulerFactory schedulerFactory = new StdSchedulerFactory();
				Scheduler scheduler = schedulerFactory.getScheduler();
				JobDetail jobDetail = JobBuilder.newJob(yunshi.class).usingJobData("jobDetail1", "���Job�������Ե�")
						.withIdentity("job1", "groups1").build();

				CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
						.usingJobData("trigger1", "����jobDetail1��trigger")
						.startNow()//������Ч
						.endAt(endDate)
						.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ? "))
						.build();

				scheduler.scheduleJob(jobDetail, cronTrigger);
				System.out.println("==========start=========");
				scheduler.start();
			}

		}catch (Exception e){
			Core.sendGroupMessages(selfQQ,fromGroup,"��ʱ�����Ժ����ԡ�",0);
			System.out.println("[Ⱥ�������쳣]");
			e.printStackTrace();
		}
		//134	�ϴ�Ⱥ�ļ�

	}

	/**
	 * �յ��¼���Ϣ
	 * @param data
	 */
	public static void receiveEventMessages(String data){
		System.out.println("[�յ��¼���Ϣ]" + data);
		try{
			JSONObject json = JSONObject.parseObject(data);
			long selfQQ = json.getInteger("selfQQ");//���QQ
			long fromGroup = json.getInteger("fromGroup");//Ⱥ��
			int msgType = json.getInteger("msgType");//����
			long triggerQQ = json.getInteger("triggerQQ");//�Է�QQ
			//String triggerQQName = json.getString("triggerQQName");//�Է��ǳ�
			long seq = json.getLongValue("seq");//������

			//32��ʾQQ����
			//17��ʾ���Ѹ����ǳ�
			//25��ʾ���������Ⱥ��
			if(msgType == 3){//Ⱥ��֤�¼� ������Ⱥ
				Core.handleGroupEvent(selfQQ, fromGroup, triggerQQ, seq, 11, 3);
			}else if(msgType == 20){//�����Ϊ����
				Core.handlePrivateEvent(selfQQ, triggerQQ, seq, 1);
			}else if(msgType == 19){//���º���
				Core.sendPrivateMessages(selfQQ, triggerQQ, "���", 0, 0);
			}else if(msgType == 23){
				Core.callpPraise(selfQQ,triggerQQ,10);
			}
		}catch (Exception e) {
			System.out.println("[�¼������쳣]");
		}

	}

	/**
	 * �յ���ѯ����
	 * @param data
	 */
	public static void selectResult(String data){
		System.out.println("[�յ���ѯ����]" + data);
		JSONObject json = JSONObject.parseObject(data);
		//long selfQQ = json.getInteger("selfQQ");//���QQ
		int msgType = json.getInteger("msgType");//����
		if(msgType == 107){
			JSONArray list = json.getJSONArray("list");
			System.out.println("[�����б���]" + list.size());
		}else if(msgType == 109){
			JSONObject userInfo = json.getJSONObject("info");
			System.out.println("[������Ϣ����]" + JSONObject.toJSONString(userInfo));
		}else if(msgType == 212){
			JSONArray list = json.getJSONArray("list");
			System.out.println("[Ⱥ���б���]" + list.size());
		}else if(msgType == 213){
			JSONObject groupInfo = json.getJSONObject("info");
			System.out.println("[Ⱥ����Ϣ����]" + JSONObject.toJSONString(groupInfo));
		}else if(msgType == 302){
			String path = json.getString("path");
			System.out.println("[���·������]" + path);
		}else if(msgType == 303){
			String picCode = json.getString("picCode");
			System.out.println("[�ϴ�ͼƬ����]" + picCode);
		}else if(msgType == 304){
			String audioCode = json.getString("audioCode");
			System.out.println("[�ϴ���������]" + audioCode);
			//�����첽
			long t = json.getLongValue("t");
			JSONObject jsonRes = StringUtils.queue.get(t);
			if(jsonRes.getBooleanValue("sync")){//ͬ������
				long selfQQ = jsonRes.getLongValue("selfQQ");
				long fromQQ = jsonRes.getLongValue("fromQQ");
				if(jsonRes.getIntValue("msgType") == 1){//����
					audioCode = audioCode.replace("voice_codec=0", "voice_codec=1");
					Core.sendPrivateMessages(selfQQ, fromQQ, audioCode, 0, 0);
				}else{//Ⱥ��

				}
			}


		}
	}
}
