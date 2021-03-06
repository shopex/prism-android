package com.shopex.android.prism.network;

import com.shopex.android.prism.req.GrantTypeReq;
import com.shopex.android.prism.req.OAuthReq;


public class INetworkAPI {
	
	/**
	 * 安全
	 * @author JazzyYang
	 *
	 */
	public interface SECURITY{
		
		void oauth(OAuthReq req,ShopExAsynchResponseHandler responseHandler );
		
		void grant(GrantTypeReq req,ShopExAsynchResponseHandler responseHandler);
		
		
	}
	
	/**
	 * WebSocket
	 */
	public interface WEBSOCKET{
		void connect(String clientId,String clientSecret,PrismMsgHandler handler);
	}
}
