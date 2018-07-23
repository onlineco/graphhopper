# Fork
Graphhopper 0.10ブランチからのフォークです。

## 変更点

OpenStreetMapのJapanTaggingに対応
有料自動車道路を通らないFlagEncoderを追加

CarFlagEncoderのデフォルト速度制限を日本仕様に変更。またmaxspeedタグが指定されている場合は、その値を使用するようにした。
CarAvoidTollFlagEncoderの追加。toll=noが指定されていない自動車道を避けるルートを検索するようにした。
DefaultFlagEncoderFactoryにCarAvoidTollFlagEncoderを追加した。

## Graphhopper本体の更新との同期

下記リンク先のように、オリジナルリポジトリからFetch、Mergeできるようにした。

https://help.github.com/articles/configuring-a-remote-for-a-fork/
https://help.github.com/articles/syncing-a-fork/

#　実行

config.propertiesのgraph.flag_encodersにcar_avoid_tollを追加する
graph.flag_encoders=car,car_avoid_toll
（注意：config.propertiesは.gitignoreに入っている）

下記コマンドで実行
OSMのグラフを作るのにメモリが2GB以上必要
$ ./graphhopper.sh web japan-latest.osm.pbf