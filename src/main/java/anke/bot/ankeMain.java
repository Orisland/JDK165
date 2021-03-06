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
 * 主要类
 * @author zhaoqk
 *
 * 2020年8月10日 下午5:07:37
 */
public class ankeMain {

	public static boolean flag = false;
	public static int ankeflag = 1;
	public static int onoff = 1;
	public static JSONObject group = new JSONObject();

	/**
	 * 程序简介
	 * 1.如果我需要的事件下面没有怎么办？
	 * 答：作者是采集了一些常用的如果你需要其他事件 比如 好友更改签名
	 * 	     那你就启动项目 用机器人号的好友改一下签名 就会收到改签名的消息 看看msgType是对多少然后下面加个判断就行了。
	 * 2.如果我需要的API操作Core中没有怎么办？
	 * 答：如果你具备易语言开发能力请下载TcpAPI的源码然后按照格式增加一个API然后在Core中也增加一个对应的就可以了
	 *    如果你不具备易语言开发能力请加群742830386向我反映你需要的API 我会在更新时一块加上
	 * 3.你的插件不收费还开源为了什么？
	 * 答：这个东西对于我来说很简单，本来打算做一个自己玩玩然后越做越完善于是就开源大家一起玩
	 *    开源是因为我的维护时间不多你需要的可能我无法帮你完善，所以我开个头 你们 尽情发挥^_^
	 */

	static ChatClient clientTest;

	public static void main(String[] args) {
		clientTest = new ChatClient("127.0.0.1", 8404);
		clientTest.start();
	}

