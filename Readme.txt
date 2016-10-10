検索エンジン

1. 文書中の語彙(形態素)をDBに登録する
ツール名：Indexer
Indexer.doRegist

1-1. 対象ディレクトリの文書を走査する。
1-2. 文書からテキストを抽出する。
1-3. テキストを形態素解析する。
参考) 形態素解析

1-4. DBに登録する
参考)sqlite3
・doc
　dicid
　title
・term
　termid
　表記
・docterm
　termid, docid


参考)形態素解析
kuromori
https://www.atilika.com/ja/products/kuromoji.html

参考)sqlite3
sqlite3をWindows7にインストールする手順
http://qiita.com/bu-son/items/5790dce071dc0864e035

参考)デザインパターン
https://ja.wikipedia.org/wiki/%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3%E3%83%91%E3%82%BF%E3%83%BC%E3%83%B3_(%E3%82%BD%E3%83%95%E3%83%88%E3%82%A6%E3%82%A7%E3%82%A2)

2. 語彙を含む文書一覧を取得する
ツール名: Searcher
searcher.search

文書と語彙の関連度を計算する
Strategyパターン

スコア順に文書を並び替える
文書一覧を表示する
