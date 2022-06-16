(function(Global)
{
 "use strict";
 var WebSocketExample,TalkingInfo,SmallTalkProcess,WebSocketExample_Templates,WebSocketExample_JsonEncoder,WebSocketExample_JsonDecoder,WebSharper,Concurrency,JSON,ClientSideJson,Provider,JavaScript,Promise,AspNetCore,WebSocket,Client,WithEncoding,console,UI,Templating,Runtime,Server,ProviderBuilder,Handler,TemplateInstance,Client$1,Templates;
 WebSocketExample=Global.WebSocketExample=Global.WebSocketExample||{};
 TalkingInfo=WebSocketExample.TalkingInfo=WebSocketExample.TalkingInfo||{};
 SmallTalkProcess=WebSocketExample.SmallTalkProcess=WebSocketExample.SmallTalkProcess||{};
 WebSocketExample_Templates=Global.WebSocketExample_Templates=Global.WebSocketExample_Templates||{};
 WebSocketExample_JsonEncoder=Global.WebSocketExample_JsonEncoder=Global.WebSocketExample_JsonEncoder||{};
 WebSocketExample_JsonDecoder=Global.WebSocketExample_JsonDecoder=Global.WebSocketExample_JsonDecoder||{};
 WebSharper=Global.WebSharper;
 Concurrency=WebSharper&&WebSharper.Concurrency;
 JSON=Global.JSON;
 ClientSideJson=WebSharper&&WebSharper.ClientSideJson;
 Provider=ClientSideJson&&ClientSideJson.Provider;
 JavaScript=WebSharper&&WebSharper.JavaScript;
 Promise=JavaScript&&JavaScript.Promise;
 AspNetCore=WebSharper&&WebSharper.AspNetCore;
 WebSocket=AspNetCore&&AspNetCore.WebSocket;
 Client=WebSocket&&WebSocket.Client;
 WithEncoding=Client&&Client.WithEncoding;
 console=Global.console;
 UI=WebSharper&&WebSharper.UI;
 Templating=UI&&UI.Templating;
 Runtime=Templating&&Templating.Runtime;
 Server=Runtime&&Runtime.Server;
 ProviderBuilder=Server&&Server.ProviderBuilder;
 Handler=Server&&Server.Handler;
 TemplateInstance=Server&&Server.TemplateInstance;
 Client$1=UI&&UI.Client;
 Templates=Client$1&&Client$1.Templates;
 TalkingInfo.New=function(username,content)
 {
  return{
   username:username,
   content:content
  };
 };
 SmallTalkProcess.ProcessBinding$44$25=function(wsServiceProvider)
 {
  return function(e)
  {
   var b;
   Concurrency.StartImmediate((b=null,Concurrency.Delay(function()
   {
    var talkingInfo;
    talkingInfo=TalkingInfo.New("someone",e.Vars.Hole("talkingcontent").$1.Get());
    wsServiceProvider.$0.Post({
     $:0,
     $0:JSON.stringify(((Provider.Id())())(talkingInfo))
    });
    return Concurrency.Zero();
   })),null);
  };
 };
 SmallTalkProcess.ProcessBinding=function(ep)
 {
  var wsServiceProvider,b,b$1,t,p,i;
  wsServiceProvider=null;
  Promise.OfAsync((b=null,Concurrency.Delay(function()
  {
   return WithEncoding.ConnectStateful(function(a)
   {
    return JSON.stringify((WebSocketExample_JsonEncoder.j$1())(a));
   },function(a)
   {
    return(WebSocketExample_JsonDecoder.j())(JSON.parse(a));
   },ep,function()
   {
    var b$2;
    b$2=null;
    return Concurrency.Delay(function()
    {
     return Concurrency.Return([0,function(state)
     {
      return function(msg)
      {
       var b$3;
       b$3=null;
       return Concurrency.Delay(function()
       {
        var ul,li;
        return msg.$==0?Concurrency.Combine((ul=self.document.getElementById("TalkingList"),(li=self.document.createElement("li"),(li.appendChild(self.document.createTextNode(msg.$0.$0)),li.setAttribute("class","list-group-item"),ul.appendChild(li),Concurrency.Zero()))),Concurrency.Delay(function()
        {
         return Concurrency.Return(state+1);
        })):msg.$==3?(console.log("WebSocket Connection Close"),Concurrency.Return(state)):msg.$==1?(console.log("WebSocket Connection Error"),Concurrency.Return(state)):(console.log("WebSocket Connection Open"),Concurrency.Return(state));
       });
      };
     }]);
    });
   });
  }))).then(function(x)
  {
   wsServiceProvider={
    $:1,
    $0:x
   };
  });
  return(b$1=(t=new ProviderBuilder.New$1(),(t.h.push(Handler.EventQ2(t.k,"sendtalking",function()
  {
   return t.i;
  },function(e)
  {
   var b$2;
   Concurrency.StartImmediate((b$2=null,Concurrency.Delay(function()
   {
    var talkingInfo;
    talkingInfo=TalkingInfo.New("someone",e.Vars.Hole("talkingcontent").$1.Get());
    wsServiceProvider.$0.Post({
     $:0,
     $0:JSON.stringify(((Provider.Id())())(talkingInfo))
    });
    return Concurrency.Zero();
   })),null);
  })),t)),(p=Handler.CompleteHoles(b$1.k,b$1.h,[["talkingcontent",0]]),(i=new TemplateInstance.New(p[1],WebSocketExample_Templates.talkingroom(p[0])),b$1.i=i,i))).get_Doc();
 };
 WebSocketExample_Templates.talkingroom=function(h)
 {
  Templates.LoadLocalTemplates("smalltalk");
  return h?Templates.NamedTemplate("smalltalk",{
   $:1,
   $0:"talkingroom"
  },h):void 0;
 };
 WebSocketExample_JsonEncoder.j$1=function()
 {
  return WebSocketExample_JsonEncoder._v$1?WebSocketExample_JsonEncoder._v$1:WebSocketExample_JsonEncoder._v$1=(Provider.EncodeUnion(void 0,{
   content:0
  },[["Speak",[["$0","content",Provider.Id(),0]]]]))();
 };
 WebSocketExample_JsonDecoder.j=function()
 {
  return WebSocketExample_JsonDecoder._v?WebSocketExample_JsonDecoder._v:WebSocketExample_JsonDecoder._v=(Provider.DecodeUnion(void 0,{
   value:0
  },[["Broadcast",[["$0","value",Provider.Id(),0]]]]))();
 };
}(self));
