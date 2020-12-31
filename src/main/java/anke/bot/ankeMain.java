package anke.bot;

import anke.anke;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import pic.Getpic;
import pic.pic;

import java.util.Base64;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * ��Ҫ��
 * @author zhaoqk
 *
 * 2020��8��10�� ����5:07:37
 */
public class ankeMain {

	public static boolean flag = false;
	public static int ankeflag = 1;
	public static int onoff = 1;
	public static JSONObject group = new JSONObject();

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
			long selfQQ = json.getInteger("selfQQ");//���QQ
			long fromQQ = json.getInteger("fromQQ");//�Է�QQ
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
				if(msg.equals("����")){
					Core.callpPraise(selfQQ,fromQQ,10);
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
			long selfQQ = json.getInteger("selfQQ");//���QQ
			long fromGroup = json.getInteger("fromGroup");//Ⱥ��
			long fromQQ = json.getInteger("fromQQ");//�Է�QQ
			String fromGroupName = json.getString("fromGroupName");	//qqȺ��
			String msg = json.getString("msg");//��Ϣ����
			//������д��һЩ����ָ��
			if(msg.indexOf("����Ƭ") == 0){//Ĭ�ϸ��Լ��� ��  ����Ƭ404

			}else if ((msg.indexOf(":") == msg.length()-1 || msg.indexOf("��") == msg.length()-1) && flag){
				anke anke = new anke();
				//ankeflag Ĭ��1,Ĭ��ѡ��Χ����
				anke.ankeout(msg,selfQQ,fromGroup,ankeflag);

			}else if (msg.equals("1��������")){
				flag = true;
				String str;
				JSONObject single;
				String ankeflag;
				Boolean onff;

				if (group.get(String.valueOf(fromGroup)) == null){
					single = new JSONObject();
					single.put("ankeflag",2);
					single.put("onff",true);
					single.put("groupname",fromGroupName);
					single.put("������",fromQQ);
//					group.put(String.valueOf(group),single);
				}else {
					single = group.getJSONObject(String.valueOf(fromGroup));
					if (single.getBooleanValue("onff")){
						String aflag = single.getString("ankeflag");
						if (aflag.equals("1")){
							str = "��Χ�������";
						}else
							str = "��Χ�������";
						Core.sendGroupMessages(selfQQ,fromGroup,"Ⱥ��:"+fromGroupName +"\r�ظ�������\r��ǰģʽΪ��"+str,0);
						return;
					}
				}
				ankeflag = single.getString("ankeflag");
				single.put("onff",true);
				group.put(String.valueOf(fromGroup),single);

				if (ankeflag.equals("1")){
					str = "��Χ�������";
				}else
					str = "��Χ�������";
				Core.sendGroupMessages(selfQQ,fromGroup,"Ⱥ��:"+fromGroupName +"\r���������������!\r��ǰģʽΪ��"+str,0);
			}else if (msg.equals("���ƹر�")){
				flag = false;
				Core.sendGroupMessages(selfQQ,fromGroup,"����������ѹر�!",0);
			}else if (msg.equals("�����л�")){
				if (ankeflag == 1){
					ankeflag = 2;
					Core.sendGroupMessages(selfQQ,fromGroup,"����������л�Ϊ\r��Χ�������!",0);
				}else {
					ankeflag = 1;
					Core.sendGroupMessages(selfQQ,fromGroup,"����������л�Ϊ\r��Χ�������!",0);
				}

			}else if (msg.equals("anke")){
				Core.sendGroupMessages(selfQQ,fromGroup,group.toJSONString(),0);
			}

		}catch (Exception e) {
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
//				Core.handleGroupEvent(selfQQ, fromGroup, triggerQQ, seq, 11, 3);
			}else if(msgType == 20){//�����Ϊ����
//				Core.handlePrivateEvent(selfQQ, triggerQQ, seq, 1);
			}else if(msgType == 19){//���º���
//				Core.sendPrivateMessages(selfQQ, triggerQQ, "���", 0, 0);
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
