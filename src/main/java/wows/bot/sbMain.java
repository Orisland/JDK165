package wows.bot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pic.Getpic;
import pic.pic;
import wows.Playerpackage;
import wows.Chart;

import java.util.Base64;
import java.util.concurrent.TimeUnit;


/**
 * ��Ҫ��
 * @author zhaoqk
 *
 * 2020��8��10�� ����5:07:37
 */
public class sbMain {
	public static long selfQQ=0L;
	public static long fromGroup=0L;
	public static long fromQQ=0L;

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
				if (msg.indexOf("��ˮ��") == 0) {
					if (msg.split(" ").length != 2) {
						Core.sendPrivateMessages(selfQQ, fromQQ, "�����ʽ������������", random, req);
						Core.sendPrivateMessages(selfQQ, fromGroup, "�����˿ո�Ŷ~", random, req);
						return;
					}
					Core.sendPrivateMessages(selfQQ, fromGroup, "���ڳ��Ի�ȡ����~���Ժ�~", random, req);
					//Playerpackage playerpackage = new Playerpackage(msg);
					String atqq = "[@" + fromQQ + "]��������~" + "\r";
					//Core.sendPrivateMessages(selfQQ, fromGroup, playerpackage.getSpackage(), random, req);
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
		try{
			JSONObject json = JSONObject.parseObject(data);
			selfQQ = json.getLong("selfQQ");//���QQ
			fromQQ = json.getLong("fromQQ");//�Է�QQ
			fromGroup = json.getLong("fromGroup");//Ⱥ��
			String msg = json.getString("msg");//��Ϣ����
			//������д��һЩ����ָ��
			if (msg.indexOf("ŷ��ˮ��") == 0){
				if(msg.split( " ").length != 2){
					Core.sendGroupMessages(selfQQ,fromGroup,"�����ʽ������������",0);
					Core.sendGroupMessages(selfQQ,fromGroup,"�����˿ո�Ŷ~",0);
					return;
				}
				Core.sendGroupMessages(selfQQ,fromGroup,"���ڳ��Ի�ȡ����~���Ժ�~",0);
				Playerpackage playerpackage = new Playerpackage(msg,0);
				String atqq = "[@"+fromQQ+"]��������~" + "\r";
				Core.sendGroupMessages(selfQQ,fromGroup,atqq + playerpackage.getSpackage(),0);
				Core.sendGroupMessages(selfQQ,fromGroup,"����ͼƬ��ʾ��Ҫ�ֶ�����~",0);
				System.out.println(playerpackage.getPic());
				Core.sendGroupMessagesPicText(selfQQ, fromGroup, playerpackage.getPic(), 0);
			}else if(msg.indexOf("�Ƿ�ˮ��") == 0){
				if(msg.split( " ").length != 2){
					Core.sendGroupMessages(selfQQ,fromGroup,"�����ʽ������������",0);
					Core.sendGroupMessages(selfQQ,fromGroup,"�����˿ո�Ŷ~",0);
					return;
				}
				Core.sendGroupMessages(selfQQ,fromGroup,"���ڳ��Ի�ȡ����~���Ժ�~",0);
				Playerpackage playerpackage = new Playerpackage(msg,1);
				String atqq = "[@"+fromQQ+"]��������~" + "\r";
				Core.sendGroupMessages(selfQQ,fromGroup,atqq + playerpackage.getSpackage(),0);
				Core.sendGroupMessages(selfQQ,fromGroup,"����ͼƬ��ʾ��Ҫ�ֶ�����~",0);
				System.out.println(playerpackage.getPic());
				Core.sendGroupMessagesPicText(selfQQ, fromGroup, playerpackage.getPic(), 0);
			}else if (msg.indexOf("ŷ����")==0){
				if (msg.split(" ").length < 2){
					Core.sendGroupMessages(selfQQ,fromGroup,"�����ʽ������������",0);
					Core.sendGroupMessages(selfQQ,fromGroup,"0��ȫͼģʽ  1������ģʽ",0);
					return;
				}
				Core.sendGroupMessages(selfQQ,fromGroup,"���ڳ��Ի�ȡ����~���Ժ�~",0);
				String base64 = Chart.chart(msg,0);
				if (base64.equals("?")){
					return;
				}
				String atqq = "[@"+fromQQ+"]��������~" + "\r";
				Core.sendGroupMessagesPicText(selfQQ,fromGroup,atqq + "[pic:"+base64+"]",0);
			}else if (msg.indexOf("�Ƿ���")==0){
				if (msg.split(" ").length < 2){
					Core.sendGroupMessages(selfQQ,fromGroup,"�����ʽ������������",0);
					Core.sendGroupMessages(selfQQ,fromGroup,"0��ȫͼģʽ  1������ģʽ",0);
					return;
				}
				Core.sendGroupMessages(selfQQ,fromGroup,"���ڳ��Ի�ȡ����~���Ժ�~",0);
				String base64 = Chart.chart(msg,1);
				if (base64.equals("?")){
					return;
				}
				String atqq = "[@"+fromQQ+"]��������~" + "\r";
				Core.sendGroupMessagesPicText(selfQQ,fromGroup,atqq + "[pic:"+base64+"]",0);
			}else if (msg.equals("ˮ����")){
				Core.sendGroupMessages(selfQQ,fromGroup,"[pic,hash=B9433B085F1C592C29137BDE4FEAD556]",0);
			}else if (msg.equals("������")){
				Core.sendGroupMessages(selfQQ,fromGroup,"[pic,hash=9640C8A87A25B56B6F0BC0C354BD3944]",0);
			}else if (msg.equals("wows����")){
				Core.sendGroupMessages(selfQQ,fromGroup,"���롰ˮ���ܡ���ȡˮ���ܰ�����\r���롰�����ܡ���ȡˮ���ܰ�����",0);
			}

		}catch (Exception e) {
			Core.sendGroupMessages(selfQQ,fromGroup,"��ѯ�û������ڻ����糬ʱ�������ԡ�",0);
			Core.sendGroupMessages(selfQQ,fromGroup,"��ѡ����ȷ��������Ϣ�������ʽ�뷢�͡�wows���ܡ�ָ��",0);
			System.out.println("[Ⱥ�������쳣]");
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
			selfQQ = json.getInteger("selfQQ");//���QQ
			fromGroup = json.getInteger("fromGroup");//Ⱥ��
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
