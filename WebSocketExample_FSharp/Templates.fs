namespace WebSocketExample

open WebSharper
open WebSharper.UI.Templating

[<JavaScript>]
module Templates =

    type SmallTalkTemplate = Template<"SmallTalk.html", ClientLoad.FromDocument, ServerLoad.WhenChanged>

