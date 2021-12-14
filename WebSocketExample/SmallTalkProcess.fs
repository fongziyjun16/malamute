namespace WebSocketExample

open WebSharper
open WebSharper.JavaScript
open WebSharper.AspNetCore.WebSocket
open WebSharper.AspNetCore.WebSocket.Client

[<JavaScript>]
module SmallTalkProcess =
    
    let ProcessBinding(ep: WebSocketEndpoint<S2CMessage, C2SMessage>) =
        
        let mutable wsServiceProvider: WebSocketServer<S2CMessage,C2SMessage> option = None
        
        let wsConnect =
            async {
                return! ConnectStateful ep <| fun ws -> async {
                    return 0, fun state msg -> async {
                        match msg with
                        | Open ->
                            Console.Log("WebSocket Connection Open")
                            return state
                        | Message data ->
                            match data with
                            | Broadcast content ->
                                let ul = JS.Document.GetElementById("TalkingList")
                                let li = JS.Document.CreateElement("li")
                                li.AppendChild(JS.Document.CreateTextNode(content)) |> ignore
                                li.SetAttribute("class", "list-group-item")
                                ul.AppendChild(li) |> ignore
                            return state + 1
                        | Close ->
                            Console.Log("WebSocket Connection Close")
                            return state
                        | Error ->
                            Console.Log("WebSocket Connection Error")
                            return state
                    }
                }
            }
        wsConnect.AsPromise().Then(fun x -> wsServiceProvider <- Some(x)) |> ignore
        
        Templates.SmallTalkTemplate.TalkingRoom()
            .SendTalking(fun e ->
                async {
                    let content = e.Vars.TalkingContent.Value
                    let talkingInfo: TalkingInfo = {
                        username = "someone"
                        content = content
                    }
                    wsServiceProvider.Value.Post(Speak (Json.Serialize talkingInfo))
                }
                |> Async.StartImmediate
            )
            .Doc()