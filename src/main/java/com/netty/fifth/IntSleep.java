package com.netty.fifth;

import com.google.gson.JsonObject;

public class IntSleep {
	
	String a = "{	\"orgAccountId\": -159024912768482966,	\"id\": -971044334476964642,	\"name\": \"石玮光\",	\"code\": \"NP00342\",	\"createTime\": 1489393942300,	\"updateTime\": 1530690079843,	\"sortId\": 296,	\"isDeleted\": false,	\"enabled\": false,	\"externalType\": 0,	\"status\": 1,	\"description\": \"\",	\"orgLevelId\": 213055789478265400,	\"orgPostId\": 6192935699446646395,	\"orgDepartmentId\": 3997718489164828592,	\"type\": 1,	\"isInternal\": true,	\"isLoginable\": true,	\"isVirtual\": false,	\"isAssigned\": true,	\"isAdmin\": false,	\"isValid\": false,	\"state\": 2,	\"properties\": {		\"birthday\": 499276800000,		\"politics\": 0,		\"website\": \"\",		\"address\": \"\",		\"imageid\": \"\",		\"gender\": 1,		\"degree\": \"\",		\"postAddress\": \"\",		\"emailaddress\": \"shiweiguang@netposa.com\",		\"reporter\": \"\",		\"blog\": \"\",		\"hiredate\": \"\",		\"extPostLevel\": \"\",		\"weixin\": \"\",		\"weibo\": \"\",		\"telnumber\": \"18666117860\",		\"postalcode\": \"\",		\"eduBack\": 0,		\"officenumber\": \"\",		\"location\": \"\",		\"idnum\": \"\"	},	\"second_post\": [],	\"concurrent_post\": [],	\"customerAddressBooklist\": [],	\"primaryLanguange\": null,	\"pinyin\": \"shiweiguang\",	\"pinyinhead\": \"swg\",	\"address\": \"\",	\"location\": null,	\"valid\": false,	\"gender\": 1,	\"loginName\": null,	\"reporter\": null,	\"officeNum\": \"\",	\"postalcode\": \"\",	\"v5External\": false,	\"vjoinExternal\": false,	\"entityType\": \"Member\",	\"birthday\": 499276800000,	\"hiredate\": null,	\"weibo\": \"\",	\"weixin\": \"\",	\"idNum\": \"\",	\"degree\": \"\",	\"postAddress\": \"\",	\"telNumber\": \"18666117860\",	\"emailAddress\": \"shiweiguang@netposa.com\",	\"blog\": \"\",	\"website\": \"\",	\"orgAccountName\": \"东方网力科技股份有限公司\",	\"orgPostName\": \"科信行业代表\",	\"orgDepartmentName\": \"广东办事处销售组\",	\"orgLevelName\": \"高级\"}";
	public static void main(String[] args) throws InterruptedException {
		
	}
	
	
	static class MyThread implements Runnable {
		public void run() {
			for(;;){
				System.out.println("hello");
				int [] a = new int[1024*1024];
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
					break;
				}
			}
		}
	}

}
