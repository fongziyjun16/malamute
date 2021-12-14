namespace WebSocketExample

open WebSharper
open WebSharper.Sitelets
open WebSharper.UI.Html
open WebSharper.UI.Server
open WebSharper.AspNetCore.WebSocket

type EndPoint =
    | [<EndPoint "/">] Talking

module Site =

    [<Website>]
    let Main =
        Application.MultiPage (fun (ctx: Context<_>) endpoint ->
            match endpoint with
            | EndPoint.Talking ->
                let buildEndPoint(url: string): WebSocketEndpoint<S2CMessage, C2SMessage> =
                    WebSocketEndpoint.Create(url, "/ws", JsonEncoding.Readable)
                let ep = buildEndPoint(ctx.RequestUri.ToString())
                Content.Page(
                    Templates.SmallTalkTemplate()
                        .Body(client <@ SmallTalkProcess.ProcessBinding(ep) @>)
                        .Doc()
                )
        )
