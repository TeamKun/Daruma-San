# Daruma-San  
だるまさんがころんだプラグイン  

# 動作条件  
- Minecraft 1.15.2  
- PaperMC 1.15.2

# 仕様  
- コマンドブロックに/darumaコマンドを登録
- 上記コマンドブロックの上に感圧版を載せ, おにはその上に立つ  
- 90°振り向く間に「だるまさんがころんだ」と一文字ずつ表示する
- 最後の「だ」が表示されてから5秒間の間に動いたプレイヤーはkill  
- コンフィグで以下のように値を変更可能  

# コンフィグ設定  
- degree 振り向きの度数(最大179)
- judgePeriod さいごの「だ」の後の判定時間(秒)
- se 文字が表示されるときの効果音  (BukkitAPI Sound列挙型)
- seLast 最後の文字が表示されるときの効果音  (BukkitAPI Sound列挙型)
- deathLogPlayer: プレイヤーがkillされたときのメッセージ(player名の後の文字を指定可能)
- deathLogOni: 鬼がkillされたときのメッセージ(player名の後の文字を指定可能)  
- seCustom datapackで効果音を指定するとき設定 例 (path名がdaruma.1の場合, darumaを設定すると最初の文字が表示されたときdaruma.1が流れる)