	/**
	 * 收到好友消息与事件
	 * @param data
	 */
	public static void receivePrivateMessages(String data){
		System.out.println("[收到好友消息]" + data);
		try{
			JSONObject json = JSONObject.parseObject(data);
			long selfQQ = json.getInteger("selfQQ");//框架QQ
			long fromQQ = json.getInteger("fromQQ");//对方QQ
			long random = json.getInteger("random");//撤回消息用
			long req = json.getInteger("req");//撤回消息用
			String msg = json.getString("msg");//消息内容
			long msgType = json.getInteger("msgType");//消息类型
			//long msgType2 = json.getInteger("msgType2");//消息类型
			if(msgType == 141){//公众号消息

			}else if(msgType == 87){//好友邀请入群

			}else if(msgType == 528){//申请加为好友

			}else if(msgType == 529){//好友文件消息

			}else if(msgType == 208){//好友语音消息
				req = 0L;
				random = 0L;
				Core.sendPrivateMessages(selfQQ, fromQQ, msg, random, req);
			}else if(msgType == 87){//好友邀请入群

			}else if(msgType == 166){//普通好友消息
				if(msg.equals("点赞")){
					Core.callpPraise(selfQQ,fromQQ,10);
				}
			}
		}catch (Exception e) {
			System.out.println("[好友数据异常]");
		}

		//红包发送成功  "msgType":141,"msgType2":134,"msgTempType":129
		//87	好友邀请入群
		//528	申请加为好友
		//166	收到好友红包
	}
	/**
	 * 收到群聊消息
	 * @param data
	 */
	public static void receiveGroupMessages(String data){
		System.out.println("[收到群聊消息]" + data);
		try{
			JSONObject json = JSONObject.parseObject(data);
			long selfQQ = json.getInteger("selfQQ");//框架QQ
			long fromGroup = json.getInteger("fromGroup");//群号
			long fromQQ = json.getInteger("fromQQ");//对方QQ
			String fromGroupName = json.getString("fromGroupName");	//qq群名
			String fromqqname = json.getString("fromQQName");
			String msg = json.getString("msg");//消息内容
			//这里我写了一些常用指令
			if(msg.indexOf("改名片") == 0){//默认改自己的 如  改名片404

			}else if ((msg.indexOf(":") == msg.length()-1 || msg.indexOf("：") == msg.length()-1) && (!msg.contains("+") && !msg.contains("-"))){
				if (!msg.contains("d")){
					return;
				}
				if (group.get(String.valueOf(fromGroup)) == null){
					return;
				}else if (!group.getJSONObject(String.valueOf(fromGroup)).getBooleanValue("onff")){
					Core.sendGroupMessages(selfQQ,fromGroup,"安科已关闭!请重新开启安科!",0);
					return;
				}else {
					JSONObject single = group.getJSONObject(String.valueOf(fromGroup));
					anke anke = new anke();
					//ankeflag 默认1,默认选范围数字
					anke.ankeout(msg,selfQQ,fromGroup,Integer.parseInt(single.getString("ankeflag")));
				}
			}else if ((msg.indexOf(":") == msg.length()-1 || msg.indexOf("：") == msg.length()-1) && ((msg.contains("+") || msg.contains("-")))){
				if (!msg.contains("d")){
					return;
				}
				if (group.get(String.valueOf(fromGroup)) == null){
					return;
				}else if (!group.getJSONObject(String.valueOf(fromGroup)).getBooleanValue("onff")){
					Core.sendGroupMessages(selfQQ,fromGroup,"安科已关闭!请重新开启安科!",0);
					return;
				}else {
					anke anke = new anke();
					if (msg.contains("+")){
						anke.ankeout(msg,selfQQ,fromGroup,true);
					}else {
						anke.ankeout(msg,selfQQ,fromGroup,false);
					}

				}
			}else if (msg.equals("安科启动")){
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
					single.put("启动人",fromQQ);
					single.put("启动人昵称",fromqqname);
//					group.put(String.valueOf(group),single);
				}else {
					single = group.getJSONObject(String.valueOf(fromGroup));
					if (single.getBooleanValue("onff")){
						String aflag = single.getString("ankeflag");
						if (aflag.equals("1")){
							str = "范围随机多数";
						}else
							str = "范围随机单数";
						Core.sendGroupMessages(selfQQ,fromGroup,"群名:"+fromGroupName +"\r重复开启！\r当前模式为："+str,0);
						return;
					}
				}
				ankeflag = single.getString("ankeflag");
				single.put("onff",true);
				group.put(String.valueOf(fromGroup),single);

				if (ankeflag.equals("1")){
					str = "范围随机多数";
				}else
					str = "范围随机单数";
				Core.sendGroupMessages(selfQQ,fromGroup,"群名:"+fromGroupName +"\r安科随机数已启动!\r当前模式为："+str,0);
			}else if (msg.equals("安科关闭")){
				flag = false;
				if (group.get(String.valueOf(fromGroup)) == null){
					Core.sendGroupMessages(selfQQ,fromGroup,"该群没开过安科，请先打开。",0);
					return;
				}else {
					JSONObject single = group.getJSONObject(String.valueOf(fromGroup));
					single.put("onff",false);
					group.put(String.valueOf(fromGroup),single);
					Core.sendGroupMessages(selfQQ,fromGroup,"安科随机数已关闭!",0);
				}
			}else if (msg.equals("安科切换")){
				if (group.get(String.valueOf(fromGroup)) == null){
					Core.sendGroupMessages(selfQQ,fromGroup,"该群没开过安科，请先打开。",0);
					return;
				}else {
					JSONObject single = group.getJSONObject(String.valueOf(fromGroup));
					String ankeflag = single.getString("ankeflag");
					System.out.println(single);
					if (ankeflag.equals("1")){
						single.put("ankeflag","2");
						group.put(String.valueOf(fromGroup),single);
						Core.sendGroupMessages(selfQQ,fromGroup,"安科随机数切换为\r范围随机单数!",0);
					}else {
						single.put("ankeflag","1");
						group.put(String.valueOf(fromGroup),single);
						Core.sendGroupMessages(selfQQ,fromGroup,"安科随机数切换为\r范围随机多数!",0);
					}
				}
			}else if (msg.equals("anke") && fromQQ == 756176532){
				Core.sendGroupMessages(selfQQ,fromGroup,group.toJSONString(),0);
			}else if (msg.equals("data del") && fromQQ == 756176532){
				group = new JSONObject();
				Core.sendGroupMessages(selfQQ,fromGroup,"json清空",0);
			}else if (msg.equals("安科功能")){
				String res = "";
				res += "简易安科插件功能:\n";
				res += "发送指令：安科启动(关闭)\n";
				res += "xdy: (x为任意整数字,要求y>=x,否则请求会被终止)\n";
//				res += "举例：10d20:  将会发送10到20之间的随机一个数字\n";
				res += "xdy+z: (加号可改为减号)\n";
//				res += "举例：10d20-100:   将会发送10到20之间并减100的数字";
				System.out.println(res);
				Core.sendGroupMessages(selfQQ,fromGroup,res,0);
			}

		}catch (Exception e) {
			System.out.println("[群聊数据异常]");
		}
		//134	上传群文件

	}

	/**
	 * 收到事件消息
	 * @param data
	 */
	public static void receiveEventMessages(String data){
		System.out.println("[收到事件消息]" + data);
		try{
			JSONObject json = JSONObject.parseObject(data);
			long selfQQ = json.getInteger("selfQQ");//框架QQ
			long fromGroup = json.getInteger("fromGroup");//群号
			int msgType = json.getInteger("msgType");//类型
			long triggerQQ = json.getInteger("triggerQQ");//对方QQ
			//String triggerQQName = json.getString("triggerQQName");//对方昵称
			long seq = json.getLongValue("seq");//操作用

			//32表示QQ上线
			//17表示好友更改昵称
			//25表示邀请加入了群聊
			if(msgType == 3){//群验证事件 申请入群
//				Core.handleGroupEvent(selfQQ, fromGroup, triggerQQ, seq, 11, 3);
			}else if(msgType == 20){//申请加为好友
//				Core.handlePrivateEvent(selfQQ, triggerQQ, seq, 1);
			}else if(msgType == 19){//有新好友
//				Core.sendPrivateMessages(selfQQ, triggerQQ, "哈喽", 0, 0);
			}else if(msgType == 23){
				Core.callpPraise(selfQQ,triggerQQ,10);
			}
		}catch (Exception e) {
			System.out.println("[事件数据异常]");
		}

	}

	/**
	 * 收到查询返回
	 * @param data
	 */
	public static void selectResult(String data){
		System.out.println("[收到查询返回]" + data);
		JSONObject json = JSONObject.parseObject(data);
		//long selfQQ = json.getInteger("selfQQ");//框架QQ
		int msgType = json.getInteger("msgType");//类型
		if(msgType == 107){
			JSONArray list = json.getJSONArray("list");
			System.out.println("[好友列表返回]" + list.size());
		}else if(msgType == 109){
			JSONObject userInfo = json.getJSONObject("info");
			System.out.println("[好友信息返回]" + JSONObject.toJSONString(userInfo));
		}else if(msgType == 212){
			JSONArray list = json.getJSONArray("list");
			System.out.println("[群聊列表返回]" + list.size());
		}else if(msgType == 213){
			JSONObject groupInfo = json.getJSONObject("info");
			System.out.println("[群聊信息返回]" + JSONObject.toJSONString(groupInfo));
		}else if(msgType == 302){
			String path = json.getString("path");
			System.out.println("[插件路径返回]" + path);
		}else if(msgType == 303){
			String picCode = json.getString("picCode");
			System.out.println("[上传图片返回]" + picCode);
		}else if(msgType == 304){
			String audioCode = json.getString("audioCode");
			System.out.println("[上传语音返回]" + audioCode);
			//处理异步
			long t = json.getLongValue("t");
			JSONObject jsonRes = StringUtils.queue.get(t);
			if(jsonRes.getBooleanValue("sync")){//同步发送
				long selfQQ = jsonRes.getLongValue("selfQQ");
				long fromQQ = jsonRes.getLongValue("fromQQ");
				if(jsonRes.getIntValue("msgType") == 1){//好友
					audioCode = audioCode.replace("voice_codec=0", "voice_codec=1");
					Core.sendPrivateMessages(selfQQ, fromQQ, audioCode, 0, 0);
				}else{//群聊

				}
			}


		}
	}
}
