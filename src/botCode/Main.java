package botCode;

import botCode.impl.WowsInfosImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pic.Getpic;
import pic.pic;

import java.util.Base64;
import java.util.concurrent.TimeUnit;


/**
 * ��Ҫ��
 * @author zhaoqk
 *
 * 2020��8��10�� ����5:07:37
 */
public class Main {
	
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
				}else if(msg.equals("�������")){
					Core.pushRedPacket(selfQQ,fromQQ,1,1,"�õĴ��","֧������");//������Ͳ��ɹ����¼�����˺�ȷ���������������
				}else if(msg.equals("ͼ��")){
					byte[] bts = StringUtils.readFile("D:\\1.png");//��ȡ�ļ�
					String base64Str = Base64.getEncoder().encodeToString(bts);//�ֽ�����תBase64
					base64Str = "[pic:"+ base64Str + "]";//��װͼƬ�ĸ�ʽ
					Core.sendPrivateMessagesPicText(selfQQ, fromQQ, base64Str + "111" + base64Str, random, req);
				}else if(msg.equals("�����б�")){
					Core.friendsList(selfQQ);
				}else if(msg.equals("Ⱥ�б�")){
					Core.groupList(selfQQ);
				}else if(msg.equals("�������Ŀ¼")){
					Core.selectPluginPath();
				}else if(msg.indexOf("�����־") == 0){
					String log = msg.substring(msg.indexOf("�����־") + 4);//ȡ���ұ�Ҫ�ߵ�QQ
					Core.outputLog(log, 0, 16777215);
				}else if(msg.equals("ͼ��2")){
//					byte[] bts = StringUtils.readFile("C:\\Users\\Orisland\\Pictures\\QQͼƬ20200713123149.jpg");//��ȡ�ļ�
//					String base64Str = Base64.getEncoder().encodeToString(bts);//�ֽ�����תBase64
//					Core.uploadPic(selfQQ, 1, fromQQ, 0, base64Str);
					String picurl = "https://i.pixiv.cat/img-original/img/2017/06/13/19/15/53/63366945_p0.jpg";
					Core.sendPrivateMessagesPicText(selfQQ, fromQQ,"[netpic:"+picurl+"]",random,req);
				}else if(msg.equals("����")){
					Core.uploadAudioSync(selfQQ, 1, fromQQ, "C:\\Users\\Admin\\Desktop\\13203.mp3");
				}
				else {
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
			long selfQQ = json.getInteger("selfQQ");//���QQ
			long fromGroup = json.getInteger("fromGroup");//Ⱥ��
			long fromQQ = json.getInteger("fromQQ");//�Է�QQ
			String msg = json.getString("msg");//��Ϣ����
			//������д��һЩ����ָ��
			if(msg.indexOf("����Ƭ") == 0){//Ĭ�ϸ��Լ��� ��  ����Ƭ404
				String cardName = msg.substring(msg.indexOf("����Ƭ") + 3);//ȡ���ұߵ���Ƭ
				Core.setGroupCardName(selfQQ, fromGroup, fromQQ, cardName);
			}else if(msg.indexOf("��") == 0){//�ұ���Ҫ����Ҫ�ߵ�QQ �� ��123456
				String otherQQ = msg.substring(msg.indexOf("��") + 1);//ȡ���ұ�Ҫ�ߵ�QQ
				Core.delGroupMember(selfQQ, fromGroup, Integer.valueOf(otherQQ), 0);
			}else if(msg.indexOf("����") == 0){//�ұ���Ҫ����Ҫ���Ե�QQ �� ����123456
				String otherQQ = msg.substring(msg.indexOf("����") + 2);//ȡ���ұ�Ҫ���Ե�QQ
				Core.prohibitSpeak(selfQQ, fromGroup, Integer.valueOf(otherQQ), 60);
			}else if(msg.equals("ͼ��")){
				byte[] bts = StringUtils.readFile("D:\\1.png");//��ȡ�ļ�
				String base64Str = Base64.getEncoder().encodeToString(bts);//�ֽ�����תBase64
				base64Str = "[pic:"+ base64Str + "]";//��װͼƬ�ĸ�ʽ
				Core.sendGroupMessagesPicText(selfQQ, fromGroup, base64Str + "111",0);
			}else if(msg.equals("�������")){
				Core.pushRedPacketGroup(selfQQ,fromGroup,1,1,"�õĴ��","֧������");//������Ͳ��ɹ����¼�����˺�ȷ���������������
			}else if(msg.equals("ȫԱ����")){
				Core.prohibitSpeakAll(selfQQ, fromGroup, 1);
			}else if(msg.equals("ȫԱ���")){
				Core.prohibitSpeakAll(selfQQ, fromGroup, 0);
			}else if(msg.equals("Ⱥ��Ϣ")){
				Core.selectGroupInfo(selfQQ, fromGroup);
			}else if(fromGroup == 0){//������������ָ�� �����Ķ�ԭ������  �����Ⱥ����Ҫ�ĳ���Ĳ���Ⱥ
				Core.sendGroupMessages(selfQQ, fromGroup, msg, 0);
			}else if (msg.equals("test1")){
				Core.sendGroupMessages(selfQQ,fromGroup,"ceshi",0);
			}else if (msg.equals("setu")){
				pic pic = Getpic.doGet("https://api.lolicon.app/setu/?apikey=232368045f2bc262c4e5e4&size1200=true", "");
				String url = "[netpic:"+ pic.getUrl() + "]";
				String picPid = "ͼƬid��"+pic.getPid();
				String picurl = "ͼƬurl��"+pic.getUrl();
				String picwriter = "���ߣ�"+pic.getAuthor();
				String pack = url+"\n"+picPid+"\n"+picwriter+"\n"+picurl;
				Core.sendGroupMessagesPicText(selfQQ, fromGroup, pack,0);
			}else if (msg.indexOf("ɬͼ��") == 0){
				int num = Integer.parseInt(msg.substring(msg.indexOf("ɬͼ��") + 3));
				if (num >10){
					Core.sendGroupMessages(selfQQ,fromGroup,"̰�ģ�ֻ����һ��ͼ~",0);
					num=1;
				}
				Core.sendGroupMessages(selfQQ,fromGroup,"����!"+num+"��setu��Ҫ��ʱ��~",0);
				Core.sendGroupMessages(selfQQ,fromGroup,"������ɽ��ʤ���Ƚ��ķ�sp��⣬��ͼ����߿ɴ�100%~",0);
				for (int i=0; i<num; i++){
					pic pic = Getpic.doGet("https://api.lolicon.app/setu/?apikey=232368045f2bc262c4e5e4&size1200=true", "");
					String url = "[netpic:"+ pic.getUrl() + "]";
					String picPid = "ͼƬid��"+pic.getPid();
					String picurl = "ͼƬurl��"+pic.getUrl();
					String picwriter = "���ߣ�"+pic.getAuthor();
					String pack = url+"\n"+picPid+"\n"+picwriter+"\n"+picurl;
					int y = i+1;
					pack = "��"+y+"��setu����~"+"\n"+pack;
					Core.sendGroupMessagesPicText(selfQQ, fromGroup, pack,0);
					TimeUnit.SECONDS.sleep(5);
				}
//				pic[] pics = Getpic.doGet(num);
//				for (int i=0; i<pics.length; i++){
//					pics[i].base64 = "[netpic:"+ pics[i].getUrl() + "]";
//					String picPid = "ͼƬid��"+pics[i].getPid();
//					String picurl = "ͼƬurl��"+pics[i].getUrl();
//					String picwriter = "���ߣ�"+pics[i].getAuthor();
//					String pack = pics[i].base64+"\n"+picPid+"\n"+picwriter+"\n"+picurl;
//					int y = i+1;
//					pack = "��"+y+"��setu����~"+"\n"+pack;
//					Core.sendGroupMessagesPicText(selfQQ, fromGroup, pack,0);
//					System.out.println("��"+i+"��setu�������,��û����������.");
//				}
			}else if (msg.equals("qwe")){
				String picurl = "https://i.pixiv.cat/img-original/img/2020/06/02/00/00/10/82028494_p0.jpg";
				Core.sendGroupMessagesPicText(selfQQ, fromGroup,"[netpic:"+picurl+"]",0);
			}else if (fromGroup == 7){
				Core.sendGroupMessages(selfQQ,fromGroup,msg,0);
			}else if (msg.startsWith("��ˮ��")){
				WowsInfosImpl wowsInfos = new WowsInfosImpl();
				String[] msgSplit = msg.split( " ");
				String uid = wowsInfos.getUserId(msgSplit[1]);
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
